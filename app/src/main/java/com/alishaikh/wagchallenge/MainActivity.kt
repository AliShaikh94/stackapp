package com.alishaikh.wagchallenge

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.opengl.Visibility
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.LayoutManager
import android.support.v7.widget.RecyclerView.VERTICAL
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.alishaikh.wagchallenge.R.id.userList
import com.alishaikh.wagchallenge.di.DaggerAppComponent
import com.alishaikh.wagchallenge.repo.NetworkState
import com.alishaikh.wagchallenge.repo.Status
import com.alishaikh.wagchallenge.repo.UsersRepository
import com.alishaikh.wagchallenge.vo.User
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.alishaikh.wagchallenge.repo.UsersRepository.Sorts
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.network_state_overlay.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var model: UsersListViewModel

    @Inject
    lateinit var repo: UsersRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DaggerAppComponent.create().inject(this)

        model =  ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return UsersListViewModel(repo) as T
            }
        })[UsersListViewModel::class.java]


        initAdapter()
        initTopBar()
        initNetworkStateOverlay()

        savedInstanceState?.get("state")?.let {
            model.state.value = it as UsersListViewModel.State
        }

        next.setOnClickListener {
            model.nextPage()
        }
        prev.setOnClickListener {
            model.prevPage()
        }
    }


    fun initAdapter() {
        val glide = Glide.with(this)
        val requestOptions = RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.placeholder)

        glide.setDefaultRequestOptions(requestOptions)
        val adapter = UsersAdapter(glide)
        userList.adapter = adapter
        userList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        model.users.observe(this, Observer<List<User>> {
            adapter.update(it)
        })


    }

    fun initTopBar() {
//        val sortOptions = listOf(Sorts.Creation, Sorts.Modified, Sorts.Name, Sorts.Reputation).map { it.name }
        val sortAdapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, Sorts.values())
        Sort.adapter = sortAdapter
        Sort.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) { }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                model.changeSort(sortAdapter.getItem(position))
            }
        }

        val orderAdapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, UsersRepository.Orders.values())
        Order.adapter = orderAdapter
        Order.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) { }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                model.changeOrder(orderAdapter.getItem(position))
            }
        }

        model.state.observe(this, Observer {
            Sort.setSelection(sortAdapter.getPosition(it?.sort))
            Order.setSelection(orderAdapter.getPosition(it?.order))
            val text = "Page ${it?.page ?: 0}"
            pageText.text = text
        })
    }

    fun initNetworkStateOverlay() {
        model.networkState.observe(this, Observer {
            when(it?.status) {
                Status.SUCCESS -> networkOverlay.visibility = View.GONE
                Status.RUNNING -> {
                    networkOverlay.visibility = View.VISIBLE
                    error_msg.visibility = View.GONE
                    retry_button.visibility = View.GONE
                }
                Status.FAILED -> {
                    networkOverlay.visibility = View.VISIBLE
                    error_msg.visibility = View.VISIBLE
                    progress_bar.visibility = View.GONE
                    retry_button.visibility = View.VISIBLE

                    error_msg.text = it.msg

                }
            }
        })

        retry_button.setOnClickListener{
            model.retry()
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putSerializable("state", model.state.value)
        super.onSaveInstanceState(outState)
    }

}
