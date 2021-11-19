package com.compact.app.extensions

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.util.Base64
import android.widget.ImageView
import androidx.core.graphics.drawable.toBitmap
import com.compact.helper.ImageHelper
import java.io.ByteArrayOutputStream

fun ImageView.base64(): String? {
    return drawable?.toBase64()
}

fun ImageView.setImageBase64(base64: String) {
    setImageBitmap(decodeBase64ToBitmap(base64))
}

fun Drawable.toBase64(): String? {
    return encodeBitmapToBase64(toBitmap())
}

fun Bitmap.toBase64(): String {
    return "data:image/png;base64,${ImageHelper.encodeBitmapToBase64(this)}"
}

private fun encodeBitmapToBase64(bitmap: Bitmap): String? {
    val byteArrayOutputStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream) //bm is the bitmap object
    val b = byteArrayOutputStream.toByteArray()
    return "data:image/png;base64,${Base64.encodeToString(b, Base64.DEFAULT)}"
}

private fun decodeBase64ToBitmap(input: String): Bitmap? {
    val decodedBytes = Base64.decode(input.replace("data:image/png;base64,", ""), 0)
    return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
}