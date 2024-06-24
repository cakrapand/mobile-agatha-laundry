package com.example.agathalaundry2.ui.editprofile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.agathalaundry2.data.Repository

class EditProfileViewModel(private val repository: Repository) : ViewModel() {
    fun getProfile() = repository.getProfile().asLiveData()

    fun editProfile(name: String, address: String, phone: String) = repository.editProfile(name, address, phone).asLiveData()
}