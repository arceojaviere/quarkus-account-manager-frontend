package org.test.rest.jwt;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.rest.client.ext.ClientHeadersFactory;
import javax.ws.rs.core.MultivaluedMap;
import org.jboss.resteasy.specimpl.MultivaluedMapImpl;

import org.jboss.logging.Logger;

@RequestScoped
public class JwtHeaderFactory implements ClientHeadersFactory{

    private static final Logger LOGGER = Logger.getLogger(JwtHeaderFactory.class);


    @Inject
    JwtContainer jwtContainer;

    @Override
    public MultivaluedMap<String, String> update(MultivaluedMap<String, String> incomingHeaders, MultivaluedMap<String, String> clientOutgoingHeaders) {
        MultivaluedMap<String, String> result = new MultivaluedMapImpl<>();
        LOGGER.debug(this.jwtContainer.getJwt());
        
        result.add("Authorization", String.format("Bearer %s", this.jwtContainer.getJwt()));
        return result;
    }

}