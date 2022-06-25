package com.compact.app.extensions

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson

private fun Throwable.printErrorStackTraceToast(
    context: Context,
    onApiBadRequestException: ((errorBody: String) -> Unit)? = null,
    onApiInternalServerErrorException: ((message: String) -> Unit)? = null,
    onNetworkErrorException: (() -> Unit)? = null
) = printErrorStackTraceBase(
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

private fun Throwable.printErrorStackTraceBase(
    onApiBadRequestException: ((errorBody: String) -> Unit)? = null,
    onApiInternalServerErrorException: ((message: String) -> Unit)? = null,
    onNetworkErrorException: (() -> Unit)? = null
) {
    Log.e("HTTP", this.message, this)
    if (this is retrofit2.HttpException) {
        if (this.code() == 400) {
            onApiBadRequestException?.invoke(this.response()?.errorBody()?.string() ?: "")
        } else {
            onApiInternalServerErrorException?.invoke(this.message())
        }
    } else if (
        arrayListOf(
            java.net.SocketTimeoutException::class,
            java.net.ConnectException::class,
            java.net.UnknownHostException::class
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
) = printErrorStackTraceReifiedBase(
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
    onShowMessage: ((message: String) -> Unit) = {},
    onShowMessageRetry: ((message: String) -> Unit) = {},
    onApiBadRequestException: ((errorBody: T) -> Unit) = {},
    onApiInternalServerErrorException: ((message: String) -> Unit) = {},
    onNetworkErrorException: (() -> Unit) = {}
) = printErrorStackTraceReifiedBase(
    onApiBadRequestException = onApiBadRequestException,
    onApiInternalServerErrorException = {
        onShowMessage.invoke(it)
        onApiInternalServerErrorException.invoke(it)
    },
    onNetworkErrorException = {
        onShowMessageRetry.invoke("Lost Connection")
        onNetworkErrorException.invoke()
    }
)

inline fun <reified T> Throwable.printErrorStackTraceReifiedBase(
    onApiBadRequestException: ((errorBody: T) -> Unit) = {},
    onApiInternalServerErrorException: ((message: String) -> Unit) = {},
    onNetworkErrorException: (() -> Unit) = {}
) {
    Log.e("HTTP", this.message, this)
    if (this is retrofit2.HttpException) {
        if (this.code() == 400) {
            this.response()?.errorBody()?.string().run {
                if (this.isNullOrEmpty()) {
                    onApiInternalServerErrorException.invoke(this@printErrorStackTraceReifiedBase.message())
                } else {
                    onApiBadRequestException.invoke(Gson().fromJson(this, T::class.java))
                }
            }
        } else {
            onApiInternalServerErrorException.invoke(this.message())
        }
    } else if (
        arrayListOf(
            java.net.SocketTimeoutException::class,
            java.net.ConnectException::class,
            java.net.UnknownHostException::class
        ).contains(this::class)
    ) {
        onNetworkErrorException.invoke()
    }
}