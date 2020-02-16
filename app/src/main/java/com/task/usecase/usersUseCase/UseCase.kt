package com.task.usecase.usersUseCase

import androidx.lifecycle.MutableLiveData
import com.task.data.Resource
import com.task.data.remote.dto.User
import com.task.data.remote.dto.UserDetails

/**
 * Created by ahmedeltaher on 3/22/17.
 */

interface UseCase {
    fun getUserDetails(id:Int)
    fun getUsers()
    val usersLiveData: MutableLiveData<Resource<List<User>>>
    val userDetailsLiveData: MutableLiveData<Resource<UserDetails>>
}
