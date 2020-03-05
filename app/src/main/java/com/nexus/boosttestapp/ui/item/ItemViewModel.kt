package com.nexus.boosttestapp.ui.item

import android.app.Application
import com.nexus.boosttestapp.core.BaseViewModel
import com.nexus.boosttestapp.model.Subreddit
import com.nexus.boosttestapp.network.RedditClient
import com.nexus.boosttestapp.network.response.UpdateVoteResponse
import com.nexus.boosttestapp.utils.SingleLiveEvent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ItemViewModel(application: Application) : BaseViewModel(application) {

    //in memory data share between fragment and activity
    var subreddit: Subreddit? = null

    // liveData to notify event
    val onFailedUpVoteEvent: SingleLiveEvent<String> = SingleLiveEvent()
    val onFailedDownVoteEvent: SingleLiveEvent<String> = SingleLiveEvent()


    // function to up vote sr
    fun upvote(thingId: String) {
        if (thingId.isNullOrEmpty()) {
            return
        }
        RedditClient.redditService.updateVote("1", thingId)
            .enqueue(object : Callback<UpdateVoteResponse> {
                override fun onFailure(
                    call: Call<UpdateVoteResponse>,
                    t: Throwable
                ) {
                    onFailedUpVoteEvent.value = thingId
                }

                override fun onResponse(
                    call: Call<UpdateVoteResponse>,
                    response: Response<UpdateVoteResponse>
                ) {
                }

            })
    }

    // function to down vote sr
    fun downvote(thingId: String) {
        if (thingId.isNullOrEmpty()) {
            return
        }
        RedditClient.redditService.updateVote("-1", thingId)
            .enqueue(object : Callback<UpdateVoteResponse> {
                override fun onFailure(
                    call: Call<UpdateVoteResponse>,
                    t: Throwable
                ) {
                    onFailedDownVoteEvent.value = thingId
                }

                override fun onResponse(
                    call: Call<UpdateVoteResponse>,
                    response: Response<UpdateVoteResponse>
                ) {
                }

            })
    }
}
