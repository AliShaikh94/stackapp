package com.alishaikh.wagchallenge.repo

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData

/**
 * Created by ashaikh on 3/7/18.
 */


data class Listing<T> (
        val list: LiveData<List<T>>,
        val networkState: LiveData<NetworkState>,
        val retry: () -> Unit
)