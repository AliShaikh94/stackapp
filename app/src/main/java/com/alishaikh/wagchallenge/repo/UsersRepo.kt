package com.alishaikh.wagchallenge.repo

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import android.util.Log
import com.alishaikh.wagchallenge.api.UsersApi
import com.alishaikh.wagchallenge.vo.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.alishaikh.wagchallenge.api.UsersApi.UsersResponse

/**
 * Created by alishaikh on 3/5/18.
 */

class UsersRepository(val usersApi: UsersApi) {

    fun getUsers(page: Int, order: String, sort: String): Listing<User> {


        val data = MutableLiveData<List<User>>()
        val networkState = MutableLiveData<NetworkState>()
        networkState.value = NetworkState.LOADING

        fun refresh() {
            usersApi.getUsersAtPage(page, order, sort, "stackoverflow").enqueue(
                    object : Callback<UsersResponse> {
                        override fun onFailure(call: Call<UsersResponse>?, t: Throwable) {
                            networkState.value = NetworkState.error(t.message ?: "unknown err")
                            Log.d("UsersRepo", t.toString())

                        }

                        override fun onResponse(call: Call<UsersResponse>, response: Response<UsersResponse>) {
                            if (response.isSuccessful) {
                                val items = (response.body() as UsersResponse).items
                                data.value = items
                                networkState.value = NetworkState.LOADED

                            } else {
                                networkState.value = NetworkState.error("error code: ${response.code()}")
                            }
                        }
                    })
        }

        refresh()

        return Listing(
                data,
                networkState,
                retry =  { refresh() }
        )
    }

    enum class Orders(val id: String) {
        Descending("desc"),
        Ascending("asc")
    }

    enum class Sorts(val id: String) {
        Reputation("reputation"),
        Name("name"),
        Creation("creation"),
        Modified("modified")
    }
}