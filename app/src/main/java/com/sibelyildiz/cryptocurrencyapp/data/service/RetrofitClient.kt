package com.sibelyildiz.cryptocurrencyapp.data.service

import com.sibelyildiz.cryptocurrencyapp.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {

    companion object {
        @Volatile
        private var INSTANCE: Retrofit? = null
        fun getRetrofit(): Retrofit {
            synchronized(this) {
                INSTANCE?.let {
                    return it
                }

                INSTANCE = Retrofit.Builder()
                    .baseUrl("Constant.BASE_URL")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(
                        OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
                            level =
                                when {
                                    BuildConfig.DEBUG -> {
                                        HttpLoggingInterceptor.Level.BODY
                                    }
                                    else -> {
                                        HttpLoggingInterceptor.Level.NONE
                                    }
                                }
                        }
                        ).build()
                    )
                    .build()

                return INSTANCE as Retrofit
            }
        }
    }
}