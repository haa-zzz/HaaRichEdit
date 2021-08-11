package com.haazzz.haabase.api

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

/*
通过Glide加载 把 uri转换为 BitMap
 */
fun uriToBitMap(context: Context, uri: Uri, glideBitmap: GlideBitmap) {

    Glide.with(context)
        .asBitmap()
        .load(uri)
        .into(object : CustomTarget<Bitmap?>() {
            override fun onResourceReady(
                resource: Bitmap,
                transition: Transition<in Bitmap?>?
            ) {
                glideBitmap.getBitmap(resource)

            }
            override fun onLoadCleared(placeholder: Drawable?) {
            }
        })

}

fun urlToBitmap(context: Context, url: String, glideBitmap: GlideBitmap) {

    Glide.with(context)
        .asBitmap()
        .load(url)
        .into(object : CustomTarget<Bitmap?>() {
            override fun onResourceReady(
                resource: Bitmap,
                transition: Transition<in Bitmap?>?
            ) {

                glideBitmap.getBitmap(resource)
            }

            override fun onLoadCleared(placeholder: Drawable?) {
            }
        })
}

/*
通过Glide 把 path 转换为 Gif
 */
fun urlToGif(context: Context, url: String, glideGif: GlideGif) {
    Glide.with(context)
        .asGif()
        .load(url)
        .into(object : CustomTarget<GifDrawable?>() {
            override fun onResourceReady(
                resource: GifDrawable,
                transition: Transition<in GifDrawable?>?
            ) {
                glideGif.getGif(resource)
            }

            override fun onLoadCleared(placeholder: Drawable?) {

            }

        })
}