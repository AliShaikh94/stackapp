package com.alishaikh.wagchallenge

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.alishaikh.wagchallenge.vo.User
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import kotlinx.android.synthetic.main.user_list_item.view.*

/**
 * Created by alishaikh on 3/5/18.
 */
class UsersAdapter(val glide: RequestManager): RecyclerView.Adapter<UserViewHolder>() {

    private var users: List<User>? = null

    fun update(users: List<User>?) {
        this.users = users
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder.create(parent, glide)
    }

    override fun getItemCount()= users?.size ?: 0


    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        users?.get(position)?.let { holder.bind(it) }
    }
}


class UserViewHolder(view: View, val glide: RequestManager): RecyclerView.ViewHolder(view) {
    var user: User? = null
    val name = view.name
    val profilePicture = view.profilePicture
    val reputation = view.reputation
    var location = view.location
    var gold = view.gold
    var silver = view.silver
    var bronze = view.bronze
//    val skills = view.skills

    fun bind(user: User){
        this.user = user
        name.text = user.displayName
        reputation.text = user.reputation.toString()
        location.text = user.location
        gold.text = user.badgeCounts.gold.toString()
        silver.text = user.badgeCounts.silver.toString()
        bronze.text = user.badgeCounts.bronze.toString()

        glide.load(user.profileImage)
                .into(profilePicture)


    }

    companion object {
        fun create(parent: ViewGroup, glide: RequestManager): UserViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.user_list_item, parent, false)
            return UserViewHolder(view, glide)
        }
    }
}