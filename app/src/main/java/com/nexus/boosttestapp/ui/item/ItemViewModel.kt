package com.nexus.boosttestapp.ui.item

import android.app.Application
import com.nexus.boosttestapp.core.BaseViewModel
import com.nexus.boosttestapp.model.Subreddit
import com.nexus.boosttestapp.network.RedditClient
import com.nexus.boosttestapp.network.response.RedditListResponse
import com.nexus.boosttestapp.network.response.UpdateVoteResponse
import com.nexus.boosttestapp.utils.SingleLiveEvent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ItemViewModel(application: Application) : BaseViewModel(application) {

    // liveData to notify event
    val onFailedUpVoteEvent: SingleLiveEvent<String> = SingleLiveEvent()
    val onFailedDownVoteEvent: SingleLiveEvent<String> = SingleLiveEvent()
    val onGetSubredditEvent: SingleLiveEvent<Subreddit> = SingleLiveEvent()
    val onUpdateTitleEvent: SingleLiveEvent<String> = SingleLiveEvent()

    // get sr by id
    fun getSubreddit(thingId: String) {
        if (thingId.isNullOrEmpty()) {
            return
        }

        RedditClient.redditService.getSubreddit(thingId)
            .enqueue(object : Callback<RedditListResponse> {
                override fun onFailure(call: Call<RedditListResponse>, t: Throwable) {
                }

                override fun onResponse(
                    call: Call<RedditListResponse>,
                    response: Response<RedditListResponse>
                ) {
                    if (response.isSuccessful) {
                        val body = response.body()

                        if (body!!.data?.children?.size!! > 0) {
                            val subreddit = body.data?.children?.get(0)
                            onGetSubredditEvent.value = subreddit?.data
                            onUpdateTitleEvent.value = subreddit?.data?.subredditNamePrefixed
                        }
                    }
                }

            })
    }
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
