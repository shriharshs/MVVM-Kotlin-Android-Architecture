package com.task.ui.base.listeners

/**
 * Created by AhmedEltaher on 5/12/2016
 */

interface ActionBarView {

    fun setUpIconVisibility(visible: Boolean)

    fun setTitle(titleKey: String)

    fun setMainAction(mainAction: MainActionActionBar)

    fun showMainAction(visibility: Boolean)
}
