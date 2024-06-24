package com.example.agathalaundry2.data.remote.response.model

import com.google.gson.annotations.SerializedName

data class Service(

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("createdAt")
    val createdAt: String,

    @field:SerializedName("updatedAt")
    val updatedAt: String,
)