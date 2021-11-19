package com.compact.app.extensions

import android.annotation.SuppressLint
import android.content.res.Resources
import android.os.Build
import android.view.View
import androidx.annotation.StyleRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import com.compact.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat

@SuppressLint("ResourceType")
fun MaterialButton.setTextSelect() {
    if (isSelected) {
        setTextColor(
            ContextCompat.getColor(context, androidx.appcompat.R.attr.colorPrimaryDark)
        )
    } else {
        setTextColor(
            ContextCompat.getColor(context, androidx.appcompat.R.attr.colorPrimaryDark)
        )
    }
}

@SuppressLint("ResourceType")
fun View.setMaterialButtonTextSelect() {
    if (isSelected) {
        (this as MaterialButton).setTextColor(
            ContextCompat.getColor(context, androidx.appcompat.R.attr.colorPrimaryDark)
        )
    } else {
        (this as MaterialButton).setTextColor(
            ContextCompat.getColor(context, androidx.appcompat.R.attr.colorAccent)
        )
    }
}

fun View.setVisibility(visibility: Boolean) {
    setVisibility(if (visibility) View.VISIBLE else View.GONE)
}

fun View.showDatePicker(
    fragmentManager: FragmentManager,
    @StyleRes theme: Int,
    onPositiveButtonClickListener: ((it: MaterialTimePicker) -> Unit)? = null
) {
    setOnClickListener {
        MaterialTimePicker.Builder()
            .setTitleText("Select Appointment time")
            .setTimeFormat(TimeFormat.CLOCK_12H)
            .build().also {
                onPositiveButtonClickListener?.apply {
                    it.addOnPositiveButtonClickListener { _ ->
                        onPositiveButtonClickListener.invoke(it)
                    }
                }
            }.showNow(fragmentManager, "date-picker")
    }
}