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
import com.task.data.remote.dto.UserDetails
import com.task.ui.ViewModelFactory
import com.task.ui.base.BaseActivity
import com.task.ui.base.listeners.MainActionActionBar
import com.task.ui.component.details.DetailsActivity
import com.task.ui.component.users.usersListAdapter.UsersAdapter
import com.task.utils.*
import kotlinx.android.synthetic.main.users_activity.*
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
        get() = R.layout.users_activity

    val countingIdlingResource: IdlingResource
        @VisibleForTesting
        get() = EspressoIdlingResource.idlingResource

    override fun initializeViewModel() {
        usersListViewModel = viewModelFactory.create(UsersListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setMainAction(MainActionActionBar.REFRESH)
        val layoutManager = LinearLayoutManager(this)
        rv_users_list.layoutManager = layoutManager
        rv_users_list.setHasFixedSize(true)
        usersListViewModel.getUsers()
    }

    private fun bindListData(users: List<User>) {
        if (!(users.isNullOrEmpty())) {
            val newsAdapter = UsersAdapter(usersListViewModel, users)
            rv_users_list.adapter = newsAdapter
            showDataView(true)
        } else {
            showDataView(false)
        }
        EspressoIdlingResource.decrement()
    }

    private fun navigateToDetailsScreen(navigateEvent: Event<UserDetails>) {
        navigateEvent.getContentIfNotHandled()?.let {
            startActivity(intentFor<DetailsActivity>(Constants.USER_KEY to it))
        }
    }

    private fun observeSnackBarMessages(event: LiveData<Event<Int>>) {
        root.setupSnackbar(this, event, Snackbar.LENGTH_LONG)
    }

    private fun observeToast(event: LiveData<Event<Any>>) {
        root.showToast(this, event, Snackbar.LENGTH_LONG)
    }

    private fun showSearchError() {
        usersListViewModel.showSnackbarMessage(R.string.search_error)
    }

    private fun showDataView(show: Boolean) {
        tv_no_data.visibility = if (show) GONE else VISIBLE
        rv_users_list.visibility = if (show) VISIBLE else GONE
        pb_loading.toGone()
    }

    private fun showLoadingView() {
        pb_loading.toVisible()
        tv_no_data.toGone()
        rv_users_list.toGone()
        EspressoIdlingResource.increment()
    }

    private fun handleUsersList(newsModel: Resource<List<User>>) {
        when (newsModel) {
            is Resource.Loading -> showLoadingView()
            is Resource.Success -> newsModel.data?.let { bindListData(users = it) }
            is Resource.DataError -> {
                showDataView(false)
                newsModel.errorCode?.let { usersListViewModel.showToastMessage(it) }
            }
        }
    }

    private fun getUserDetails(userDetails: Resource<UserDetails>) {
        when (userDetails) {
            is Resource.Loading -> {
                showLoader(true)
            }
            is Resource.Success -> userDetails.data?.let {
                showLoader(false)
                usersListViewModel.openUserDetails(it)


            }
            is Resource.DataError -> {
                showLoader(false)
                userDetails.errorCode?.let { usersListViewModel.showToastMessage(it) }
            }
        }
    }

    fun showLoader(show:Boolean){
        if(show){
            pb_loading.toVisible()
            EspressoIdlingResource.increment()
        } else {
            pb_loading.toGone()
            EspressoIdlingResource.decrement()
        }
    }

    override fun observeViewModel() {
        observe(usersListViewModel.usersLiveData, ::handleUsersList)
        observe(usersListViewModel.userDetailsLiveData, ::getUserDetails)
        observeEvent(usersListViewModel.openUserDetails, ::navigateToDetailsScreen)
        observeSnackBarMessages(usersListViewModel.showSnackBar)
        observeToast(usersListViewModel.showToast)

    }

    override fun onRefreshed() {
        usersListViewModel.getUsers()
    }
}
