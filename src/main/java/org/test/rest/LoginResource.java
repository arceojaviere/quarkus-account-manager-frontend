package org.test.rest;

import java.util.List;

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

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;

@Path("/")
public class LoginResource {

    @Inject
    private Template login;

    @Inject
    private Template home;

    @Inject
    @RestClient
    private AccountServiceClient accountServiceClient;

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
                returnPage = this.home.data("accountData", account);
            }
        }
        return returnPage;
    }
}