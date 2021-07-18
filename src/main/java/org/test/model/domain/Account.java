package org.test.model.domain;

import javax.json.bind.annotation.JsonbProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


public class Account{
    
    // Changing the property name when marshalling/unmarshalling to/from JSON
    @JsonbProperty("accountId")
    private String id;
    
    @NotBlank(message = "The user ID is required for the Account")
    private String userId;
    private String password;

    @NotNull(message = "The person ID associated with this account is required and cannot be null.")
    private Person person;

    public String getAccountId(){
        return this.id;
    }

    public void setAccountId(String id){
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }


}
