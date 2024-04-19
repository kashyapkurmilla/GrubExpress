package com.example.project162.Domain;


public class User {
    String EmailId, userName , phoneNumber ;
    Integer grubCoins;

    public User() {
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getGrubCoins() {
        return grubCoins;
    }

    public void setGrubCoins(Integer grubCoins) {
        this.grubCoins = grubCoins;
    }

    public User(String emailId, String userName, String phoneNumber, Integer grubCoins) {
        EmailId = emailId;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.grubCoins = grubCoins;
    }
}

