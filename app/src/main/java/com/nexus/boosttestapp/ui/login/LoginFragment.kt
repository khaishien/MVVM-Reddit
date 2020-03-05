package com.nexus.boosttestapp.ui.login

import android.graphics.Bitmap
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import com.nexus.boosttestapp.R
import com.nexus.boosttestapp.core.BaseFragment
import com.nexus.boosttestapp.databinding.LoginFragmentBinding
import com.nexus.boosttestapp.network.RedditAuthClient
import com.nexus.boosttestapp.network.RedditClient
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class LoginFragment : BaseFragment<LoginFragmentBinding, LoginViewModel>() {


    companion object {
        fun newInstance() = LoginFragment()
    }

    override val viewModelClass: Class<LoginViewModel>
        get() = LoginViewModel::class.java
    override val layout: Int
        get() = R.layout.login_fragment

    override fun onInit() {

        // ui binding
        mBinding!!.loginButton.visibility = View.VISIBLE
        mBinding!!.loginButton.setOnClickListener {
            redditLogin()
        }
    }

    //login reddit using RedditAuthClient
    private fun redditLogin() {

        var authClient = RedditAuthClient.getRedditService(context!!)
        mBinding!!.loginButton.visibility = View.GONE
        mBinding!!.webView.visibility = View.VISIBLE
//        if (!authClient.hasSavedBearer()) {
        mBinding!!.webView.loadUrl(authClient.provideAuthorizeUrl())

        mBinding!!.webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {

                if (authClient.isRedirectedUrl(url)) {
                    mBinding!!.webView.stopLoading()

                    // We will retrieve the bearer on the background thread.
                    doAsync {
                        val bearer = authClient.getTokenBearer(url)
                        RedditClient.setHeader(bearer?.getRawAccessToken()!!)

                        uiThread {
                            mBinding!!.webView.visibility = View.GONE
                            activity?.finish()
                        }
                    }
                }
            }
        }
    }

}