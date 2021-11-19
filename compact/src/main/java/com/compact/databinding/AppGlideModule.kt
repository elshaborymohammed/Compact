package com.compact.databinding

import android.content.Context
import android.graphics.drawable.PictureDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.caverock.androidsvg.SVG
import com.compact.databinding.svg.SvgDecoder
import com.compact.databinding.svg.SvgDrawableTranscoder
import java.io.InputStream
import com.bumptech.glide.load.model.GlideUrl

@GlideModule
open class AppGlideModule : AppGlideModule() {

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        registry.register(
            SVG::class.java,
            PictureDrawable::class.java, SvgDrawableTranscoder()
        ).append(InputStream::class.java, SVG::class.java, SvgDecoder())
    }

    // Disable manifest parsing to avoid adding similar modules twice.
    override fun isManifestParsingEnabled(): Boolean {
        return false
    }
}