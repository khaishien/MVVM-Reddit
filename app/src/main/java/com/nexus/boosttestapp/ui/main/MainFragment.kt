package com.nexus.boosttestapp.ui.main

import android.content.Intent
import android.util.Log
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.nexus.boosttestapp.R
import com.nexus.boosttestapp.core.BaseFragment
import com.nexus.boosttestapp.databinding.MainFragmentBinding
import com.nexus.boosttestapp.network.RedditAuthClient
import com.nexus.boosttestapp.network.RedditClient
import com.nexus.boosttestapp.ui.item.ItemActivity
import com.nexus.boosttestapp.ui.login.LoginActivity
import com.nexus.boosttestapp.ui.newitem.NewItemActivity
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


class MainFragment : BaseFragment<MainFragmentBinding, MainViewModel>() {

    private var subredditListAdapter: SubredditListAdapter? = null

    companion object {
        fun newInstance() = MainFragment()
    }

    override val viewModelClass: Class<MainViewModel>
        get() = MainViewModel::class.java
    override val layout: Int
        get() = R.layout.main_fragment

    override fun onInit() {
        initView()
    }

    override fun onResume() {
        super.onResume()

        val authClient = RedditAuthClient.getRedditService(context!!)
        if (authClient.hasSavedBearer()) {
            Log.d("TOKEN", authClient.getSavedBearer().getRawAccessToken()!!)
            doAsync {
                RedditClient.setHeader(authClient.getSavedBearer().getRawAccessToken()!!)
                uiThread {
                    //                    mViewModel!!.getSubredditList()
                }
            }


        } else {

            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
        }

    }

    override fun subscribeObserver() {
        super.subscribeObserver()

        mViewModel!!.subredditData?.observe(this, Observer { pagedList ->
            subredditListAdapter!!.submitList(pagedList)

        })
        mViewModel!!.networkState?.observe(this, Observer { networkState ->
            subredditListAdapter!!.networkState = networkState
        })

        mViewModel!!.onFailedUpVoteEvent.observe(this, Observer { thingId ->
            subredditListAdapter!!.deductUpVote(thingId)
        })

        mViewModel!!.onFailedDownVoteEvent.observe(this, Observer { thingId ->
            subredditListAdapter!!.deductUpVote(thingId)
        })

        mViewModel!!.onGetSubredditEvent.observe(this, Observer { subreddit ->
            ItemActivity.start(activity!!, subreddit)
        })
    }

    private fun initView() {
        val glide = Glide.with(this)
        mBinding!!.subredditRecyclerView.layoutManager = LinearLayoutManager(context)
        subredditListAdapter =
            SubredditListAdapter(glide, object : SubredditListAdapter.OnCallback {
                override fun onUpVote(thingId: String) {
                    mViewModel!!.upvote(thingId)
                }

                override fun onDownVote(thingId: String) {
                    mViewModel!!.downvote(thingId)
                }

                override fun onClick(thingId: String) {
                    mViewModel!!.getSubreddit(thingId)
                }
            })
        mBinding!!.subredditRecyclerView.adapter = subredditListAdapter

        mBinding!!.fabAdd.setOnClickListener {
            NewItemActivity.start(activity!!)
        }
    }


}
