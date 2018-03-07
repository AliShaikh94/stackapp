package com.alishaikh.wagchallenge

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations.map
import android.arch.lifecycle.Transformations.switchMap
import android.arch.lifecycle.ViewModel
import com.alishaikh.wagchallenge.api.UsersApi
import com.alishaikh.wagchallenge.repo.UsersRepository
import com.alishaikh.wagchallenge.vo.User
import java.io.Serializable
import java.util.*

/**
 * Created by alishaikh on 3/5/18.
 */
class UsersListViewModel(val repo: UsersRepository): ViewModel() {

    val state = MutableLiveData<State>()

//    val currentPage = MutableLiveData<Int>()
//
//    val currentOrder = MutableLiveData<UsersRepository.Orders>()
//    val currentSort = MutableLiveData<UsersRepository.Sorts>()

//
    init {
        state.value = State()
    }

    var users = switchMap(state, {
        repo.getUsers(it.page, it.order.id, it.sort.id)
    })


    fun prevPage() {
        state.value?.let {
            if (it.page > 1) {
                it.page = it.page  - 1
                state.value = it
            }
        }
    }

    fun nextPage() {
        state.value?.let {
            it.page = it.page + 1
            state.value = it
        }
    }

    fun changeSort(sort: UsersRepository.Sorts) {
        state.value = State(order = state.value?.order!!, sort = sort)
//        state.value?.let {
//            it.sort = sort
//            it.
//            state.value = it
//        }
    }

    fun changeOrder(order: UsersRepository.Orders) {
        state.value = State(sort = state.value?.sort!!, order = order)
//        state.value?.let {
//            it.order = order
//            state.value = it
//        }
    }

    class State(
        var page: Int = 1,
        var order: UsersRepository.Orders = UsersRepository.Orders.Descending,
        var sort: UsersRepository.Sorts = UsersRepository.Sorts.Reputation
    ) : Serializable

}