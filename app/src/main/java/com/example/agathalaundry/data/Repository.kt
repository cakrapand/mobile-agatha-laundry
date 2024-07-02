package com.example.agathalaundry.data

import android.util.Log
import com.example.agathalaundry.data.local.AuthPreferences
import com.example.agathalaundry.data.remote.request.OrderRequest
import com.example.agathalaundry.data.remote.response.ErrorResponse
import com.example.agathalaundry.data.remote.retrofit.ApiService
import com.google.gson.Gson
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class Repository(private val apiService: ApiService, private val authPreferences: AuthPreferences) {

    fun isLogin() = flow {
        emit(Result.Loading)
        try {
            authPreferences.getToken().collect {
                emit(Result.Success(it))
            }
        } catch (e: Exception) {
            if (e is HttpException) emit(Result.Error(getHttpError(e).message))
            else emit(Result.Error(e.message.toString()))
        }
    }

    fun login(email: String, password: String) = flow {
        emit(Result.Loading)
        try {
            val result = apiService.login(email, password)
            authPreferences.saveToken(result.data.token)
            emit(Result.Success(result))
        } catch (e: Exception) {
            if (e is HttpException) emit(Result.Error(getHttpError(e).message))
            else emit(Result.Error(e.message.toString()))
        }
    }

    fun register(email: String, password: String, name: String, address: String, phone: String) =
        flow {
            emit(Result.Loading)
            try {
                val result = apiService.register(email, password, name, address, phone)
                emit(Result.Success(result))
            } catch (e: Exception) {
                if (e is HttpException) emit(Result.Error(getHttpError(e).message))
                else emit(Result.Error(e.message.toString()))
            }
        }

    fun getHome() = flow {
        emit(Result.Loading)
        try {
            authPreferences.getToken().collect {
                if (it != null) {
                    val result = apiService.getHome(it)
                    emit(Result.Success(result))
                }
            }
        } catch (e: Exception) {
            if (e is HttpException) emit(Result.Error(getHttpError(e).message))
            else emit(Result.Error(e.message.toString()))
        }
    }

    fun getProfile() = flow {
        emit(Result.Loading)
        try {
            authPreferences.getToken().collect {
                if (it != null) {
                    val result = apiService.getProfile(it)
                    emit(Result.Success(result))
                }
            }
        } catch (e: Exception) {
            if (e is HttpException) emit(Result.Error(getHttpError(e).message))
            else emit(Result.Error(e.message.toString()))
        }
    }

    fun editProfile(name: String, address: String, phone: String) = flow {
        emit(Result.Loading)
        try {
            authPreferences.getToken().collect {
                if (it != null) {
                    val result = apiService.editProfile(it, name, address, phone)
                    emit(Result.Success(result))
                }
            }
        } catch (e: Exception) {
            if (e is HttpException) emit(Result.Error(getHttpError(e).message))
            else emit(Result.Error(e.message.toString()))
        }
    }

    fun getOrders() = flow {
        emit(Result.Loading)
        try {
            authPreferences.getToken().collect {
                if (it != null) {
                    val result = apiService.getOrders(it)
                    emit(Result.Success(result))
                }
            }
        } catch (e: Exception) {
            if (e is HttpException) emit(Result.Error(getHttpError(e).message))
            else emit(Result.Error(e.message.toString()))
        }
    }

    fun getOrderById(orderId: String) = flow {
        emit(Result.Loading)
        try {
            authPreferences.getToken().collect {
                if (it != null) {
                    val result = apiService.getOrderById(it, orderId)
                    emit(Result.Success(result))
                }
            }
        } catch (e: Exception) {
            if (e is HttpException) emit(Result.Error(getHttpError(e).message))
            else emit(Result.Error(e.message.toString()))
        }
    }

    fun getPackages() = flow {
        emit(Result.Loading)
        try {
            authPreferences.getToken().collect {
                if (it != null) {
                    val result = apiService.getPackages(it)
                    emit(Result.Success(result))
                }
            }
        } catch (e: Exception) {
            if (e is HttpException) emit(Result.Error(getHttpError(e).message))
            else emit(Result.Error(e.message.toString()))
        }
    }


    fun addOrder(listOrderRequest: MutableList<String>) = flow {
        emit(Result.Loading)
        val orderRequest = OrderRequest(listOrderRequest)
        try {
            authPreferences.getToken().collect {
                if (it != null) {
                    val result = apiService.addOrder(it, orderRequest)
                    emit(Result.Success(result))
                }
            }
        } catch (e: Exception) {
            if (e is HttpException) emit(Result.Error(getHttpError(e).message))
            else emit(Result.Error(e.message.toString()))
        }
    }

    fun logout() = flow {
        emit(Result.Loading)
        try{
            authPreferences.logout()
            emit(Result.Success("Logout Success"))
        }catch (e: Exception){
            emit(Result.Error(e.message.toString()))
        }
    }

    private fun getHttpError(e: HttpException): ErrorResponse{
        return try {
            Gson().fromJson(e.response()?.errorBody()?.string(), ErrorResponse::class.java)
        } catch (err: Exception) {
            ErrorResponse(500, "Something went wrong")
        }
    }

    companion object {
        @Volatile
        private var instance: Repository? = null
        fun getInstance(
            apiService: ApiService,
            authPreferences: AuthPreferences,
        ): Repository =
            instance ?: synchronized(this) {
                instance ?: Repository(apiService, authPreferences)
            }.also { instance = it }
    }
}

