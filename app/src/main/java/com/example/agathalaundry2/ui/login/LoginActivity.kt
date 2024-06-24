package com.example.agathalaundry2.ui.login

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
import com.example.agathalaundry2.R
import com.example.agathalaundry2.data.Result
import com.example.agathalaundry2.databinding.ActivityLoginBinding
import com.example.agathalaundry2.ui.ViewModelFactory
import com.example.agathalaundry2.ui.main.MainActivity
import com.example.agathalaundry2.ui.register.RegisterActivity

class LoginActivity : AppCompatActivity() {

    private var _activityLoginBinding: ActivityLoginBinding? = null
    private val binding get() = _activityLoginBinding!!

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth")

    private val loginViewModel: LoginViewModel by viewModels {
        ViewModelFactory.getInstance(dataStore)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activityLoginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.btnLogin.setOnClickListener {
            val email = binding.edEmailLogin.editText?.text.toString().trim()
            val password = binding.edPasswordLogin.editText?.text.toString().trim()
            loginViewModel.login(email, password).observe(this@LoginActivity){ result ->
                when(result){
                    is Result.Loading ->{
                        binding.pbLogin.visibility = View.VISIBLE
                    }
                    is Result.Success ->{
                        binding.pbLogin.visibility = View.GONE
                        Toast.makeText(this@LoginActivity, "Login Success", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        finish()
                    }
                    is Result.Error ->{
                        binding.pbLogin.visibility = View.GONE
                        Toast.makeText(this@LoginActivity, result.error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        binding.btnMoveToRegister.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityLoginBinding = null
    }
}