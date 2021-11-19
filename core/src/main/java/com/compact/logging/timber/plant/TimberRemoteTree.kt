package com.compact.logging.timber.plant

import android.os.Build
import android.util.Log
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

class TimberRemoteTree(
    private val deviceDetails: DeviceDetails,
    private val onLog: ((deviceDetails: DeviceDetails, log: TimberLog) -> Unit)
) : Timber.DebugTree() {

    private val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    private val timeFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS a zzz", Locale.getDefault())
    private val date = dateFormat.format(Date(System.currentTimeMillis()))

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        val timestamp = System.currentTimeMillis()
        val time = timeFormat.format(Date(timestamp))
        val log = TimberLog(priorityAsString(priority), tag, message, t, date, time)
        onLog.invoke(deviceDetails, log)
    }

    private fun priorityAsString(priority: Int): Priority = when (priority) {
        Log.VERBOSE -> Priority.VERBOSE
        Log.DEBUG -> Priority.DEBUG
        Log.INFO -> Priority.INFO
        Log.WARN -> Priority.WARN
        Log.ERROR -> Priority.ERROR
        Log.ASSERT -> Priority.ASSERT
        else -> Priority.VERBOSE
    }

    enum class Priority(val priority: Int, val message: String) {
        VERBOSE(Log.VERBOSE, "VERBOSE"),
        DEBUG(Log.DEBUG, "DEBUG"),
        INFO(Log.INFO, "INFO"),
        WARN(Log.WARN, "WARN"),
        ERROR(Log.ERROR, "ERROR"),
        ASSERT(Log.ASSERT, "ASSERT")
    }

    data class TimberLog(
        var priority: Priority,
        var tag: String?,
        var message: String,
        var cause: Throwable?,
        val date: String,
        val time: String
    )

    data class DeviceDetails(
        val deviceId: String = Build.ID,
        val osVersion: String = Build.VERSION.RELEASE,
        val manufacturer: String = Build.MANUFACTURER,
        val brand: String = Build.BRAND,
        val device: String = Build.DEVICE,
        val model: String = Build.MODEL,
        val appVersionName: String,
        val appVersionCode: Int
    )
}