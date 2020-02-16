package com.task.ui.base

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.task.R
import com.task.ui.base.listeners.ActionBarView
import com.task.ui.base.listeners.BaseView
import com.task.ui.base.listeners.MainActionActionBar
import com.task.utils.ResourcesUtil
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Created by AhmedEltaher on 5/12/2016
 */


abstract class BaseActivity : AppCompatActivity(), BaseView, ActionBarView {

    protected lateinit var baseViewModel: BaseViewModel

    abstract val layoutId: Int

    protected abstract fun initializeViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
        initializeToolbar()
        initializeViewModel()
        observeViewModel()
    }

    private fun initializeToolbar() {
        if (toolbar != null) {
            setSupportActionBar(toolbar)
            supportActionBar?.title = ""
        }
    }

    override fun setUpIconVisibility(visible: Boolean) {
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(visible)
    }

    override fun setTitle(titleKey: String) {
        val actionBar = supportActionBar
        if (actionBar != null) {
            val title = findViewById<TextView>(R.id.txt_toolbar_title)
            title?.text = titleKey
        }
    }

    override fun setMainAction(mainAction: MainActionActionBar) {
        val actionBar = supportActionBar
        if (actionBar != null) {
            val icon = findViewById<ImageView>(R.id.ic_toolbar_main_action)
            var iconDrawable: Drawable = ResourcesUtil.getDrawable(R.drawable.ic_arrow_back_white_24dp)
            when (mainAction) {
                MainActionActionBar.BACK -> {
                    iconDrawable = ResourcesUtil.getDrawable(R.drawable.ic_arrow_back_white_24dp)
                    icon.setOnClickListener { onBackClicked() }
                }
                MainActionActionBar.REFRESH -> {
                    iconDrawable = ResourcesUtil.getDrawable(R.drawable.ic_sync_white_24dp)
                    icon.setOnClickListener { onRefreshed() }
                }
            }
            icon.setImageDrawable(iconDrawable)
        }
    }

    override fun showMainAction(visibility: Boolean) {
        val actionBar = supportActionBar
        if (actionBar != null) {
            val icon = findViewById<ImageView>(R.id.ic_toolbar_main_action)
            icon?.visibility = if (visibility) VISIBLE else GONE
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    open fun onRefreshed() {

    }

    fun onBackClicked() {
        this.finish()
    }

    abstract fun observeViewModel()
}
