package com.ogoons.instagram.model;

/**
 * Created by ogoons on 2016-09-21.
 */
public class InstagramUser {
    public String id;
    public String userName;
    public String fullName;
    public String profilePic;
    public String accessToken;

    public InstagramUser(String accessToken, String id, String userName, String fullName, String profilePic) {
        this.accessToken    = accessToken;
        this.id             = id;
        this.userName       = userName;
        this.fullName       = fullName;
        this.profilePic     = profilePic;
    }
}
