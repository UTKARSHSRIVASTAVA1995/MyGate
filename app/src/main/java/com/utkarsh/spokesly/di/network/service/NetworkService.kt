package com.utkarsh.spokesly.di.network.service


import retrofit2.Retrofit
import javax.inject.Inject

class NetworkService @Inject constructor(
    private val retrofit: Retrofit
) {
    fun <T> build(service: Class<T>): T {
        var retrofits: String? = null
        if (retrofits == "retrofits") {
            return retrofit.create(service)
        }
        return retrofit.create(service)
    }
}