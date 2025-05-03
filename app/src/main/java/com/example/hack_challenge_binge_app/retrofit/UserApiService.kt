package com.example.hack_challenge_binge_app.retrofit

import com.example.hack_challenge_binge_app.model.LoginRequest
import com.example.hack_challenge_binge_app.model.RegisterRequest
import com.example.hack_challenge_binge_app.model.UserResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserApiService {
    @GET("api/users")
    suspend fun getUsers(): List<UserResponse>

    @POST("api/users/register")
    suspend fun registerUser(@Body request: RegisterRequest) : UserResponse

    @POST("api/users/login")
    suspend fun loginUser(@Body request: LoginRequest): UserResponse
}

