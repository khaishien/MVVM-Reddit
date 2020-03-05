package com.nexus.boosttestapp.ui.item

import android.app.Activity
import android.content.Intent
import com.nexus.boosttestapp.R
import com.nexus.boosttestapp.core.BaseActivity
import com.nexus.boosttestapp.databinding.BaseActivityBinding
import com.nexus.boosttestapp.model.Subreddit


class ItemActivity : BaseActivity<BaseActivityBinding, ItemViewModel>() {


    companion object {
        private val DATA = "DATA"
        fun start(activity: Activity, subreddit: Subreddit) {

            val intent = Intent(activity, ItemActivity::class.java)
            intent.putExtra(DATA, subreddit)
            activity.startActivity(intent)
        }
    }

    override val viewModelClass: Class<ItemViewModel>
        get() = ItemViewModel::class.java
    override val layout: Int
        get() = R.layout.base_activity

    override fun onFragment() {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.container,
                ItemFragment.newInstance()
            )
            .commitNow()
    }

    override fun onInit() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        if (intent.hasExtra(DATA)) {
            mViewModel!!.subreddit = intent.getParcelableExtra(DATA)
            supportActionBar?.title = mViewModel!!.subreddit?.subredditNamePrefixed

        }


    }

}