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

/**
 * Created by alishaikh on 3/5/18.
 */
class UsersAdapter(val glide: RequestManager): RecyclerView.Adapter<UserViewHolder>() {

    private var users: List<User>? = null

    fun update(users: List<User>?) {
        this.users = users
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder.create(parent, glide)
    }

    override fun getItemCount(): Int {
        return users?.size ?: 0
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        users?.get(position)?.let { holder.bind(it) }
    }
}


class UserViewHolder(view: View, val glide: RequestManager): RecyclerView.ViewHolder(view) {
    var user: User? = null
    val name: TextView = view.findViewById(R.id.name)
    val profilePicture: ImageView = view.findViewById(R.id.profilePicture)
    val reputation: TextView = view.findViewById(R.id.reputation)
    val skills: TextView = view.findViewById(R.id.skills)

    fun bind(user: User){
        this.user = user
        name.text = user.displayName
        reputation.text = user.reputation.toString()
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