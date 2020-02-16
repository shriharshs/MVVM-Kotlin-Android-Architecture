package com.task.data.remote.service

import com.task.data.remote.dto.User
import com.task.data.remote.dto.UserDetails
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by AhmedEltaher on 5/12/2016
 */

interface GitHubUsers {
    @GET("users")
    suspend fun fetchUsers(): Response<List<User>>

    @GET("users/{userId}")
    suspend fun fetchUserDetails(@Path("userId") userId: Int): Response<UserDetails>
}
