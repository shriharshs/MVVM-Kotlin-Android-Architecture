package com.task.data.remote

import com.task.App
import com.task.data.Resource
import com.task.data.error.Error.Companion.NETWORK_ERROR
import com.task.data.error.Error.Companion.NO_INTERNET_CONNECTION
import com.task.data.remote.dto.User
import com.task.data.remote.dto.UserDetails
import com.task.data.remote.service.GitHubUsers
import com.task.utils.Network.Utils.isConnected
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject


/**
 * Created by AhmedEltaher on 5/12/2016
 */

class RemoteRepository @Inject
constructor(private val serviceGenerator: ServiceGenerator) : RemoteSource {

    override suspend fun requestUsers(): Resource<List<User>> {
        val obj = object : ApiCall {
            override suspend fun requestAPI(): Response<*> {
                val gitHubService = serviceGenerator.createService(GitHubUsers::class.java)
                return gitHubService.fetchUsers()
            }
        }
        return when (val response = processCall(obj)) {
            is List<*> -> {
                Resource.Success(data = response as List<User>)
            }
            else -> {
                Resource.DataError(errorCode = response as Int)
            }
        }
    }

    override suspend fun requestUserDetails(userID: String): Resource<UserDetails> {
        val obj = object : ApiCall {
            override suspend fun requestAPI(): Response<*> {
                val gitHubService = serviceGenerator.createService(GitHubUsers::class.java)
                return gitHubService.fetchUserDetails(userID)
            }
        }
        return when (val response = processCall(obj)) {
            is UserDetails -> {
                Resource.Success(data = response)
            }
            else -> {
                Resource.DataError(errorCode = response as Int)
            }
        }
    }


    private suspend fun processCall(apiCall: ApiCall): Any? {
        if (!isConnected(App.context)) {
            return NO_INTERNET_CONNECTION
        }
        return try {
            val response = apiCall.requestAPI()
            val responseCode = response.code()
            if (response.isSuccessful) {
                response.body()
            } else {
                responseCode
            }
        } catch (e: IOException) {
            NETWORK_ERROR
        }
    }

    private interface ApiCall {
        suspend fun requestAPI(): Response<*>
    }
}
