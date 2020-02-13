package com.task.usecase

import androidx.lifecycle.MutableLiveData
import com.task.data.DataSource
import com.task.data.Resource
import com.task.data.error.Error.Companion.NETWORK_ERROR
import com.task.data.remote.dto.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


/**
 * Created by AhmedEltaher on 5/12/2016
 */

class UsersUseCase @Inject
constructor(private val dataRepository: DataSource, override val coroutineContext: CoroutineContext) : UseCase, CoroutineScope {
    private val usersMutableLiveData = MutableLiveData<Resource<List<User>>>()
    override val usersLiveData: MutableLiveData<Resource<List<User>>> = usersMutableLiveData


    override fun getUsers() {
        var serviceResponse: Resource<List<User>>?
        usersMutableLiveData.postValue(Resource.Loading())
        launch {
            try {
                serviceResponse = dataRepository.requestUsers()
                usersMutableLiveData.postValue(serviceResponse)
            } catch (e: Exception) {
                usersMutableLiveData.postValue(Resource.DataError(NETWORK_ERROR))
            }
        }
    }

    override fun searchByTitle(keyWord: String): User? {
//        val news = usersMutableLiveData.value?.data?.newsItems
//        if (!news.isNullOrEmpty()) {
//            for (newsItem in news) {
//                if (newsItem.title.isNotEmpty() && newsItem.title.toLowerCase().contains(keyWord.toLowerCase())) {
//                    return newsItem
//                }
//            }
//        }
        return null
    }
}
