package com.alishaikh.wagchallenge

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.alishaikh.wagchallenge.di.DaggerAppComponent
import com.alishaikh.wagchallenge.repo.UsersRepository
import com.alishaikh.wagchallenge.vo.User
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var model: UsersListViewModel

    @Inject
    lateinit var repo: UsersRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        DaggerAppComponent.create().inject(this)

        model =  ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return UsersListViewModel(repo) as T
            }
        })[UsersListViewModel::class.java]

        initAdapter()

        model.loadPage(1)
    }


    fun initAdapter() {
        val glide = Glide.with(this)
        val requestOptions = RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.placeholder)

        glide.setDefaultRequestOptions(requestOptions)
        val adapter = UsersAdapter(glide)
        userList.adapter = adapter
        model.users.observe(this, Observer<List<User>> {
            adapter.update(it)
        })
    }

}
