package com.task.data.remote

import com.task.data.Resource
import com.task.data.remote.dto.User
import com.task.data.remote.dto.UserDetails

/**
 * Created by ahmedEltaher on 3/23/17.
 */

internal interface RemoteSource {
    suspend fun requestUsers(): Resource<List<User>>
    suspend fun requestUserDetails(userID: Int): Resource<UserDetails>
}
