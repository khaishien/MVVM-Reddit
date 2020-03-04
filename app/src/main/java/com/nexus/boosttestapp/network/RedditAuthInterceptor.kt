package com.nexus.boosttestapp.network

import okhttp3.Interceptor
import okhttp3.Response

class RedditAuthInterceptor : Interceptor {
    private var redditToken: String = ""

    constructor(redditToken: String) {
        this.redditToken = redditToken
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request().newBuilder()
            .addHeader(
                "Authorization", "bearer $redditToken"
            )
            .addHeader(
                "User-Agent",
                "android:com.nexus.boosttestapp:v0.0.1 (by /u/nexuks02)"
            )

        return chain.proceed(request.build())
    }
}