package com.example.agathalaundry2.data.remote.response.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class PackageOnService(

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("packageId")
    val packageId: String,

    @field:SerializedName("serviceId")
    val serviceId: String,

    @field:SerializedName("price")
    val price: Long,

    @field:SerializedName("package")
    val `package`: @RawValue Package,

    @field:SerializedName("service")
    val service: @RawValue Service,
): Parcelable