package com.example.agathalaundry.ui.payment

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.viewModels
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.agathalaundry.R
import com.example.agathalaundry.databinding.ActivityPaymentBinding
import com.example.agathalaundry.ui.ViewModelFactory

class PaymentActivity : AppCompatActivity() {

    private var _activityPaymentBinding: ActivityPaymentBinding? = null
    private val binding get() = _activityPaymentBinding!!

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth")

    private val paymentViewModel: PaymentViewModel by viewModels {
        ViewModelFactory.getInstance(dataStore)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activityPaymentBinding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val redirectUrl: String = intent.getStringExtra(EXTRA_URL)!!

        binding.wvPayment.apply {
            settings.javaScriptEnabled = true

            webViewClient = object : WebViewClient() {
//                override fun onPageFinished(view: WebView, url: String) {
//                    view.loadUrl("javascript:alert('Load Success')")
//                }
            }

            webChromeClient = object : WebChromeClient() {
                override fun onJsAlert(view: WebView, url: String, message: String, result: android.webkit.JsResult): Boolean {
                    Toast.makeText(this@PaymentActivity, message, Toast.LENGTH_LONG).show()
                    result.confirm()
                    return true
                }
            }

            loadUrl(redirectUrl)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityPaymentBinding = null
    }

    companion object{
        const val EXTRA_URL = "extra_url"
    }
}