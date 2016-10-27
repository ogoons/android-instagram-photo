package com.ogoons.instagram;

import com.ogoons.instagram.model.InstagramMedia;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by ogoons on 2016-09-20.
 */
public interface InstagramRequest {
    public static final String BASE_URL = "https://api.instagram.com";

    //@GET("comments")
    //Call<ResponseBody> getComment(@Query("posetId") int postId);

    @GET("v1/users/self/media/recent")
    Call<InstagramMedia> requestMedia(@Query("access_token") String accessToken, @Query("count") int count);

    @GET("v1/users/self/media/recent")
    Call<InstagramMedia> requestMediaNext(
            @Query("access_token") String accessToken,
            @Query("max_id") String maxId);

    @FormUrlEncoded
    @POST("oauth/access_token")
    Call<InstagramAccessToken> accessToken(
            @Field("client_id")     String clientId,
            @Field("client_secret") String clientSecret,
            @Field("grant_type")    String grantType,
            @Field("redirect_uri")  String redirectUri,
            @Field("code")          String code);
}
