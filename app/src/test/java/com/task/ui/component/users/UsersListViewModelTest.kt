package com.task.ui.component.users

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.task.data.Resource
import com.task.data.remote.dto.User
import com.task.data.remote.dto.UserDetails
import com.task.usecase.usersUseCase.UsersUseCase
import com.util.InstantExecutorExtension
import com.util.MainCoroutineRule
import com.util.TestModelsGenerator
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@ExtendWith(InstantExecutorExtension::class)
class UsersListViewModelTest {
    // Subject under test
    private lateinit var usersListViewModel: UsersListViewModel

    // Use a fake UseCase to be injected into the viewmodel
    private val usersUseCase: UsersUseCase = mockk()

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val testModelsGenerator: TestModelsGenerator = TestModelsGenerator()

    @Before
    fun setUp() {
        // Create class under test
        // We initialise the repository with no tasks
        val usersModelSuccess = MutableLiveData<Resource<List<User>>>()
        val userDetailsModelSuccess = MutableLiveData<Resource<UserDetails>>()
        every { usersUseCase.userDetailsLiveData } returns userDetailsModelSuccess
        every { usersUseCase.usersLiveData } returns usersModelSuccess
    }

    @Test
    fun handleUsersList() {
        // Let's do an answer for the liveData
        val usersModel = testModelsGenerator.generateUsersModel()
        val usersModelSuccess = MutableLiveData<Resource<List<User>>>()
        usersModelSuccess.value = Resource.Success(usersModel)

        //1- Mock calls
        every { usersUseCase.getUsers() } just Runs
        every { usersUseCase.usersLiveData } returns usersModelSuccess

        //2-Call
        usersListViewModel = UsersListViewModel(usersUseCase)
        usersListViewModel.getUsers()
        //active observer for livedata
        usersUseCase.usersLiveData.observeForever { }

        //3-verify
        val isEmptyList = usersListViewModel.usersLiveData.value?.data.isNullOrEmpty()
        assert(usersModel == usersListViewModel.usersLiveData.value)
        assert(!isEmptyList)
    }

    @Test
    fun handleEmptyList() {
        // Let's do an answer for the liveData
        val usersModel = testModelsGenerator.generateEmptyUsersModel()
        val usersModelSuccess = MutableLiveData<Resource<List<User>>>()
        usersModelSuccess.value = Resource.Success(usersModel)

        //1- Mock calls
        every { usersUseCase.getUsers() } just Runs
        every { usersUseCase.usersLiveData } returns usersModelSuccess

        //2-Call
        usersListViewModel = UsersListViewModel(usersUseCase)
        usersListViewModel.getUsers()
        //active observer for livedata
        usersUseCase.usersLiveData.observeForever { }

        //3-verify
        val isEmptyList = usersListViewModel.usersLiveData.value?.data.isNullOrEmpty()
        assert(usersModel == usersListViewModel.usersLiveData.value?.data)
        assert(isEmptyList)
    }

    @Test
    fun handleUserDetails() {
        // Let's do an answer for the liveData
        val userDetailsModel = testModelsGenerator.generateUserDetailsModel()
        val userDetailsModelSuccess = MutableLiveData<Resource<UserDetails>>()
        userDetailsModelSuccess.value = Resource.Success(userDetailsModel)

        //1- Mock calls
        val fackId = 1
        every { usersUseCase.getUsers() } just Runs
        every { usersUseCase.userDetailsLiveData } returns userDetailsModelSuccess

        //2-Call
        usersListViewModel = UsersListViewModel(usersUseCase)
        usersListViewModel.getUserDetails(fackId)
        //active observer for livedata
        usersUseCase.userDetailsLiveData.observeForever { }

        //3-verify
        assert(userDetailsModel == usersListViewModel.userDetailsLiveData.value?.data)
    }

    @Test
    fun handleUserDetailsFail() {
        // Let's do an answer for the liveData
        val mockedError = -1
        val userDetailsModelError = MutableLiveData<Resource<UserDetails>>()
        userDetailsModelError.value = Resource.DataError(mockedError)

        //1- Mock calls
        val fackId = 1
        every { usersUseCase.getUserDetails(fackId) } just Runs
        every { usersUseCase.userDetailsLiveData } returns userDetailsModelError

        //2-Call
        usersListViewModel = UsersListViewModel(usersUseCase)
        usersListViewModel.getUserDetails(fackId)
        //active observer for livedata
        usersUseCase.userDetailsLiveData.observeForever { }

        //3-verify
        assert(mockedError == usersListViewModel.userDetailsLiveData.value?.errorCode)
    }
}