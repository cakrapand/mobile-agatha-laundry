package com.example.agathalaundry.ui

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.agathalaundry.data.Repository
import com.example.agathalaundry.di.Injection
import com.example.agathalaundry.ui.detailorder.DetailOrderViewModel
import com.example.agathalaundry.ui.history.HistoryViewModel
import com.example.agathalaundry.ui.login.LoginViewModel
import com.example.agathalaundry.ui.main.MainViewModel
import com.example.agathalaundry.ui.order.OrderViewModel
import com.example.agathalaundry.ui.payment.PaymentViewModel
import com.example.agathalaundry.ui.editprofile.EditProfileViewModel
import com.example.agathalaundry.ui.register.RegisterViewModel

class ViewModelFactory(private val repository: Repository) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HistoryViewModel::class.java)) {
            return HistoryViewModel(repository) as T
        }
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(repository) as T
        }
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(repository) as T
        }
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(repository) as T
        }
        if (modelClass.isAssignableFrom(OrderViewModel::class.java)) {
            return OrderViewModel(repository) as T
        }
        if (modelClass.isAssignableFrom(DetailOrderViewModel::class.java)) {
            return DetailOrderViewModel(repository) as T
        }
        if (modelClass.isAssignableFrom(PaymentViewModel::class.java)) {
            return PaymentViewModel(repository) as T
        }
        if (modelClass.isAssignableFrom(EditProfileViewModel::class.java)) {
            return EditProfileViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(dataStore: DataStore<Preferences>): ViewModelFactory = instance ?: synchronized(this) {
            instance ?: ViewModelFactory(Injection.provideRepository(dataStore))
        }.also { instance = it }
    }
}