package com.example.agathalaundry2.data

import android.util.Log
import com.example.agathalaundry2.data.local.AuthPreferences
import com.example.agathalaundry2.data.remote.request.OrderRequest
import com.example.agathalaundry2.data.remote.response.ErrorResponse
import com.example.agathalaundry2.data.remote.retrofit.ApiService
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
            if (e is HttpException) {
                val responseError: ErrorResponse =
                    try {
                        Gson().fromJson(
                            e.response()?.errorBody()?.string(),
                            ErrorResponse::class.java
                        )
                    } catch (err: Exception) {
                        ErrorResponse(500, "Something went wrong")
                    }
                emit(Result.Error(responseError.message))
            } else {
                emit(Result.Error(e.message.toString()))
            }
        }
    }

    fun login(email: String, password: String) = flow {
        emit(Result.Loading)
        try {
            val result = apiService.login(email, password)
            authPreferences.saveToken(result.data.token)
            emit(Result.Success(result))
        } catch (e: Exception) {
            if (e is HttpException) {
                val responseError: ErrorResponse =
                    try {
                        Gson().fromJson(
                            e.response()?.errorBody()?.string(),
                            ErrorResponse::class.java
                        )
                    } catch (err: Exception) {
                        ErrorResponse(500, "Something went wrong")
                    }
                emit(Result.Error(responseError.message))
            } else {
                emit(Result.Error(e.message.toString()))
            }
        }
    }

    fun register(email: String, password: String, name: String, address: String, phone: String) =
        flow {
            emit(Result.Loading)
            try {
                val result = apiService.register(email, password, name, address, phone)
                emit(Result.Success(result))
            } catch (e: Exception) {
                if (e is HttpException) {
                    val responseError: ErrorResponse =
                        try {
                            Gson().fromJson(
                                e.response()?.errorBody()?.string(),
                                ErrorResponse::class.java
                            )
                        } catch (err: Exception) {
                            ErrorResponse(500, "Something went wrong")
                        }
                    emit(Result.Error(responseError.message))
                } else {
                    emit(Result.Error(e.message.toString()))
                }
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
            if (e is HttpException) {
                val responseError: ErrorResponse =
                    try {
                        Gson().fromJson(
                            e.response()?.errorBody()?.string(),
                            ErrorResponse::class.java
                        )
                    } catch (err: Exception) {
                        ErrorResponse(500, "Something went wrong")
                    }
                emit(Result.Error(responseError.message))
            } else {
                emit(Result.Error(e.message.toString()))
            }
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
            if (e is HttpException) {
                val responseError: ErrorResponse =
                    try {
                        Gson().fromJson(
                            e.response()?.errorBody()?.string(),
                            ErrorResponse::class.java
                        )
                    } catch (err: Exception) {
                        ErrorResponse(500, "Something went wrong")
                    }
                emit(Result.Error(responseError.message))
            } else {
                emit(Result.Error(e.message.toString()))
            }
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
            if (e is HttpException) {
                val responseError: ErrorResponse =
                    try {
                        Gson().fromJson(
                            e.response()?.errorBody()?.string(),
                            ErrorResponse::class.java
                        )
                    } catch (err: Exception) {
                        ErrorResponse(500, "Something went wrong")
                    }
                emit(Result.Error(responseError.message))
            } else {
                emit(Result.Error(e.message.toString()))
            }
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
            if (e is HttpException) {
                val responseError: ErrorResponse =
                    try {
                        Gson().fromJson(
                            e.response()?.errorBody()?.string(),
                            ErrorResponse::class.java
                        )
                    } catch (err: Exception) {
                        ErrorResponse(500, "Something went wrong")
                    }
                emit(Result.Error(responseError.message))
            } else {
                emit(Result.Error(e.message.toString()))
            }
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
            if (e is HttpException) {
                val responseError: ErrorResponse =
                    try {
                        Gson().fromJson(
                            e.response()?.errorBody()?.string(),
                            ErrorResponse::class.java
                        )
                    } catch (err: Exception) {
                        ErrorResponse(500, "Something went wrong")
                    }
                emit(Result.Error(responseError.message))
            } else {
                emit(Result.Error(e.message.toString()))
            }
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
            Log.i("TEST", "getPackages: ${e}")
            if (e is HttpException) {
                val responseError: ErrorResponse =
                    try {
                        Gson().fromJson(
                            e.response()?.errorBody()?.string(),
                            ErrorResponse::class.java
                        )
                    } catch (err: Exception) {
                        ErrorResponse(500, "Something went wrong")
                    }
                emit(Result.Error(responseError.message))
            } else {
                emit(Result.Error(e.message.toString()))
            }
        }
    }


    fun addOrder(listOrderRequest: MutableList<String>) = flow {
        emit(Result.Loading)
        val orderRequest = OrderRequest(listOrderRequest)
        try {
            authPreferences.getToken().collect {
                if (it != null) {
                    val result = apiService.addOrder(
                        token = it,
                        orderDetails = orderRequest,
                    )
                    emit(Result.Success(result))
                }
            }
        } catch (e: Exception) {
            Log.d("TEST", "${e}")
            if (e is HttpException) {
                val responseError: ErrorResponse =
                    try {
                        Gson().fromJson(
                            e.response()?.errorBody()?.string(),
                            ErrorResponse::class.java
                        )
                    } catch (err: Exception) {
                        ErrorResponse(500, "Something went wrong")
                    }
                emit(Result.Error(responseError.message))
            } else {
                emit(Result.Error(e.message.toString()))
            }
        }
    }

    fun logout() = flow {
        emit(Result.Loading)
        try{
            authPreferences.logout()
            emit(Result.Success("Logout Success"))
        }catch (e: Exception){
            Log.d("Repository", "logout: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
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

        private const val GOAL_KEY: String = "goal"
        private const val GENDER_KEY: String = "gender"
        private const val HEIGHT_KEY = "height"
        private const val WEIGHT_KEY = "weight"
        private const val LEVEL_KEY = "level"
        private const val TARGET_KEY = "target"
        private const val NAME_KEY = "name"
        private const val AGE_KEY = "age"
    }
}

