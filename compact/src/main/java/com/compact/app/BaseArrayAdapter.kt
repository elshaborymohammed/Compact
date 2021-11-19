package com.compact.app

import android.content.Context
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Filter
import java.util.*

open class BaseArrayAdapter<T> : ArrayAdapter<T> {
    private val data: List<T>

    constructor(
        context: Context,
        resource: Int = android.R.layout.simple_spinner_dropdown_item,
        textViewResourceId: Int = android.R.id.text1,
        objects: List<T>
    ) : super(context, resource, textViewResourceId, objects) {
        data = ArrayList(objects)
    }

    constructor(
        context: Context,
        resource: Int = android.R.layout.simple_spinner_dropdown_item,
        textViewResourceId: Int = android.R.id.text1,
        objects: Array<T>
    ) : super(context, resource, textViewResourceId, objects) {
        data = listOf(*objects)
    }

    open fun convertToString(resultValue: Any?): CharSequence {
        return resultValue?.toString() ?: ""
    }

    override fun getFilter(): Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val results = FilterResults()
            if (constraint.isNullOrEmpty()) {
                results.values = data
                results.count = data.size
            } else {
                val filterPattern = constraint.toString().lowercase(Locale.getDefault()).trim()
                data.filter {
                    it.toString().lowercase(Locale.getDefault()).contains(filterPattern)
                }.apply {
                    results.values = this
                    results.count = this.size
                }
            }

            return results
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults) {
            try {
                clear()
                addAll(results.values as List<T>)
                notifyDataSetChanged()
            } catch (e: Exception) {
                Log.i("BaseArrayAdapter", "publishResults: ", e)
            }
        }

        override fun convertResultToString(resultValue: Any?): CharSequence {
            return convertToString(resultValue)
        }
    }
}