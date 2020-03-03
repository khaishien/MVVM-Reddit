package com.nexus.boosttestapp.ui

import com.nexus.boosttestapp.R
import com.nexus.boosttestapp.core.BaseActivity
import com.nexus.boosttestapp.databinding.MainActivityBinding

class MainActivity : BaseActivity<MainActivityBinding, MainViewModel>() {
    override val viewModelClass: Class<MainViewModel>
        get() = MainViewModel::class.java
    override val layout: Int
        get() = R.layout.main_activity

    override fun onInit() {

    }

    override fun onFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, MainFragment.newInstance())
            .commitNow()
    }

}
