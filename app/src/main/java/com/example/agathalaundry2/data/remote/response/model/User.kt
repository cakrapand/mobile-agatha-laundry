package com.example.agathalaundry2.data.remote.response.model

import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.RawValue

data class User (
    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("userProfile")
    val userProfile: @RawValue UserProfile,
)