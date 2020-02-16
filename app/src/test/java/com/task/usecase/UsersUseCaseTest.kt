package com.task.usecase


import com.task.data.DataRepository
import com.task.data.Resource
import com.task.data.error.Error
import com.task.data.remote.dto.User
import com.task.data.remote.dto.UserDetails
import com.task.usecase.usersUseCase.UsersUseCase
import com.util.InstantExecutorExtension
import com.util.MainCoroutineRule
import com.util.TestModelsGenerator
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

/**
 * Created by ahmedeltaher on 3/8/17.
 */
@ExperimentalCoroutinesApi
@ExtendWith(InstantExecutorExtension::class)
class UsersUseCaseTest {

    private var dataRepository: DataRepository? = null

    private lateinit var usersUseCase: UsersUseCase
    private val testModelsGenerator: TestModelsGenerator = TestModelsGenerator()
    private lateinit var users: List<User>
    private lateinit var userDetails: UserDetails

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @BeforeEach
    fun setUp() {
        dataRepository = DataRepository(mockk(), mockk())
        usersUseCase = UsersUseCase(dataRepository!!, mainCoroutineRule.coroutineContext)
    }

    @Test
    fun testGetUsersSuccessful() {
        //mock
        users = testModelsGenerator.generateUsersModel()
        val serviceResponse = Resource.Success(users)
        coEvery { dataRepository?.requestUsers() } returns serviceResponse
        //call
        usersUseCase.getUsers()
        usersUseCase.usersLiveData.observeForever { }
        //assert test
        assert(serviceResponse == usersUseCase.usersLiveData.value)
    }

    @Test
    fun testGetUsersFail() {
        //mock
        val serviceResponse = Resource.DataError<List<User>>(Error.DEFAULT_ERROR)
        coEvery { dataRepository?.requestUsers() } returns serviceResponse
        //call
        usersUseCase.getUsers()
        usersUseCase.usersLiveData.observeForever { }
        //assert test
        assert(Error.DEFAULT_ERROR == usersUseCase.usersLiveData.value?.errorCode)
    }

    @Test
    fun testGetUserDetailsSuccessful() {
        //mock
        userDetails = testModelsGenerator.generateUserDetailsModel()
        val fackID = 1
        val serviceResponse = Resource.Success(userDetails)
        coEvery { dataRepository?.requestUserDetails(fackID) } returns serviceResponse
        //call
        usersUseCase.getUserDetails(fackID)
        usersUseCase.userDetailsLiveData.observeForever { }
        //assert test
        assert(serviceResponse == usersUseCase.userDetailsLiveData.value)
    }

    @Test
    fun testGetUserDetailsFail() {
        //mock
        val serviceResponse = Resource.DataError<UserDetails>(Error.DEFAULT_ERROR)
        val fackID = 1
        coEvery { dataRepository?.requestUserDetails(fackID) } returns serviceResponse
        //call
        usersUseCase.getUserDetails(fackID)
        usersUseCase.userDetailsLiveData.observeForever { }
        //assert test
        assert(Error.DEFAULT_ERROR == usersUseCase.userDetailsLiveData.value?.errorCode)
    }

    @AfterEach
    fun tearDown() {
    }
}
