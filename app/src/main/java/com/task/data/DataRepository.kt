package com.task.data

import com.task.data.local.LocalRepository
import com.task.data.remote.RemoteRepository
import com.task.data.remote.dto.User
import com.task.data.remote.dto.UserDetails
import javax.inject.Inject


/**
 * Created by AhmedEltaher on 5/12/2016
 */

class DataRepository @Inject
constructor(private val remoteRepository: RemoteRepository, private val localRepository: LocalRepository) : DataSource {

    override suspend fun requestUsers(): Resource<List<User>> {
        return remoteRepository.requestUsers()
    }

    override suspend fun requestUserDetails(id:Int): Resource<UserDetails> {
        return remoteRepository.requestUserDetails(id)
    }
}
