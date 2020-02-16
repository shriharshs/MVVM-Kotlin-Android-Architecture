package com.task.ui.component.users

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.task.data.Resource
import com.task.data.error.mapper.ErrorMapper
import com.task.data.remote.dto.User
import com.task.data.remote.dto.UserDetails
import com.task.ui.base.BaseViewModel
import com.task.usecase.errors.ErrorManager
import com.task.usecase.usersUseCase.UsersUseCase
import com.task.utils.Event
import javax.inject.Inject

/**
 * Created by AhmedEltaher on 5/12/2016
 */

class UsersListViewModel @Inject
constructor(private val usersDataUseCase: UsersUseCase) : BaseViewModel() {

    override val errorManager: ErrorManager
        get() = ErrorManager(ErrorMapper())

    /**
     * Data --> LiveData, Exposed as LiveData, Locally in viewModel as MutableLiveData
     */
    var usersLiveData: MutableLiveData<Resource<List<User>>> = usersDataUseCase.usersLiveData
    var userDetailsLiveData: MutableLiveData<Resource<UserDetails>> = usersDataUseCase.userDetailsLiveData

    /**
     * UI actions as event, user action is single one time event, Shouldn't be multiple time consumption
     */
    private val openUserDetailsPrivate = MutableLiveData<Event<UserDetails>>()
    val openUserDetails: LiveData<Event<UserDetails>> get() = openUserDetailsPrivate

    /**
     * Error handling as UI
     */
    private val showSnackBarPrivate = MutableLiveData<Event<Int>>()
    val showSnackBar: LiveData<Event<Int>> get() = showSnackBarPrivate

    private val showToastPrivate = MutableLiveData<Event<Any>>()
    val showToast: LiveData<Event<Any>> get() = showToastPrivate


    fun getUsers() {
        usersDataUseCase.getUsers()
    }

    fun openUserDetails(user: UserDetails) {
        openUserDetailsPrivate.value = Event(user)
    }

    fun showSnackbarMessage(@StringRes message: Int) {
        showSnackBarPrivate.value = Event(message)
    }

    fun showToastMessage(errorCode: Int) {
        val error = errorManager.getError(errorCode)
        showToastPrivate.value = Event(error.description)
    }

    fun getUserDetails(id: Int) {
        usersDataUseCase.getUserDetails(id)
    }

    fun onSearchClick(newsTitle: String) {
    }
}
