package com.task.ui.component.users.newsAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.task.R
import com.task.data.remote.dto.User
import com.task.ui.base.listeners.RecyclerItemListener
import com.task.ui.component.users.UsersListViewModel

/**
 * Created by AhmedEltaher on 5/12/2016.
 */

class UsersAdapter(private val usersListViewModel: UsersListViewModel, private val users: List<User>) : RecyclerView.Adapter<UsersViewHolder>() {

    private val onItemClickListener: RecyclerItemListener = object : RecyclerItemListener {
        override fun onItemSelected(user: User) {
            usersListViewModel.openUserDetails(user)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_list_item, parent, false)
        return UsersViewHolder(view)
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        holder.bind(users[position], onItemClickListener)
    }

    override fun getItemCount(): Int {
        return users.size
    }
}

