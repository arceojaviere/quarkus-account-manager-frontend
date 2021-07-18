package org.test.rest.client;

import java.util.List;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.test.model.domain.Account;

@RegisterRestClient(configKey = "accountServiceClient")
@Path("/account")
public interface AccountServiceClient {


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Account> getAccounts( @QueryParam("queryPersonDetails") @DefaultValue("false") boolean queryPersonDetails);

    @GET
    @Path("/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Account getAccountByUserId( @PathParam("userId") String userId,
            @QueryParam("queryPersonDetails") @DefaultValue("false") boolean queryPersonDetails);

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Account newAccount(@Valid Account account);
    
}