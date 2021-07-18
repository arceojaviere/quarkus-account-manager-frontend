package org.test.rest.jwt;

import javax.enterprise.context.RequestScoped;

@RequestScoped
public class JwtContainer {
    private String jwt;

    public String getJwt(){
        return this.jwt;
    }

    public void setJwt(String jwt){
        this.jwt = jwt;
    }
    
}
