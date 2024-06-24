package com.example.agathalaundry.data.remote.response

import com.google.gson.annotations.SerializedName

data class ErrorResponse (
    @field:SerializedName("statusCode")
    val statusCode: Int,

    @field:SerializedName("message")
    val message: String,
)

