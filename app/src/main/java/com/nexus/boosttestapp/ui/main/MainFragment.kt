package com.nexus.boosttestapp.ui.main

import android.content.Intent
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.nexus.boosttestapp.R
import com.nexus.boosttestapp.core.BaseFragment
import com.nexus.boosttestapp.databinding.MainFragmentBinding
import com.nexus.boosttestapp.network.RedditAuthClient
import com.nexus.boosttestapp.network.RedditClient
import com.nexus.boosttestapp.ui.login.LoginActivity
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

        var authClient = RedditAuthClient.getRedditService(context!!)
        if (authClient.hasSavedBearer()) {
            doAsync {
                RedditClient.setHeader(authClient.getSavedBearer().getRawAccessToken()!!)
                uiThread {

                    mViewModel!!.getSubredditList()
                }
            }


        } else {

            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
        }

    }

    override fun subscribeObserver() {
        super.subscribeObserver()

        mViewModel!!.onGetSubredditData.observe(this, Observer {
            subredditListAdapter?.notifyDataSetChanged()

        })

    }

    private fun initView() {
        mBinding!!.subredditRecyclerView.layoutManager = LinearLayoutManager(context)
        subredditListAdapter = SubredditListAdapter()
        mBinding!!.subredditRecyclerView.adapter = subredditListAdapter
        subredditListAdapter!!.data = mViewModel!!.subredditData
    }


}
