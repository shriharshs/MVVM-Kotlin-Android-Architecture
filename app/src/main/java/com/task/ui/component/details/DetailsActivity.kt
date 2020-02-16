package com.task.ui.component.details

import android.os.Bundle
import com.squareup.picasso.Picasso
import com.task.R
import com.task.data.remote.dto.UserDetails
import com.task.ui.ViewModelFactory
import com.task.ui.base.BaseActivity
import com.task.utils.Constants
import com.task.utils.observe
import kotlinx.android.synthetic.main.details_layout.*
import kotlinx.android.synthetic.main.details_layout.tv_user_name
import kotlinx.android.synthetic.main.user_list_item.*
import javax.inject.Inject

/**
 * Created by AhmedEltaher on 11/12/16.
 */

class DetailsActivity : BaseActivity() {

    @Inject
    lateinit var viewModel: DetailsViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override val layoutId: Int
        get() = R.layout.details_layout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.userDetails.value = intent.getParcelableExtra(Constants.USER_KEY)
    }

    override fun observeViewModel() {
        observe(viewModel.userDetails, ::initializeView)
    }

    override fun initializeViewModel() {
        viewModel = viewModelFactory.create(viewModel::class.java)
    }

    private fun initializeView(userDetails: UserDetails) {
        tv_user_name.text = userDetails.name
        userDetails.avatarUrl?.let {
            Picasso.get().load(it)
                    .resize(480, 480)
                    .centerInside()
                    .onlyScaleDown()
                    .placeholder(R.drawable.github_logo)
                    .error(R.drawable.github_logo)
                    .into(iv_user_avatar)
            tv_location.text = userDetails.location
            tv_account_created.text = userDetails.createdAt
            tv_account_updated.text = userDetails.updatedAt
            tv_followers.text = userDetails.followers.toString()
            tv_public_repositories.text = userDetails.publicRepos.toString()
            tv_public_gists.text = userDetails.publicGists.toString()
        }
    }
}
