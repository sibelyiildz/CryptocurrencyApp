package com.sibelyildiz.cryptocurrencyapp.di

import com.sibelyildiz.cryptocurrencyapp.BuildConfig
import com.sibelyildiz.cryptocurrencyapp.data.service.ApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single { retrofitClient<ApiService>(get()) }
    single { getRetrofit(get()) }
    single { httpClient() }

}

inline fun <reified T> retrofitClient(retrofit: Retrofit): T = retrofit.create(T::class.java)

fun httpClient(): OkHttpClient {
    return OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
        level = when {
            BuildConfig.DEBUG -> {
                HttpLoggingInterceptor.Level.BODY
            }
            else -> {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }
    ).build()
}

fun getRetrofit(httpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(httpClient)
        .build()
}
