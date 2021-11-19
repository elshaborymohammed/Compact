package com.compact.app.extensions

import android.util.Base64
import android.util.Patterns
import java.util.regex.Pattern

/**
 * CharSequence Extensions.
 * @author Mohammed Elshabory
 */

fun CharSequence.isPassword(): Boolean {
    return matches(Regex("^.{8,}\$"))
}

fun CharSequence.limit(min: Int, max: Int): Boolean {
    return matches(Regex("^.{$min,$max}\$"))
}

fun CharSequence.isEmail(): Boolean {
    return matches(Patterns.EMAIL_ADDRESS.toRegex())
}

fun CharSequence.isPhone(): Boolean {
    return matches(Pattern.compile("^[0-9]{10}$").toRegex())
}

fun CharSequence.isDigits(): Boolean {
    return matches(
        Regex(
//        "^[1-9][0-9]*\\.?\\d\$"
            "^\\d*\\.?\\d*\$"
        )
    )
}

fun CharSequence.isPrice(): Boolean {
    return matches(Regex("^([1-9][0-9]*(\\.[0-9]+)?|0+\\.[0-9]*[1-9][0-9]*)\$"))
}

fun CharSequence.isUserName(): Boolean {
    return matches(Pattern.compile("^[a-zA-Z]+$").toRegex())
}

fun CharSequence.isFullName(): Boolean {
    return matches(Pattern.compile("^[A-Za-z][A-Za-z\\s]+$").toRegex())
}

fun CharSequence.isNotNullOrEmpty(): Boolean {
    return !isNullOrEmpty()
}

fun CharSequence.isBase64(): Boolean {
    return matches(
        Pattern.compile("^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{4}|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)$")
            .toRegex()
    )
}

fun CharSequence.decodeBase64(): ByteArray? {
    return Base64.decode(toString().replace("data:image/png;base64,", ""), 0)
}