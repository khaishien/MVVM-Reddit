package com.nexus.boosttestapp.network

import com.nexus.boosttestapp.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RedditClient {
    private var mService: RedditService? = null

    fun setHeader(accessToken: String) {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY


        val builder = OkHttpClient.Builder()
            .addInterceptor(RedditAuthInterceptor(accessToken))
            .addInterceptor(interceptor)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)

        val client = builder.build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        mService = retrofit.create<RedditService>(RedditService::class.java!!)
    }

    val redditService: RedditService
        get() {
            if (mService == null) {
                setHeader("")
            }
            return mService!!
        }
}