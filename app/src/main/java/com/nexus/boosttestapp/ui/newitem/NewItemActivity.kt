package com.nexus.boosttestapp.ui.newitem

import android.app.Activity
import android.content.Intent
import com.nexus.boosttestapp.R
import com.nexus.boosttestapp.core.BaseActivity
import com.nexus.boosttestapp.databinding.BaseActivityBinding

class NewItemActivity : BaseActivity<BaseActivityBinding, NewItemViewModel>() {


    companion object {
        fun start(activity: Activity) {

            val intent = Intent(activity, NewItemActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override val viewModelClass: Class<NewItemViewModel>
        get() = NewItemViewModel::class.java
    override val layout: Int
        get() = R.layout.base_activity

    override fun onFragment() {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.container,
                NewItemFragment.newInstance()
            )
            .commitNow()
    }

    override fun onInit() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.title = "New Subreddit"


    }

}