package com.nexus.boosttestapp.core

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.nexus.boosttestapp.R
import com.nexus.boosttestapp.utils.ViewModelUtils

abstract class BaseFragment<V : ViewDataBinding, VM : BaseViewModel?> : Fragment() {


    protected var mBinding: V? = null
    protected var mViewModel: VM? = null

    abstract val viewModelClass: Class<VM>
    abstract val layout: Int
    abstract fun onInit()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.bind(inflater.inflate(layout, container, false))
        mViewModel = ViewModelUtils.getViewModel(activity, viewModelClass)
        // ContentView is the root view of the layout of this activity/fragment
        onInit()
        subscribeObserver()
        return mBinding!!.root
    }

    private fun subscribeObserver() {

        mViewModel!!.showErrorEvent.observe(this,
            Observer { message: String? ->
                showErrorMessage(
                    context,
                    message,
                    null
                )
            }
        )
    }

    fun showErrorMessage(
        context: Context?, messageResId: Int,
        onClickListener: DialogInterface.OnClickListener?
    ) {
        AlertDialog.Builder(context)
            .setTitle(R.string.title_error)
            .setMessage(messageResId)
            .setCancelable(false)
            .setNegativeButton(R.string.title_ok, onClickListener)
            .show()
    }

    fun showErrorMessage(
        context: Context?,
        messageString: String?,
        onClickListener: DialogInterface.OnClickListener?
    ) {
        AlertDialog.Builder(context)
            .setTitle(R.string.title_error)
            .setMessage(messageString)
            .setCancelable(false)
            .setNegativeButton(R.string.title_ok, onClickListener)
            .show()
    }

}
