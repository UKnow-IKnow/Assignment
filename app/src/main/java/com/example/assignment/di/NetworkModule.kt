package com.example.assignment.di

import com.example.assignment.api.AuthInterceptor
import com.example.assignment.api.MessageAPI
import com.example.assignment.api.UserAPI
import com.example.assignment.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofitBuilder(): Retrofit.Builder{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient{
        return OkHttpClient.Builder().addInterceptor(authInterceptor).build()
    }

    @Singleton
    @Provides
    fun provideUserAPI(retrofitBuilder: Retrofit.Builder) : UserAPI{
        return retrofitBuilder.build().create(UserAPI::class.java)
    }


    @Singleton
    @Provides
    fun provideMessageApi(retrofitBuilder: Retrofit.Builder, okHttpClient: OkHttpClient):MessageAPI{
        return retrofitBuilder
            .client(okHttpClient)
            .build().create(MessageAPI::class.java)
    }
}