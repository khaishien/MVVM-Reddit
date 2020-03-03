package com.nexus.boosttestapp.core

import android.R
import android.app.Activity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import com.nexus.boosttestapp.utils.ViewModelUtils


abstract class BaseActivity<V : ViewDataBinding, VM : ViewModel?> :
    AppCompatActivity() {
    protected lateinit var mBinding: V
    protected var mViewModel: VM? = null
    abstract val viewModelClass: Class<VM>
    abstract val layout: Int
    abstract fun onFragment()
    abstract fun onInit()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this as Activity, layout)
        mViewModel = ViewModelUtils.getViewModel(this, viewModelClass)

        onInit()
        onFragment()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    protected fun initToolbar(
        toolbar: Toolbar?,
        title: String?
    ) {
        if (toolbar != null) {
            setSupportActionBar(toolbar)
            if (title != null) {
                supportActionBar!!.title = title
            } else {
                supportActionBar!!.title = ""
            }
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setHomeButtonEnabled(true)
        }
    }

}