package com.task.usecase

import androidx.lifecycle.MutableLiveData
import com.task.data.Resource
import com.task.data.remote.dto.User

/**
 * Created by ahmedeltaher on 3/22/17.
 */

interface UseCase {
    fun getUsers()
    fun searchByTitle(keyWord: String): User?
    val usersLiveData: MutableLiveData<Resource<List<User>>>
}
