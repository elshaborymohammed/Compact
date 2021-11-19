package com.compact.app.extensions

import android.widget.AdapterView
import android.widget.AutoCompleteTextView
import android.widget.Filterable
import android.widget.ListAdapter
import androidx.annotation.ArrayRes
import com.compact.app.BaseArrayAdapter
import com.google.android.material.textfield.TextInputLayout

fun <T> TextInputLayout.setAdapter(
    adapter: T,
    onItemClickListener: AdapterView.OnItemClickListener? = null
) where T : ListAdapter, T : Filterable {
    if (editText is AutoCompleteTextView) {
        (editText as AutoCompleteTextView).let {
            it.threshold = 1 //will start working from first character
            it.setAdapter(adapter)
            it.onItemClickListener = onItemClickListener
            this.setEndIconOnClickListener { _ ->
//                adapter.filter?.filter(null)
                it.showDropDown()
            }
            it.setOnTouchListener { _, _ ->
//                adapter?.filter?.filter(null)
                it.showDropDown()
                false
            }
        }
    }
}

fun <T> TextInputLayout.setAdapter(objects: List<T>) {
    setAdapter(
        BaseArrayAdapter(
            context = context,
            objects = objects
        )
    )
}

fun <T> TextInputLayout.setAdapter(objects: Array<T>) {
    setAdapter(
        BaseArrayAdapter<T>(
            context = context,
            objects = objects
        )
    )
}

fun TextInputLayout.setAdapter(@ArrayRes arrayRes: Int) {
    setAdapter(
        BaseArrayAdapter<String>(
            context = context,
            objects = resources.getStringArray(arrayRes)
        )
    )
}

fun List<String>?.isBlack(): Boolean {
    return when {
        isNullOrEmpty() -> {
            true
        }
        this.contains("-1") -> {
            true
        }
        else -> this.isEmpty()
    }
}
