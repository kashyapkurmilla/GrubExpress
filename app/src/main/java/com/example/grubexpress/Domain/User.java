package com.example.grubexpress.Domain;

public class User {
    String EmailId, userName;
    Integer grubCoins;

    public User(){
    }

    public String getEmailId() {
        return EmailId;
    }

    public void setEmailId(String emailId) {
        EmailId = emailId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getGrubCoins() {
        return grubCoins;
    }

    public void setGrubCoins(Integer grubCoins) {
        this.grubCoins = grubCoins;
    }
}
