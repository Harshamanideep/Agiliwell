package com.app.agiliwell.utils

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import kotlin.math.ceil


object UIUtils {
    val Int.dpf: Float
        get() {
            return dp.toFloat()
        }


    val Float.dpf: Float
        get() {
            return dp.toFloat()
        }

    val Int.dp: Int
        get() {
            return if (this == 0) {
                0
            } else ceil((Resources.getSystem().displayMetrics.density * this).toDouble()).toInt()
        }

    val Float.dp: Int
        get() {
            return if (this == 0f) {
                0
            } else ceil((Resources.getSystem().displayMetrics.density * this).toDouble()).toInt()
        }
}