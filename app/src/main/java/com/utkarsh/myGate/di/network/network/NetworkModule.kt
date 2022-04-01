package com.utkarsh.myGate.di.network.network

import android.content.Context
import com.utkarsh.myGate.di.network.network.NetworkConnectionInterceptor
import com.utkarsh.myGate.di.service.MyGateService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(
        @ApplicationContext appContext: Context
    ): Retrofit = MyGateService.createRetrofit(appContext)

    @Provides
    @Singleton
    fun provideNetworkConnection(
        @ApplicationContext appContext: Context
    ): NetworkConnectionInterceptor = NetworkConnectionInterceptor(appContext)


}