package com.compact.databinding

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.compact.app.extensions.decodeBase64
import com.compact.app.extensions.isBase64

object ImageViewHelper {
    @JvmStatic
    @BindingAdapter(
        value = ["app:imageUrl", "app:placeholder", "app:error", "app:shape"],
        requireAll = false
    )
    fun setImageUrl(
        imageView: ImageView, url: String?, placeholder: Drawable?, error: Drawable?, shape: Shape?
    ) {
        var load: GlideRequest<Drawable> = when {
            url.isNullOrEmpty() -> {
                GlideApp.with(imageView).load("")
            }
            url.isBase64() -> {
                GlideApp.with(imageView).load(url.decodeBase64())
            }
            else -> {
                GlideApp.with(imageView).load(url)
            }
        }

        load = load.placeholder(placeholder).error(error)
            .transition(DrawableTransitionOptions.withCrossFade(100))
//            .override(64f.dp, 64f.dp)

        load = if (shape == Shape.CIRCLE) {
            load.circleCrop()
        } else {
            load.optionalFitCenter()
        }
        load.into(imageView)
    }

    @JvmStatic
    @BindingAdapter(value = ["app:src"])
    fun setImageRes(imageView: ImageView, @DrawableRes res: Int) {
        imageView.setImageResource(res)
    }

    enum class Shape {
        CIRCLE, RECTANGLE, ROUNDED, OVAL
    }
}