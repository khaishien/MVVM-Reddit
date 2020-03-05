package com.nexus.boosttestapp.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.nexus.boosttestapp.R
import com.nexus.boosttestapp.databinding.ItemNetworkBinding
import com.nexus.boosttestapp.databinding.ItemSubredditBinding
import com.nexus.boosttestapp.model.SubredditData
import com.nexus.boosttestapp.utils.DateUtils
import com.nexus.boosttestapp.utils.NetworkState
import com.nexus.boosttestapp.utils.Status


class SubredditListAdapter(
    private val glide: RequestManager,
    private val onCallback: OnCallback
) :
    PagedListAdapter<SubredditData, RecyclerView.ViewHolder>(POST_COMPARATOR) {

    interface OnCallback {
        fun onClick(thingId: String)
        fun onUpVote(thingId: String)
        fun onDownVote(thingId: String)
    }

    private val TYPE_PROGRESS = 0
    private val TYPE_ITEM = 1

    var networkState: NetworkState? = null

    class NetworkStateItemViewHolder(binding: ItemNetworkBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val binding: ItemNetworkBinding = binding
        fun bindView(networkState: NetworkState?) {
            if (networkState != null && networkState.status === Status.RUNNING) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
            if (networkState != null && networkState.status === Status.FAILED) {
                binding.errorMsg.visibility = View.VISIBLE
                binding.errorMsg.text = networkState.msg
            } else {
                binding.errorMsg.visibility = View.GONE
            }
        }

    }

    class ViewHolder(
        private val binding: ItemSubredditBinding,
        private val glide: RequestManager,
        private val onCallback: OnCallback
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun setData(subredditData: SubredditData) {

            var data = subredditData.data!!
            if (!data.thumbnail.isNullOrEmpty() && data.thumbnail != "self") {
                glide.load(data.thumbnail).into(binding.ivIcon)
            }

            var titleString = data.title ?: ""
            if (titleString.length > 255) {
                titleString = titleString.substring(0, 255) + " ... "
            }
            binding.tvTitle.text = titleString
            binding.tvDesc.text =
                "created at ${DateUtils.formatDateFromUnix(data.created)} by ${data.author} to ${data.subredditNamePrefixed}"

            binding.tvVoteCount.text = data.score.toString()

            binding.ibUpvote.setOnClickListener {
                onCallback.onUpVote(data.thingsId!!)
                data.score = data.score!!.plus(1)
                binding.tvVoteCount.text = data.score.toString()
            }
            binding.ibDownvote.setOnClickListener {
                onCallback.onDownVote(data.thingsId!!)
                data.score = data.score!!.minus(1)
                binding.tvVoteCount.text = data.score.toString()
            }
            binding.layout.setOnClickListener {
                onCallback.onClick(data.thingsId!!)
            }
        }
    }

    fun deductUpVote(thingId: String) {
        val found = currentList!!.find { it.data?.thingsId == thingId }
        val index = currentList!!.indexOf(found)
        found!!.data?.score = found.data?.score?.minus(1)
        notifyItemChanged(index)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {
            TYPE_PROGRESS -> NetworkStateItemViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_network,
                    parent,
                    false
                )
            )
            else -> ViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_subreddit,
                    parent,
                    false
                ),
                this.glide,
                this.onCallback
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (hasExtraRow() && position == itemCount - 1) {
            return TYPE_PROGRESS
        } else {
            return TYPE_ITEM
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is ViewHolder) {
            holder.setData(getItem(position)!!)
        } else {
            (holder as NetworkStateItemViewHolder).bindView(networkState)
        }
    }


    private fun hasExtraRow(): Boolean {
        return networkState != null && networkState !== NetworkState.LOADED
    }


    companion object {
        val POST_COMPARATOR = object : DiffUtil.ItemCallback<SubredditData>() {
            override fun areContentsTheSame(
                oldItem: SubredditData,
                newItem: SubredditData
            ): Boolean =
                oldItem.data?.thingsId == newItem.data?.thingsId

            override fun areItemsTheSame(oldItem: SubredditData, newItem: SubredditData): Boolean =
                oldItem.data?.thingsId == newItem.data?.thingsId
        }

    }
}