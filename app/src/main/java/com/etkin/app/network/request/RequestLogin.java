package com.etkin.app.network.request;

public class RequestLogin {

    private String email = "";
    private String password = "";
    public RequestLogin(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
