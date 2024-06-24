package com.example.agathalaundry.data.remote.response.model

import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.RawValue

data class Order(
    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("userCredentialId")
    val userCredentialId: String,

//    @field:SerializedName("redirectUrl")
//    val redirectUrl: String?,

    @field:SerializedName("amount")
    val amount: Long,

    @field:SerializedName("orderStatus")
    val orderStatus: String,

    @field:SerializedName("createdAt")
    val createdAt: String,

    @field:SerializedName("updatedAt")
    val updatedAt: String,

//    @field:SerializedName("transaction")
//    val transaction: @RawValue Transaction,

    @field:SerializedName("orderDetail")
    val orderDetail: @RawValue List<OrderDetail>
)