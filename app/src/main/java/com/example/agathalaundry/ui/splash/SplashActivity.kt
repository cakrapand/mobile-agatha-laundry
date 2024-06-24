package com.example.agathalaundry.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.viewModels
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.agathalaundry.R
import com.example.agathalaundry.data.Result
import com.example.agathalaundry.ui.ViewModelFactory
import com.example.agathalaundry.ui.login.LoginActivity
import com.example.agathalaundry.ui.login.LoginViewModel
import com.example.agathalaundry.ui.main.MainActivity

class SplashActivity : AppCompatActivity() {

    private val handler = Handler(Looper.getMainLooper())
    private val dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth")

    private val loginViewModel: LoginViewModel by viewModels {
        ViewModelFactory.getInstance(dataStore)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        supportActionBar?.hide()
    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed({ isLogin() }, DELAY_TIME)
    }

    private fun isLogin(){
        loginViewModel.isLogin().observe(this){ result ->
            when(result){
                is Result.Loading -> {}
                is Result.Success -> {
                    if(result.data != null){
                        startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                    }else{
                        startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                    }
                    finish()
                }
                is Result.Error -> { Toast.makeText(this@SplashActivity, result.error, Toast.LENGTH_SHORT).show() }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacksAndMessages(null)
    }

    companion object{
        private const val DELAY_TIME: Long = 3000
    }
}