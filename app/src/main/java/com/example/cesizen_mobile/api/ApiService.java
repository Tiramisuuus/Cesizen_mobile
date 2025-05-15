package com.example.cesizen_mobile.api;

import com.example.cesizen_mobile.models.ApiResponse;
import com.example.cesizen_mobile.models.AuthRequest;
import com.example.cesizen_mobile.models.AuthResponse;
import com.example.cesizen_mobile.models.EmotionTracker;
import com.example.cesizen_mobile.models.EmotionTrackerRequest;
import com.example.cesizen_mobile.models.GroupedEmotionsResponse;
import com.example.cesizen_mobile.models.GroupedTrackerResponse;
import com.example.cesizen_mobile.models.Resource;
import com.example.cesizen_mobile.models.SecondaryEmotion;
import com.example.cesizen_mobile.models.UserProfile;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.GET;

public interface ApiService {
    @POST("/admin/register")
    Call<AuthResponse> register(@Body AuthRequest request);

    @POST("/admin/login")
    Call<AuthResponse> login(@Body AuthRequest request);

    @POST("/admin/logout")
    Call<Void> logout(@Header("Authorization") String token);

    @GET("/admin/resources")
    Call<List<Resource>> getResources();

    @Headers("Content-Type: application/json")
    @POST("/admin/emotion-trackers")
    Call<ApiResponse> addEmotionTracker(
            @Body EmotionTrackerRequest body,
            @Header("Authorization") String token
    );

    @GET("/admin/emotions/grouped")
    Call<Map<String, List<SecondaryEmotion>>> getGroupedEmotions(@Header("Authorization") String token);

    @GET("/admin/emotion-trackers")
    Call<List<GroupedTrackerResponse>> getGroupedEmotionTrackers(@Header("Authorization") String token);


    @GET("/admin/user/profile")
    Call<UserProfile> getProfile(@Header("Authorization") String token);
}
