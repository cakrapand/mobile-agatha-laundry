package com.example.agathalaundry.data.remote.retrofit

import com.example.agathalaundry.data.remote.request.OrderRequest
import com.example.agathalaundry.data.remote.response.BaseResponse
import com.example.agathalaundry.data.remote.response.LoginResponse
import com.example.agathalaundry.data.remote.response.HomeResponse
import com.example.agathalaundry.data.remote.response.model.Order
import com.example.agathalaundry.data.remote.response.model.PackageOnService
import com.example.agathalaundry.data.remote.response.model.User
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @FormUrlEncoded
    @POST("auth/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String,
    ): BaseResponse<LoginResponse>

    @FormUrlEncoded
    @POST("auth/register")
    suspend fun register(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("name") name: String,
        @Field("address") address: String,
        @Field("phone") phone: String,
    ): BaseResponse<Nothing>

    @GET("home")
    suspend fun getHome(@Header("Authorization") token: String): BaseResponse<HomeResponse>

    @GET("orders")
    suspend fun getOrders(@Header("Authorization") token: String): BaseResponse<List<Order>>

    @GET("users/profile")
    suspend fun getProfile(@Header("Authorization") token: String): BaseResponse<User>

    @FormUrlEncoded
    @PATCH("users/profile")
    suspend fun editProfile(
        @Header("Authorization") token: String,
        @Field("name") name: String,
        @Field("address") address: String,
        @Field("phone") phone: String,
    ): BaseResponse<Nothing>

    @GET("orders/{id}")
    suspend fun getOrderById(
        @Header("Authorization") token: String,
        @Path("id") orderId: String
    ): BaseResponse<Order>

    @GET("packages")
    suspend fun getPackages(@Header("Authorization") token: String,): BaseResponse<List<PackageOnService>>

    @POST("orders")
    suspend fun addOrder(
        @Header("Authorization") token: String,
        @Body orderDetails: OrderRequest,
    ): BaseResponse<Nothing>

}