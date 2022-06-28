package com.example.noteapp.api;

import com.example.noteapp.models.LoginResponse;
import com.example.noteapp.models.Note;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface API {
    @FormUrlEncoded
    @POST("createUser")
    Call<ResponseBody> createUser(
        @Field("firstName") String firstName,
        @Field("lastName") String lastName,
        @Field("email") String email,
        @Field("password") String password
    );

    @FormUrlEncoded
    @POST("userLogin")
    Call<LoginResponse> userLogin(
        @Field("email") String email,
        @Field("password") String password
    );

    @GET("notes/")
    Call<ArrayList<Note>> getNotesById(
            @Query("_id") String _id
    );
}
