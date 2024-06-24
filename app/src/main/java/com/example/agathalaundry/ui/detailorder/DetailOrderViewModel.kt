package com.example.agathalaundry.ui.detailorder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.agathalaundry.data.Repository

class DetailOrderViewModel (private val repository: Repository) : ViewModel() {

    fun getOrderById(orderId: String) = repository.getOrderById(orderId).asLiveData()
}