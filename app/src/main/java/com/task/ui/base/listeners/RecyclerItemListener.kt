package com.task.ui.base.listeners

import com.task.data.remote.dto.User

/**
 * Created by AhmedEltaher on 5/12/2016
 */

interface RecyclerItemListener {
    fun onItemSelected(user: User)
}
