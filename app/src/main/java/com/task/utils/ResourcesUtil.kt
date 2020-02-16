package com.task.utils

import android.graphics.drawable.Drawable
import android.os.Build.VERSION.SDK_INT
import com.task.App

/**
 * Created by AhmedEltaher on 05/12/16.
 */

object ResourcesUtil {
    private val context = App.context
    private val theme = App.context.theme
    var LOLLIPOP = 21

    fun getDrawable(resId: Int): Drawable {
        return if (SDK_INT >= LOLLIPOP)
            context.resources.getDrawable(resId, theme)
        else
            context.resources.getDrawable(resId)
    }
}
