package com.example.agathalaundry2.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.agathalaundry2.data.Repository

class RegisterViewModel(private val repository: Repository) : ViewModel() {
    fun register(email: String, password: String, name: String, address: String, phone: String) = repository.register(email, password, name, address, phone).asLiveData()
}