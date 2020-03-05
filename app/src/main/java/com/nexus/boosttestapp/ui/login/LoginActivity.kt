package com.nexus.boosttestapp.ui.login

import com.nexus.boosttestapp.R
import com.nexus.boosttestapp.core.BaseActivity
import com.nexus.boosttestapp.databinding.BaseActivityBinding


class LoginActivity : BaseActivity<BaseActivityBinding, LoginViewModel>() {
    override val viewModelClass: Class<LoginViewModel>
        get() = LoginViewModel::class.java
    override val layout: Int
        get() = R.layout.base_activity

    override fun onFragment() {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.container,
                LoginFragment.newInstance()
            )
            .commitNow()
    }

    override fun onInit() {

    }

    //remove back action for this activity, due to other action in this app required auth
    override fun onBackPressed() {

    }
}