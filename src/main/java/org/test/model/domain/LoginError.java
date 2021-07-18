package org.test.model.domain;

public class LoginError {
    private String message;
    private String suggestedUser;
    private String suggestedPassword;

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getSuggestedUser() {
        return suggestedUser;
    }
    public void setSuggestedUser(String suggestedUser) {
        this.suggestedUser = suggestedUser;
    }
    public String getSuggestedPassword() {
        return suggestedPassword;
    }
    public void setSuggestedPassword(String suggestedPassword) {
        this.suggestedPassword = suggestedPassword;
    }
    
}
