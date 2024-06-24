package com.example.agathalaundry2.ui.detailorder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.agathalaundry2.data.Repository

class DetailOrderViewModel (private val repository: Repository) : ViewModel() {

    fun getOrderById(orderId: String) = repository.getOrderById(orderId).asLiveData()
}