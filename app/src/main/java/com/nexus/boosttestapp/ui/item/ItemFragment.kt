package com.nexus.boosttestapp.ui.item

import android.view.View
import androidx.lifecycle.Observer
import com.nexus.boosttestapp.R
import com.nexus.boosttestapp.core.BaseFragment
import com.nexus.boosttestapp.databinding.ItemFragmentBinding
import com.nexus.boosttestapp.model.Subreddit
import com.nexus.boosttestapp.utils.DateUtils

class ItemFragment : BaseFragment<ItemFragmentBinding, ItemViewModel>() {


    companion object {
        fun newInstance() = ItemFragment()
    }

    override val viewModelClass: Class<ItemViewModel>
        get() = ItemViewModel::class.java
    override val layout: Int
        get() = R.layout.item_fragment

    override fun onInit() {
        mBinding!!.progressIndicator.visibility = View.VISIBLE
        mBinding!!.itemLayout.visibility = View.GONE
    }

    override fun subscribeObserver() {
        super.subscribeObserver()

        mViewModel!!.onGetSubredditEvent.observe(this, Observer { data ->
            updateView(data)
        })
    }

    private fun updateView(data: Subreddit) {
        mBinding!!.progressIndicator.visibility = View.GONE
        mBinding!!.itemLayout.visibility = View.VISIBLE

        //temp remove preview
//        if (data.preview?.images != null && data.preview?.images?.size!! > 0) {
//            mBinding!!.ivPreview.visibility = View.VISIBLE
//            Glide.with(this).load(data.preview?.images?.get(0)?.source?.url)
//                .into(mBinding!!.ivPreview)
//        } else {
//            mBinding!!.ivPreview.visibility = View.GONE
//        }


        mBinding!!.tvTitle.text = data.title
        mBinding!!.tvDesc.text =
            "created at ${DateUtils.formatDateFromUnix(data.created)} by ${data.author}"
        mBinding!!.tvComments.text = "${data.numComments} comments"

        mBinding!!.tvVoteCount.text = data.score.toString()

        mBinding!!.ibUpvote.setOnClickListener {
            mViewModel!!.upvote(data.thingsId!!)
            data.score = data.score!!.plus(1)
            mBinding!!.tvVoteCount.text = data.score.toString()
        }
        mBinding!!.ibDownvote.setOnClickListener {
            mViewModel!!.downvote(data.thingsId!!)
            data.score = data.score!!.minus(1)
            mBinding!!.tvVoteCount.text = data.score.toString()
        }
    }


}