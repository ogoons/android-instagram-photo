package com.zzixx.instagram;

/**
 * Created by ogoons on 2016-09-22.
 */
public class InstagramAccessToken {
    public String access_token;
    public InstagramAccessUser user;
    public class InstagramAccessUser {
        public String username;
        public String bio;
        public String website;
        public String profile_picture;
        public String full_name;
        public String id;
    }
}