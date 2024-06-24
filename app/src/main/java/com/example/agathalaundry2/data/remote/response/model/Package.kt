package com.example.agathalaundry2.data.remote.response.model

import com.google.gson.annotations.SerializedName

data class Package(

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("duration")
    val duration: Int,

    @field:SerializedName("createdAt")
    val createdAt: String,

    @field:SerializedName("updatedAt")
    val updatedAt: String,
)