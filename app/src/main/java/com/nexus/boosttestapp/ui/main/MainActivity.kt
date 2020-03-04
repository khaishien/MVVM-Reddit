package com.nexus.boosttestapp.ui.main

import com.nexus.boosttestapp.R
import com.nexus.boosttestapp.core.BaseActivity
import com.nexus.boosttestapp.databinding.BaseActivityBinding

class MainActivity : BaseActivity<BaseActivityBinding, MainViewModel>() {
    override val viewModelClass: Class<MainViewModel>
        get() = MainViewModel::class.java
    override val layout: Int
        get() = R.layout.base_activity

    override fun onInit() {

    }

    override fun onFragment() {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.container,
                MainFragment.newInstance()
            )
            .commitNow()
    }

}
