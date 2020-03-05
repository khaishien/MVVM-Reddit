package com.nexus.boosttestapp.ui.newitem

import android.app.Application
import com.nexus.boosttestapp.core.BaseViewModel
import com.nexus.boosttestapp.network.RedditClient
import com.nexus.boosttestapp.network.response.UpdateVoteResponse
import com.nexus.boosttestapp.utils.SingleLiveEvent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewItemViewModel(application: Application) : BaseViewModel(application) {


    val onSubmitEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()

    fun submitLink(title: String, text: String) {
        if (title.isNullOrEmpty()) {
            return
        }
        RedditClient.redditService.submitLink(title, text, "self", "profile")
            .enqueue(object : Callback<UpdateVoteResponse> {
                override fun onFailure(
                    call: Call<UpdateVoteResponse>,
                    t: Throwable
                ) {
                    onSubmitEvent.value = false
                    showErrorEvent.value = t.message
                }

                override fun onResponse(
                    call: Call<UpdateVoteResponse>,
                    response: Response<UpdateVoteResponse>
                ) {
                    onSubmitEvent.value = true
                }

            })
    }

}
