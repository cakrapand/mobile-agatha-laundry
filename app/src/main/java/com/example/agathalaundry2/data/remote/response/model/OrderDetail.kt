package com.example.agathalaundry2.data.remote.response.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class OrderDetail(

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("orderId")
    val orderId: String,

    @field:SerializedName("packageOnServiceId")
    val packageOnServiceId: String,

    @field:SerializedName("quantity")
    val quantity: Int,

    @field:SerializedName("createdAt")
    val createdAt: String,

    @field:SerializedName("updatedAt")
    val updatedAt: String,

    @field:SerializedName("packageOnService")
    val packageOnService: @RawValue PackageOnService,
): Parcelable