package com.example.agathalaundry2.data.remote.response.model

import com.google.gson.annotations.SerializedName

data class Transaction(

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("orderId")
    val orderId: String,

    @field:SerializedName("amount")
    val amount: Long,

    @field:SerializedName("transactionStatus")
    val transactionStatus: String,

    @field:SerializedName("createdAt")
    val createdAt: String,

    @field:SerializedName("updatedAt")
    val updatedAt: String,
)