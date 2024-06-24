package com.example.agathalaundry.data.remote.response

import com.example.agathalaundry.data.remote.response.model.Order
import com.example.agathalaundry.data.remote.response.model.User
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.RawValue

data class HomeResponse(
    @field:SerializedName("user")
    val user: @RawValue User,

    @field:SerializedName("orders")
    val orders: @RawValue List<Order>,
)