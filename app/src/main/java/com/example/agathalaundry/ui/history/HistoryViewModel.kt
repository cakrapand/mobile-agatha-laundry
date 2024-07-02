package com.example.agathalaundry.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agathalaundry.data.Repository
import com.example.agathalaundry.data.Result
import com.example.agathalaundry.data.remote.response.BaseResponse
import com.example.agathalaundry.data.remote.response.model.Order
import kotlinx.coroutines.launch

class HistoryViewModel(private val repository: Repository) : ViewModel() {
    private val _listOrders = MutableLiveData<Result<BaseResponse<List<Order>>>>()
    val listOrders : LiveData<Result<BaseResponse<List<Order>>>>
        get() = _listOrders

    init{
        getOrders()
    }

    private fun getOrders(){
        viewModelScope.launch {
            repository.getOrders().collect{
                _listOrders.value = it
            }
        }
    }
}