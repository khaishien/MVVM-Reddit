package com.nexus.boosttestapp.ui.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.nexus.boosttestapp.core.BaseViewModel
import com.nexus.boosttestapp.data.RedditDataFactory
import com.nexus.boosttestapp.data.RedditDataSource
import com.nexus.boosttestapp.model.SubredditData
import com.nexus.boosttestapp.network.RedditClient
import com.nexus.boosttestapp.network.response.UpdateVoteResponse
import com.nexus.boosttestapp.utils.NetworkState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executor
import java.util.concurrent.Executors


class MainViewModel(application: Application) : BaseViewModel(application) {

    private var executor: Executor = Executors.newFixedThreadPool(5)
    var networkState: LiveData<NetworkState>? = null
    var subredditData: LiveData<PagedList<SubredditData>>? = null

    init {
        var redditDataFactory = RedditDataFactory()
        networkState = Transformations.switchMap(
            redditDataFactory.mutableLiveData!!
        ) { dataSource: RedditDataSource -> dataSource.networkState }
        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(10)
            .setPageSize(20).build()
        subredditData =
            LivePagedListBuilder(redditDataFactory, pagedListConfig)
                .setFetchExecutor(executor)
                .build()
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
