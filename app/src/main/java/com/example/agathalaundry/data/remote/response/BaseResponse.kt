package com.example.agathalaundry.data.remote.response

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(

    @field:SerializedName("statusCode")
    val statusCode: Int,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("data")
    val data: T,
)