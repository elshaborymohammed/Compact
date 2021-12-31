package com.compact.app.extensions

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import retrofit2.HttpException
import timber.log.Timber
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

private fun Throwable.printErrorStackTrace(
    context: Context,
    onApiBadRequestException: ((errorBody: String) -> Unit)? = null,
    onApiInternalServerErrorException: ((message: String) -> Unit)? = null,
    onNetworkErrorException: (() -> Unit)? = null
) = printErrorStackTrace(
    onApiBadRequestException = onApiBadRequestException,
    onApiInternalServerErrorException = {
        Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        onApiInternalServerErrorException?.invoke(it)
    },
    onNetworkErrorException = {
        Toast.makeText(context, "Lost Connection", Toast.LENGTH_LONG).show()
        onNetworkErrorException?.invoke()
    }
)

private fun Throwable.printErrorStackTrace(
    onApiBadRequestException: ((errorBody: String) -> Unit)? = null,
    onApiInternalServerErrorException: ((message: String) -> Unit)? = null,
    onNetworkErrorException: (() -> Unit)? = null
) {
    Timber.e(this, this.message)
    if (this is HttpException) {
        if (this.code() == 400) {
            onApiBadRequestException?.invoke(this.response()?.errorBody()?.string() ?: "")
        } else {
            onApiInternalServerErrorException?.invoke(this.message())
        }
    } else if (
        arrayListOf(
            SocketTimeoutException::class,
            ConnectException::class,
            UnknownHostException::class
        ).contains(this::class)
    ) {
        onNetworkErrorException?.invoke()
    }
}


inline fun <reified T> Throwable.printErrorStackTraceReified(
    onShowMessage: ((message: String) -> Unit) = {},
    onApiBadRequestException: ((errorBody: T) -> Unit) = {},
    onApiInternalServerErrorException: ((message: String) -> Unit) = {},
    onNetworkErrorException: (() -> Unit) = {}
) = printErrorStackTraceReified(
    onApiBadRequestException = onApiBadRequestException,
    onApiInternalServerErrorException = {
        onShowMessage.invoke(it)
        onApiInternalServerErrorException.invoke(it)
    },
    onNetworkErrorException = {
        onShowMessage.invoke("Lost Connection")
        onNetworkErrorException.invoke()
    }
)


inline fun <reified T> Throwable.printErrorStackTraceReified(
    onApiBadRequestException: ((errorBody: T) -> Unit) = {},
    onApiInternalServerErrorException: ((message: String) -> Unit) = {},
    onNetworkErrorException: (() -> Unit) = {}
) {
    Timber.e(this, this.message)
    if (this is HttpException) {
        if (this.code() == 400) {
            this.response()?.errorBody()?.string().run {
                if (this.isNullOrEmpty()) {
                    onApiInternalServerErrorException.invoke(this@printErrorStackTraceReified.message())
                } else {
                    onApiBadRequestException.invoke(Gson().fromJson(this, T::class.java))
                }
            }
        } else {
            onApiInternalServerErrorException.invoke(this.message())
        }
    } else if (
        arrayListOf(
            SocketTimeoutException::class,
            ConnectException::class,
            UnknownHostException::class
        ).contains(this::class)
    ) {
        onNetworkErrorException.invoke()
    }
}