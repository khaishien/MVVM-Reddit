package com.nexus.boosttestapp.ui.main

import android.app.Application
import com.nexus.boosttestapp.core.BaseViewModel
import com.nexus.boosttestapp.model.SubredditData
import com.nexus.boosttestapp.network.RedditClient
import com.nexus.boosttestapp.network.response.SubredditListResponse
import com.nexus.boosttestapp.network.response.UpdateVoteResponse
import com.nexus.boosttestapp.utils.SingleLiveEvent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(application: Application) : BaseViewModel(application) {
    // TODO: Implement the ViewModel

    var subredditData: MutableList<SubredditData> = mutableListOf()
    var onGetSubredditData: SingleLiveEvent<Boolean> = SingleLiveEvent()

    fun getSubredditList() {
        subredditData.clear()
        RedditClient.redditService.getSubredditList()
            .enqueue(object : Callback<SubredditListResponse> {
                override fun onFailure(call: Call<SubredditListResponse>, t: Throwable) {

                }

                override fun onResponse(
                    call: Call<SubredditListResponse>,
                    response: Response<SubredditListResponse>
                ) {

                    val body = response.body() as SubredditListResponse
                    if (body.data != null) {
                        subredditData.addAll(body.data.children)
                        onGetSubredditData.value = true
                    }
                }

            })
    }

    fun vote() {
        RedditClient.redditService.updateVote("1", "t5_2qh1i")
            .enqueue(object : Callback<UpdateVoteResponse> {
                override fun onFailure(
                    call: Call<UpdateVoteResponse>,
                    t: Throwable
                ) {
                }

                override fun onResponse(
                    call: Call<UpdateVoteResponse>,
                    response: Response<UpdateVoteResponse>
                ) {
                }

            })
    }
}
