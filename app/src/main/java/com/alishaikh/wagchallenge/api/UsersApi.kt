package com.alishaikh.wagchallenge.api

import android.util.Log
import com.alishaikh.wagchallenge.vo.User
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by alishaikh on 3/5/18.
 */

interface UsersApi {

    @GET("users")
    fun getUsersAtPage(
            @Query("page") page: Int,
            @Query("order") order: String,
            @Query("sort") sort: String,
            @Query("site") site: String): Call<UsersResponse>

    class UsersResponse(val items: List<User>)

    companion object {
        private const val BASE_URL = "https://api.stackexchange.com/2.2/"
        fun create(): UsersApi = create(HttpUrl.parse(BASE_URL)!!)
        fun create(httpUrl: HttpUrl): UsersApi {

//            val client = OkHttpClient.Builder()
//                    .build()
            return Retrofit.Builder()
                    .baseUrl(httpUrl)
//                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(UsersApi::class.java)
        }
    }


}