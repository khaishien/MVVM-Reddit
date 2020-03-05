package com.nexus.boosttestapp.ui.item

import com.nexus.boosttestapp.R
import com.nexus.boosttestapp.core.BaseFragment
import com.nexus.boosttestapp.databinding.ItemFragmentBinding
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
        initView()
    }


    private fun initView() {

        val data = mViewModel!!.subreddit
        //temp remove image to display
//        if (!data!!.thumbnail.isNullOrEmpty() && data.thumbnail != "self") {
//            mBinding!!.ivIcon.visibility = View.VISIBLE
//            Glide.with(this).load(data.thumbnail).into(mBinding!!.ivIcon)
//        } else {
//            mBinding!!.ivIcon.visibility = View.GONE
//        }

        mBinding!!.tvTitle.text = data!!.title
        mBinding!!.tvDesc.text =
            "created at ${DateUtils.formatDateFromLong(data.created)} by ${data.author}"

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