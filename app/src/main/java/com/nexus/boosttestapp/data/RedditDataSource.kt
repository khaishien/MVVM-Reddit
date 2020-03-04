package com.nexus.boosttestapp.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.nexus.boosttestapp.model.SubredditData
import com.nexus.boosttestapp.network.RedditClient
import com.nexus.boosttestapp.network.response.RedditListResponse
import com.nexus.boosttestapp.utils.NetworkState
import com.nexus.boosttestapp.utils.Status
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RedditDataSource : PageKeyedDataSource<String, SubredditData>() {

     var networkState: MutableLiveData<NetworkState>? = null
     var initialLoading: MutableLiveData<NetworkState>? = null

    init {
        networkState = MutableLiveData()
        initialLoading = MutableLiveData()
    }


    override fun loadInitial(
        params: LoadInitialParams<String>,
        callback: LoadInitialCallback<String, SubredditData>
    ) {

        initialLoading?.postValue(NetworkState.LOADING)
        networkState?.postValue(NetworkState.LOADING)

        RedditClient.redditService.getHot(params.requestedLoadSize)
            .enqueue(object : Callback<RedditListResponse> {
                override fun onFailure(
                    call: Call<RedditListResponse>,
                    t: Throwable
                ) {
                    val errorMessage = if (t == null) "unknown error" else t.message
                    networkState?.postValue(NetworkState(Status.FAILED, errorMessage))

                }

                override fun onResponse(
                    call: Call<RedditListResponse>,
                    response: Response<RedditListResponse>
                ) {
                    if (response.isSuccessful) {
                        val body = response.body() as RedditListResponse
                        callback.onResult(body.data!!.children, null, body.data.after)
                        initialLoading!!.postValue(NetworkState.LOADED)
                        networkState!!.postValue(NetworkState.LOADED)
                    } else {
                        initialLoading!!.postValue(
                            NetworkState(
                                Status.FAILED,
                                response.message()
                            )
                        )
                        networkState!!.postValue(
                            NetworkState(
                                Status.FAILED,
                                response.message()
                            )
                        )
                    }
                }

            })
    }

    override fun loadAfter(
        params: LoadParams<String>,
        callback: LoadCallback<String, SubredditData>
    ) {
        Log.i("TAG", "Loading Rang " + params.key + " Count " + params.requestedLoadSize)
        networkState?.postValue(NetworkState.LOADING)

        RedditClient.redditService.getAfterHot(params.key, params.requestedLoadSize)
            .enqueue(object : Callback<RedditListResponse> {
                override fun onFailure(call: Call<RedditListResponse>, t: Throwable) {
                    val errorMessage =
                        if (t == null) "unknown error" else t.message
                    networkState!!.postValue(NetworkState(Status.FAILED, errorMessage))

                }

                override fun onResponse(
                    call: Call<RedditListResponse>,
                    response: Response<RedditListResponse>
                ) {
                    if (response.isSuccessful) {
                        val body = response.body() as RedditListResponse
                        callback.onResult(body.data!!.children, body.data.after)
                        networkState!!.postValue(NetworkState.LOADED)

                    } else {
                        networkState!!.postValue(
                            NetworkState(
                                Status.FAILED,
                                response.message()
                            )
                        );
                    }

                }

            })
    }

    override fun loadBefore(
        params: LoadParams<String>,
        callback: LoadCallback<String, SubredditData>
    ) {


    }

}