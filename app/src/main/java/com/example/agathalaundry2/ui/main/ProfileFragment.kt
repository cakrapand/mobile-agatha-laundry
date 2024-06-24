package com.example.agathalaundry2.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.activityViewModels
import com.example.agathalaundry2.data.Result
import com.example.agathalaundry2.databinding.FragmentProfileBinding
import com.example.agathalaundry2.ui.ViewModelFactory
import com.example.agathalaundry2.ui.login.LoginActivity
import com.example.agathalaundry2.ui.editprofile.EditProfileActivity

class ProfileFragment : Fragment() {
    private var _fragmentProfileBinding: FragmentProfileBinding? = null
    private val binding get() = _fragmentProfileBinding!!

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth")

    private val mainViewModel: MainViewModel by activityViewModels {
        ViewModelFactory.getInstance(requireActivity().dataStore)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _fragmentProfileBinding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val launcherEditProfileActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                mainViewModel.getProfile()
            }
        }

        binding.btnLogout.setOnClickListener {
            mainViewModel.logout().observe(requireActivity()){
                when(it){
                    is Result.Loading -> {
                        binding.pbProfile.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        binding.pbProfile.visibility = View.GONE
                        Toast.makeText(requireActivity(), "Logged Out", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(requireActivity(), LoginActivity::class.java))
                        requireActivity().finish()
                    }
                    is Result.Error -> {
                        binding.pbProfile.visibility = View.GONE
                        Toast.makeText(requireActivity(), it.error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        mainViewModel.profileData.observe(requireActivity()) {
            when(it){
                is Result.Loading -> {
                    binding.pbProfile.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    binding.pbProfile.visibility = View.GONE
                    binding.tvUserEmailProfile.text = it.data.data.email
                    binding.tvUserNameProfile.text = it.data.data.userProfile.name
                    binding.tvUserAddressProfile.text = it.data.data.userProfile.address
                    binding.tvUserPhoneProfile.text = it.data.data.userProfile.phone
                }
                is Result.Error -> {
                    binding.pbProfile.visibility = View.GONE
                }
            }
        }

        binding.btnEditProfile.setOnClickListener {
            val intent = Intent(requireActivity(), EditProfileActivity::class.java)
            launcherEditProfileActivity.launch(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _fragmentProfileBinding = null
    }

    companion object{
        const val RESULT_OK = 200
    }
}