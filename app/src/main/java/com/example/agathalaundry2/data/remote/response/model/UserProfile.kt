package com.example.agathalaundry2.data.remote.response.model

import com.google.gson.annotations.SerializedName

data class UserProfile (

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("address")
    val address: String,

    @field:SerializedName("phone")
    val phone: String,
)