package com.cx.gal.tickeTooli.zendeskClient.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Galn on 10/21/2018.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    private long id;
    private String url;
    private String name;
    private String email;

    public User(User _user) {
        this.id = _user.id;
        this.url = _user.url;
        this.name = _user.name;
        this.email = _user.email;
    }

    public User(long id, String url, String name, String email) {
        this.id = id;
        this.url = url;
        this.name = name;
        this.email = email;
    }

    public User() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
