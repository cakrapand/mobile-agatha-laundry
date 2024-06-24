package com.example.agathalaundry.ui.register

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
import com.example.agathalaundry.databinding.ActivityLoginBinding
import com.example.agathalaundry.databinding.ActivityRegisterBinding
import com.example.agathalaundry.ui.ViewModelFactory
import com.example.agathalaundry.ui.main.MainActivity

class RegisterActivity : AppCompatActivity() {

    private var _activityRegisterBinding: ActivityRegisterBinding? = null
    private val binding get() = _activityRegisterBinding!!

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth")

    private val registerViewModel: RegisterViewModel by viewModels {
        ViewModelFactory.getInstance(dataStore)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activityRegisterBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.btnRegister.setOnClickListener {
            val email = binding.edEmailRegister.editText?.text.toString().trim()
            val password = binding.edPasswordRegister.editText?.text.toString().trim()
            val name = binding.edNameRegister.editText?.text.toString().trim()
            val address = binding.adAddressRegister.editText?.text.toString().trim()
            val phone = binding.adPhoneRegister.editText?.text.toString().trim()

            registerViewModel.register(email, password, name, address, phone).observe(this@RegisterActivity){result ->
                when(result){
                    is Result.Loading ->{
                        binding.pbRegister.visibility = View.VISIBLE
                    }
                    is Result.Success ->{
                        binding.pbRegister.visibility = View.GONE
                        Toast.makeText(this@RegisterActivity, "Register Success", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    is Result.Error ->{
                        binding.pbRegister.visibility = View.GONE
                        Toast.makeText(this@RegisterActivity, result.error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        binding.btnMoveToLogin.setOnClickListener {
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityRegisterBinding = null
    }
}