package com.cx.gal.tickeTooli.zendeskClient.dto;

/**
 * Created by Galn on 10/21/2018.
 */
public class CxConfig {
    private String password;
    private String email;

    public CxConfig(String password, String email) {
        this.password = password;
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
