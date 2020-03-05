package com.nexus.boosttestapp.network

import android.content.Context
import com.kirkbushman.auth.RedditAuth
import com.kirkbushman.auth.managers.SharedPrefsStorageManager
import com.nexus.boosttestapp.BuildConfig

object RedditAuthClient {
    private var mService: RedditAuth? = null

    fun getRedditService(context: Context): RedditAuth {
        if (mService == null) {
            // using library RedditAuth to help handle oauth of reddit
            mService = RedditAuth.Builder()
                .setCredentials(BuildConfig.REDDIT_CLIENT_ID, "http://127.0.0.1")
                .setScopes(arrayOf("read", "vote", "submit"))
                .setStorageManager(SharedPrefsStorageManager(context))
                .setLogging(true)
                .build()
        }
        return mService!!
    }
}