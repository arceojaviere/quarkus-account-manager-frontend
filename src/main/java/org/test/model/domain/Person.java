package org.test.model.domain;

import java.util.List;

import javax.validation.constraints.NotBlank;


public class Person{
    @NotBlank(message= "The ID of the person associated with the account is required and may not be blank")
    private String id;
    
    private String name;
    
    private String lastName;
    
    private List<String> emailAddresses;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public List<String> getEmailAddresses() {
        return emailAddresses;
    }
    public void setEmailAddresses(List<String> emailAddresses) {
        this.emailAddresses = emailAddresses;
    }

    
}
