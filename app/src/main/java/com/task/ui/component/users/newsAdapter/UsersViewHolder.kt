package com.task.ui.component.users.newsAdapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.task.R
import com.task.data.remote.dto.User
import com.task.ui.base.listeners.RecyclerItemListener
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.user_list_item.*

/**
 * Created by AhmedEltaher on 5/12/2016.
 */

class UsersViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(user: User, recyclerItemListener: RecyclerItemListener) {
        tv_user_name.text = user.login
        user.avatarUrl?.let { Picasso.get().load(it).placeholder(R.drawable.ic_person_black_48dp).error(R.drawable.ic_person_black_48dp).into(iv_user_image) }
        rl_news_item.setOnClickListener { recyclerItemListener.onItemSelected(user) }
    }
}

