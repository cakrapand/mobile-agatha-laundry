package com.example.agathalaundry.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.agathalaundry.data.Repository

class LoginViewModel(private val repository: Repository) : ViewModel() {

    fun isLogin() = repository.isLogin().asLiveData()

    fun login(email: String, password: String) = repository.login(email, password).asLiveData()
}