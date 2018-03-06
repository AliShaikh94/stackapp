package com.alishaikh.wagchallenge.repo

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.LiveData
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

    fun getUsers(page: Int, order: String, sort: String): LiveData<List<User>> {


        val data = MutableLiveData<List<User>>()
        usersApi.getUsersAtPage(page, order, sort, "stackoverflow").enqueue(
                object : Callback<UsersResponse> {
                    override fun onFailure(call: Call<UsersResponse>?, t: Throwable?) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }
                    override fun onResponse(call: Call<UsersResponse>, response: Response<UsersResponse>) {
                        if(response.isSuccessful) {
                            val items = response.body()?.items
                            data.setValue(items)
                        }
                    }
        })
        return data
    }
}