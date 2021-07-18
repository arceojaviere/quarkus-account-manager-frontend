package org.test.rest;

import java.util.Arrays;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.test.model.domain.Account;

import org.test.model.domain.LoginError;
import org.test.rest.client.AccountServiceClient;
import org.test.rest.client.PersonServiceClient;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.smallrye.jwt.build.Jwt;

import java.util.HashSet;

import org.test.rest.jwt.JwtContainer;

@Path("/")
@RequestScoped
public class LoginResource {

    @Inject
    private Template login;

    @Inject
    private Template home;

    @Inject
    @RestClient
    private AccountServiceClient accountServiceClient;

    @Inject
    @RestClient
    private PersonServiceClient personServiceClient;

    @Inject
    private JwtContainer jwtContainer;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance loadLogin() {
        return login.instance();
    }

    @POST
    @Path("/login")
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public TemplateInstance executeLogin(@FormParam("username") String userId, 
            @FormParam("password") String password){
        
        TemplateInstance returnPage = null;
        Account account = null;
        LoginError error = null;
        
        if(userId == null || userId.equals("")){

            error = new LoginError();
            error.setMessage("User name is required; obviously");
            
            account = this.accountServiceClient.getAccounts(false).get(0);
            
            error.setSuggestedUser(account.getUserId());
            error.setSuggestedPassword(account.getPassword());
            returnPage = this.login.data("loginError", error);

        }else{

            account = this.accountServiceClient.getAccountByUserId(userId, false);
            
            if(account == null){
                error = new LoginError();
                error.setMessage("User name not found in the account database");
                
                account = this.accountServiceClient.getAccounts(false).get(0);
                
                error.setSuggestedUser(account.getUserId());
                error.setSuggestedPassword(account.getPassword());
                returnPage = this.login.data("loginError", error);

            }else if(!password.equals(account.getPassword())){
                error = new LoginError();
                error.setMessage("Wrong password");
                                
                error.setSuggestedUser(account.getUserId());
                error.setSuggestedPassword(account.getPassword());
                returnPage = this.login.data("loginError", error);
            }else{

                this.jwtContainer.setJwt(Jwt.issuer("http://test.signer.cert/issuer")
                    .upn(account.getUserId())
                    .groups(new HashSet<String>(Arrays.asList("admin")))
                    .sign());
                    
                account.setPerson(this.personServiceClient.getPersonById(account.getPerson().getId()));

                returnPage = this.home.data("account", account);
            }
        }
        return returnPage;
    }
}