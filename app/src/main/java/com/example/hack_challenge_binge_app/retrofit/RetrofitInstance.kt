package com.example.hack_challenge_binge_app.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitInstance @Inject constructor() {
    private val okHttpClient = OkHttpClient.Builder().build()

    val apiService: UserApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://binge-ncvc.onrender.com/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserApiService::class.java)
    }
}