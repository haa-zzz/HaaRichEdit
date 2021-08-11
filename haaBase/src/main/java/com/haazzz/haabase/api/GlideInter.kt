package com.haazzz.haabase.api

import android.graphics.Bitmap
import com.bumptech.glide.load.resource.gif.GifDrawable

interface GlideBitmap {
    fun getBitmap(bitmap: Bitmap?)
}

interface GlideGif {
    fun getGif(gif: GifDrawable?)
}