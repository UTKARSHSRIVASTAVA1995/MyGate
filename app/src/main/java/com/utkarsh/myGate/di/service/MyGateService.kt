package com.utkarsh.myGate.di.service

import android.content.Context
import com.utkarsh.myGate.BuildConfig
import com.utkarsh.myGate.di.network.network.NetworkConnectionInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named

object MyGateService {

    private const val baseUrl = "https://jsonplaceholder.typicode.com/"

    private fun createOkHttpClient(appContext: Context): OkHttpClient {

        return OkHttpClient.Builder().apply {
            addInterceptor(
                Interceptor { chain ->
                    val original = chain.request()
                    val builder = chain.request().newBuilder()
                    builder.method(original.method, original.body)
                    return@Interceptor chain.proceed(builder.build())
                }
            )
            if (BuildConfig.DEBUG) {
                this.addInterceptor(HttpLoggingInterceptor()
                    .apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    })
            }
        }.addInterceptor(NetworkConnectionInterceptor(appContext)).build()
    }

    fun createRetrofit(@Named("Api") appContext: Context): Retrofit {
        val httpClient = createOkHttpClient(appContext)
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
    }
}