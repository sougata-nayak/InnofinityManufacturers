package com.example.innofinitymanufacturers;

public class NewUser {

    private String userName;
    private String userPhone;
    private String userEmail;
    private String uvid;

    public NewUser() {
    }

    public NewUser(String userName, String userPhone, String userEmail, String uvid) {
        this.userName = userName;
        this.userPhone = userPhone;

        this.userEmail = userEmail;
        this.uvid = uvid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }


    public String getUvid() {
        return uvid;
    }

    public void setUvid(String uvid) {
        this.uvid = uvid;
    }
}
