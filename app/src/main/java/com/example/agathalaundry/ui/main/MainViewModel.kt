package com.example.agathalaundry.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.agathalaundry.data.Repository
import com.example.agathalaundry.data.remote.response.BaseResponse
import com.example.agathalaundry.data.Result
import com.example.agathalaundry.data.remote.response.HomeResponse
import com.example.agathalaundry.data.remote.response.model.User
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository) : ViewModel() {

    private val _homeData = MutableLiveData<Result<BaseResponse<HomeResponse>>>()
    val homeData : LiveData<Result<BaseResponse<HomeResponse>>>
        get() = _homeData

    private val _profileData = MutableLiveData<Result<BaseResponse<User>>>()
    val profileData : LiveData<Result<BaseResponse<User>>>
        get() = _profileData

    init{
        getHome()
        getProfile()
    }

    fun getHome(){
        viewModelScope.launch {
            repository.getHome().collect{
                _homeData.value = it
            }
        }
    }

    fun getProfile(){
        viewModelScope.launch {
            repository.getProfile().collect{
                _profileData.value = it
            }
        }
    }

    fun logout() = repository.logout().asLiveData()

}