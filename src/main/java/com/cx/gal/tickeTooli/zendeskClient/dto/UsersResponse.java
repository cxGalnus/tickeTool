package com.cx.gal.tickeTooli.zendeskClient.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by Galn on 10/21/2018.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UsersResponse {
    List<User> users;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
