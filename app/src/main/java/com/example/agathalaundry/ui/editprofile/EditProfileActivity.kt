package com.example.agathalaundry.ui.editprofile

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.agathalaundry.R
import com.example.agathalaundry.data.Result
import com.example.agathalaundry.databinding.ActivityEditProfileBinding
import com.example.agathalaundry.ui.ViewModelFactory
import com.example.agathalaundry.ui.main.ProfileFragment

class EditProfileActivity : AppCompatActivity() {

    private var _activityEditProfileBinding: ActivityEditProfileBinding? = null
    private val binding get() = _activityEditProfileBinding!!

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth")

    private val editProfileViewModel: EditProfileViewModel by viewModels {
        ViewModelFactory.getInstance(dataStore)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activityEditProfileBinding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.topAppBarProfile.setNavigationOnClickListener  { finish() }
        getProfile()
    }

    private fun getProfile(){
        editProfileViewModel.getProfile().observe(this@EditProfileActivity){
            when(it){
                is Result.Loading -> {
                    binding.pbEditProfile.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    binding.pbEditProfile.visibility = View.GONE
                    binding.edEmailEditProfile.editText?.setText(it.data.data.email)
                    binding.edNameEditProfile.editText?.setText(it.data.data.userProfile.name)
                    binding.edAddressEditProfile.editText?.setText(it.data.data.userProfile.address)
                    binding.edPhoneEditProfile.editText?.setText(it.data.data.userProfile.phone)

                    binding.btnEditProfile.setOnClickListener {
                        val name = binding.edNameEditProfile.editText?.text.toString()
                        val address = binding.edAddressEditProfile.editText?.text.toString()
                        val phone = binding.edPhoneEditProfile.editText?.text.toString()
                        editProfile(name, address, phone)
                    }
                }
                is Result.Error -> {
                    binding.pbEditProfile.visibility = View.GONE
                }
            }
        }
    }

    private fun editProfile(name: String, address: String, phone: String){
        editProfileViewModel.editProfile(name, address, phone).observe(this@EditProfileActivity){ editProfile ->
            when(editProfile){
                is Result.Loading -> {
                    binding.pbEditProfile.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    binding.pbEditProfile.visibility = View.GONE
                    Toast.makeText(this@EditProfileActivity, resources.getString(R.string.edit_profile_success), Toast.LENGTH_SHORT).show()
                    setResult(ProfileFragment.RESULT_OK, Intent())
                    finish()
                }
                is Result.Error -> {
                    binding.pbEditProfile.visibility = View.GONE
                    Toast.makeText(this@EditProfileActivity, editProfile.error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityEditProfileBinding = null
    }
}