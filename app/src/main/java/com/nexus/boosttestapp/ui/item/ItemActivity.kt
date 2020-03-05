package com.nexus.boosttestapp.ui.item

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.Observer
import com.nexus.boosttestapp.R
import com.nexus.boosttestapp.core.BaseActivity
import com.nexus.boosttestapp.databinding.BaseActivityBinding


class ItemActivity : BaseActivity<BaseActivityBinding, ItemViewModel>() {


    companion object {
        private val THING_ID = "THING_ID"
        fun start(activity: Activity, thingId: String) {

            val intent = Intent(activity, ItemActivity::class.java)
            intent.putExtra(THING_ID, thingId)
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
        supportActionBar?.title = ""
        subscribeObserver()
        if (intent.hasExtra(THING_ID)) {
            var thingId = intent.getStringExtra(THING_ID)
            mViewModel!!.getSubreddit(thingId)
        }


    }

    private fun subscribeObserver() {
        mViewModel!!.onUpdateTitleEvent.observe(this, Observer { title ->
            supportActionBar?.title = title
        })
    }

}