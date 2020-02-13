package com.task.data

import com.task.data.remote.dto.User

/**
 * Created by ahmedeltaher on 3/23/17.
 */

interface DataSource {
    suspend fun requestUsers(): Resource<List<User>>
}
