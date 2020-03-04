package com.nexus.boosttestapp.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.nexus.boosttestapp.R
import com.nexus.boosttestapp.databinding.ItemSubredditBinding
import com.nexus.boosttestapp.model.SubredditData
import com.nexus.boosttestapp.utils.DateUtils

class SubredditListAdapter : RecyclerView.Adapter<SubredditListAdapter.ViewHolder>() {

     var data: List<SubredditData> = ArrayList()

    class ViewHolder(val binding: ItemSubredditBinding) : RecyclerView.ViewHolder(binding.root) {

        fun setData(data: SubredditData) {

//            binding.ivIcon

            binding.tvTitle.text = data.title
            binding.tvCreatedAt.text = "created at ${DateUtils.formatDateFromLong(data.created)} "
            binding.tvSubmittedBy.text = "by ${data.author} "
            binding.tvToReddit.text = "to ${data.subredditNamePrefixed} "

            binding.tvVoteCount.text = data.score.toString()

            binding.ibUpvote.setOnClickListener { }
            binding.ibDownvote.setOnClickListener { }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_subreddit,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(data[position])
    }

}