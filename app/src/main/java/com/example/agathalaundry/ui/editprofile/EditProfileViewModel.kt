package com.example.agathalaundry.ui.editprofile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.agathalaundry.data.Repository

class EditProfileViewModel(private val repository: Repository) : ViewModel() {
    fun getProfile() = repository.getProfile().asLiveData()

    fun editProfile(name: String, address: String, phone: String) = repository.editProfile(name, address, phone).asLiveData()
}