package com.task.ui.component.users

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.task.data.Resource
import com.task.data.error.Error
import com.task.usecase.UsersUseCase
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

    private lateinit var newsTitle: String
    private val testModelsGenerator: TestModelsGenerator = TestModelsGenerator()

    @Before
    fun setUp() {
        // Create class under test
        // We initialise the repository with no tasks
        newsTitle = testModelsGenerator.getStupSearchTitle()
        val newsModelSuccess = MutableLiveData<Resource<NewsModel>>()
        every { usersUseCase.usersLiveData } returns newsModelSuccess
    }

    @Test
    fun handleNewsList() {
        // Let's do an answer for the liveData
        val newsModeltest = testModelsGenerator.generateNewsModel()
        val newsModelSuccess = MutableLiveData<Resource<NewsModel>>()
        newsModelSuccess.value = Resource.Success(newsModeltest)

        //1- Mock calls
        every { usersUseCase.getUsers() } just Runs
        every { usersUseCase.usersLiveData } returns newsModelSuccess

        //2-Call
        usersListViewModel = UsersListViewModel(usersUseCase)
        usersListViewModel.getNews()
        //active observer for livedata
        usersUseCase.usersLiveData.observeForever { }

        //3-verify
        val isEmptyList = usersListViewModel.usersLiveData.value?.data?.newsItems.isNullOrEmpty()
        assert(newsModeltest == usersListViewModel.usersLiveData.value?.data)
        assert(!isEmptyList)
    }

    @Test
    fun handleEmptyList() {
        // Let's do an answer for the liveData
        val newsModeltest = testModelsGenerator.generateNewsModelWithEmptyList()
        val newsModelSuccess = MutableLiveData<Resource<NewsModel>>()
        newsModelSuccess.value = Resource.Success(newsModeltest)

        //1- Mock calls
        every { usersUseCase.getUsers() } just Runs
        every { usersUseCase.usersLiveData } returns newsModelSuccess

        //2-Call
        usersListViewModel = UsersListViewModel(usersUseCase)
        usersListViewModel.getNews()
        //active observer for livedata
        usersUseCase.usersLiveData.observeForever { }

        //3-verify
        val isEmptyList = usersListViewModel.usersLiveData.value?.data?.newsItems.isNullOrEmpty()
        assert(newsModeltest == usersListViewModel.usersLiveData.value?.data)
        assert(isEmptyList)
    }

    @Test
    fun handleNewsError() {
        // Let's do an answer for the liveData
        val newsModelFail = MutableLiveData<Resource<NewsModel>>()
        newsModelFail.value = Resource.DataError(Error.NETWORK_ERROR)

        //1- Mock calls
        every { usersUseCase.getUsers() } just Runs
        every { usersUseCase.usersLiveData } returns newsModelFail

        //2-Call
        usersListViewModel = UsersListViewModel(usersUseCase)
        usersListViewModel.getNews()
        //active observer for livedata
        usersUseCase.usersLiveData.observeForever { }

        //3-verify
        assert(Error.NETWORK_ERROR == usersListViewModel.usersLiveData.value?.errorCode)
    }

    @Test
    fun testSearchSuccess() {

        // Let's do an answer for the liveData
        val newsItem = testModelsGenerator.generateNewsItemModel()
        val title = newsItem.title
        //1- Mock calls
        every { usersUseCase.searchByTitle(title) } returns newsItem

        //2-Call
        usersListViewModel = UsersListViewModel(usersUseCase)
        usersListViewModel.onSearchClick(title)
        //active observer for livedata
        usersListViewModel.newsSearchFound.observeForever { }

        //3-verify
        assert(usersListViewModel.newsSearchFound.value == newsItem)
    }

    @Test
    fun testSearchFail() {

        // Let's do an answer for the liveData
        val title = "*&*^%"

        //1- Mock calls
        every { usersUseCase.searchByTitle(title) } returns null

        //2-Call
        usersListViewModel = UsersListViewModel(usersUseCase)
        usersListViewModel.onSearchClick(title)
        //active observer for livedata
        usersListViewModel.noSearchFound.observeForever { }

        //3-verify
        assert(usersListViewModel.noSearchFound.value == Unit)
    }
}