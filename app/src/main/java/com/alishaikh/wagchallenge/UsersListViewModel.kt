package com.alishaikh.wagchallenge

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations.map
import android.arch.lifecycle.Transformations.switchMap
import android.arch.lifecycle.ViewModel
import com.alishaikh.wagchallenge.api.UsersApi
import com.alishaikh.wagchallenge.repo.UsersRepository

/**
 * Created by alishaikh on 3/5/18.
 */
class UsersListViewModel(repo: UsersRepository): ViewModel() {

    val currentPage = MutableLiveData<Int>()

//    init {
//        currentPage.value = 0
//    }

    fun loadPage(page : Int) {
        currentPage.value = page
    }

    var users = switchMap(currentPage, {
        repo.getUsers(it, "desc", "reputation")
    })


    fun prevPage() {
        currentPage.value?.let {
            if(it > 1)
                currentPage.value = it - 1
        }
    }

    fun nextPage() {
        currentPage.value?.let {
            currentPage.value = it + 1
        }
    }

}