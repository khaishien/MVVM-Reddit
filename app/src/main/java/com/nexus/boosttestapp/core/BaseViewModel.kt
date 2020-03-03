package com.nexus.boosttestapp.core

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.nexus.boosttestapp.utils.SingleLiveEvent


open class BaseViewModel(application: Application) : AndroidViewModel(application) {
    var showErrorEvent: SingleLiveEvent<String> = SingleLiveEvent()

}