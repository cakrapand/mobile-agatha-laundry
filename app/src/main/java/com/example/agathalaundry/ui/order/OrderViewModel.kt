package com.example.agathalaundry.ui.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.agathalaundry.data.Repository
import com.example.agathalaundry.data.remote.request.OrderRequest


class OrderViewModel(private val repository: Repository) : ViewModel() {

    val listPackages = repository.getPackages().asLiveData()

    private val _listOrders = MutableLiveData<MutableList<String>>()
    val listOrders: LiveData<MutableList<String>>
        get() = _listOrders


    fun addOrder(order: String) {
        val currentList = _listOrders.value ?: mutableListOf()
        currentList.add(order)
        _listOrders.value = currentList
    }

    fun deleteOrder(pos: Int) {
        val currentList = _listOrders.value ?: mutableListOf()
        currentList.removeAt(pos)
        _listOrders.value = currentList
    }

    fun order() = repository.addOrder(_listOrders.value ?: mutableListOf()).asLiveData()
}