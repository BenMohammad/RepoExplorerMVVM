package com.opensource.giantturtle.clientapp.data.webservice.apiresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by StandardWorkSSD on 11/06/2018.
 */

public class Owner {

    @SerializedName("login")
    @Expose
    private String login;

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    @SerializedName("avatar_url")
    @Expose
    private String avatar_url;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public String toString() {
        return "Owner{" +
                "login='" + login + '\'' +
                ", avatar_url='" + avatar_url + '\'' +
                '}';
    }
}
