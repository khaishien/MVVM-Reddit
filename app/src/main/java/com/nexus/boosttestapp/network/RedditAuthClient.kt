package com.nexus.boosttestapp.network

import android.content.Context
import com.kirkbushman.auth.RedditAuth
import com.kirkbushman.auth.managers.SharedPrefsStorageManager

object RedditAuthClient {
    private var mService: RedditAuth? = null

    fun getRedditService(context: Context): RedditAuth {
        if (mService == null) {
            mService = RedditAuth.Builder()
                .setCredentials("mu9OHxOj1IdRxA", "http://127.0.0.1")
                .setScopes(arrayOf("read", "vote"))
                .setStorageManager(SharedPrefsStorageManager(context))
                .setLogging(true)
                .build()
        }
        return mService!!
    }
}