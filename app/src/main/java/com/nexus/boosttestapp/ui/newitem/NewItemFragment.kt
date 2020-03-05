package com.nexus.boosttestapp.ui.newitem

import androidx.lifecycle.Observer
import com.nexus.boosttestapp.R
import com.nexus.boosttestapp.core.BaseFragment
import com.nexus.boosttestapp.databinding.NewItemFragmentBinding


class NewItemFragment : BaseFragment<NewItemFragmentBinding, NewItemViewModel>() {


    companion object {
        fun newInstance() = NewItemFragment()
    }

    override val viewModelClass: Class<NewItemViewModel>
        get() = NewItemViewModel::class.java
    override val layout: Int
        get() = R.layout.new_item_fragment

    override fun onInit() {
        initView()
    }

    override fun subscribeObserver() {
        super.subscribeObserver()

        mViewModel!!.onSubmitEvent.observe(this, Observer { aBoolean ->

            if (aBoolean) {
                mBinding!!.etTitle.text?.clear()
                mBinding!!.etText.text?.clear()
                activity!!.finish()
            } else {
                mBinding!!.btnSubmit.isEnabled = true
            }
        })
    }

    private fun initView() {

        mBinding!!.btnSubmit.setOnClickListener {


            var title = mBinding!!.etTitle.text.toString()
            var text = mBinding!!.etText.text.toString()
            var error = validateField(title)

            if (error != null) {
                showErrorMessage(activity, error, null)
            } else {
                mBinding!!.btnSubmit.isEnabled = false
                mViewModel!!.submitLink(title, text)
            }
        }

    }

    private fun validateField(title: String): String? {
        if (title.isEmpty()) {
            return "Title is required!"
        }
        if (title.length > 300) {
            return "Title cannot more than 300!"
        }
        return null
    }


}