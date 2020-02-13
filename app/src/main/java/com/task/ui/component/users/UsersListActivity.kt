package com.task.ui.component.users

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.test.espresso.IdlingResource
import com.google.android.material.snackbar.Snackbar
import com.task.R
import com.task.data.Resource
import com.task.data.remote.dto.User
import com.task.ui.ViewModelFactory
import com.task.ui.base.BaseActivity
import com.task.ui.component.details.DetailsActivity
import com.task.ui.component.users.newsAdapter.UsersAdapter
import com.task.utils.*
import kotlinx.android.synthetic.main.home_activity.*
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.intentFor
import javax.inject.Inject

/**
 * Created by AhmedEltaher on 5/12/2016
 */

class UsersListActivity : BaseActivity() {
    @Inject
    lateinit var usersListViewModel: UsersListViewModel
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override val layoutId: Int
        get() = R.layout.home_activity

    val countingIdlingResource: IdlingResource
        @VisibleForTesting
        get() = EspressoIdlingResource.idlingResource

    override fun initializeViewModel() {
        usersListViewModel = viewModelFactory.create(UsersListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ic_toolbar_refresh.setOnClickListener {
            usersListViewModel.getNews()
        }
        btn_search.setOnClickListener {
            if (!(et_search.text?.toString().isNullOrEmpty())) {
                pb_loading.visibility = VISIBLE
                usersListViewModel.onSearchClick(et_search.text?.toString()!!)
            }
        }
        val layoutManager = LinearLayoutManager(this)
        rv_news_list.layoutManager = layoutManager
        rv_news_list.setHasFixedSize(true)
        usersListViewModel.getNews()
    }

    private fun bindListData(users: List<User>) {
        if (!(users.isNullOrEmpty())) {
            val newsAdapter = UsersAdapter(usersListViewModel, users)
            rv_news_list.adapter = newsAdapter
            showDataView(true)
        } else {
            showDataView(false)
        }
        EspressoIdlingResource.decrement()
    }

    private fun navigateToDetailsScreen(navigateEvent: Event<User>) {
        navigateEvent.getContentIfNotHandled()?.let {
            startActivity(intentFor<DetailsActivity>(Constants.USER_KEY to it))
        }
    }

    private fun observeSnackBarMessages(event: LiveData<Event<Int>>) {
        rl_users_list.setupSnackbar(this, event, Snackbar.LENGTH_LONG)
    }

    private fun observeToast(event: LiveData<Event<Any>>) {
        rl_users_list.showToast(this, event, Snackbar.LENGTH_LONG)
    }

    private fun showSearchError() {
        usersListViewModel.showSnackbarMessage(R.string.search_error)
    }

    private fun showDataView(show: Boolean) {
        tv_no_data.visibility = if (show) GONE else VISIBLE
        rl_users_list.visibility = if (show) VISIBLE else GONE
        pb_loading.toGone()
    }

    private fun showLoadingView() {
        pb_loading.toVisible()
        tv_no_data.toGone()
        rl_users_list.toGone()
        EspressoIdlingResource.increment()
    }


    private fun showSearchResult(newsItem: User) {
        usersListViewModel.openUserDetails(newsItem)
        pb_loading.toGone()
    }

    private fun noSearchResult(unit: Unit) {
        showSearchError()
        pb_loading.toGone()
    }

    private fun handleNewsList(newsModel: Resource<List<User>>) {
        when (newsModel) {
            is Resource.Loading -> showLoadingView()
            is Resource.Success -> newsModel.data?.let { bindListData(users = it) }
            is Resource.DataError -> {
                showDataView(false)
                newsModel.errorCode?.let { usersListViewModel.showToastMessage(it) }
            }
        }

    }

    override fun observeViewModel() {
        observe(usersListViewModel.usersLiveData, ::handleNewsList)
        observeEvent(usersListViewModel.openNewsDetails, ::navigateToDetailsScreen)
        observeSnackBarMessages(usersListViewModel.showSnackBar)
        observeToast(usersListViewModel.showToast)

    }
}
