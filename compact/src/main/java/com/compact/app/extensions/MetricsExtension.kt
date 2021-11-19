package com.compact.app.extensions

import android.content.res.Resources
import android.util.TypedValue

val Float.dp: Int
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        Resources.getSystem().displayMetrics
    ).toInt()

val Int.dp: Int
    get() = this.toFloat().dp