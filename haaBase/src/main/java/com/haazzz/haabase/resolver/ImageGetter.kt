package com.haazzz.haabase.resolver

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.text.Html
import android.widget.TextView
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.haazzz.haabase.R
import com.haazzz.haabase.api.GlideBitmap
import com.haazzz.haabase.api.GlideGif
import com.haazzz.haabase.api.urlToBitmap
import com.haazzz.haabase.api.urlToGif
import com.haazzz.haabase.util.dip2px
import com.haazzz.haabase.util.getScreenSize
import com.haazzz.haabase.util.isGif

/**
 * Author : Haa-zzz
 * Time : 2021/8/9
 * 解析图片
 */
class ImageGetter(private val mContext: Context, private val mTextView: TextView) : Html.ImageGetter {

    init {
        mTextView.setTag(R.id.img_tag, this)
    }

    override fun getDrawable(url: String): Drawable {

        val myDrawableWrapper = MyDrawableWrapper()
        if(isGif(url)){
            urlToGif(mContext,url,object  : GlideGif{
                override fun getGif(gif: GifDrawable?) {
                    setGifWrapper(gif,myDrawableWrapper)
                }
            })
        }else{
            urlToBitmap(mContext,url,object : GlideBitmap{
                override fun getBitmap(bitmap: Bitmap?) {
                    bitmap?:return
                    //val newBitmap = zoomBitmapToFixWidth(bitmap, mTextView.measuredWidth - mTextView.paddingLeft - mTextView.paddingRight )
                    setBitMapWrapper(bitmap,myDrawableWrapper)
                }
            })
        }
        return myDrawableWrapper
    }
    private fun  setGifWrapper(resource: GifDrawable?,myDrawableWrapper:MyDrawableWrapper){
        resource?:return
        val w = getScreenSize(mContext).x
        val hh = resource.intrinsicHeight
        val ww = resource.intrinsicWidth
        val high = hh * (w - 50) / ww
        val rect = Rect(20, 20, w - 30, high)
        resource.bounds = rect

        myDrawableWrapper.bounds = rect
        myDrawableWrapper.drawable = resource
        resource.callback = mTextView
        resource.start()
        mTextView.text = mTextView.text
        mTextView.invalidate()
    }

    private fun  setBitMapWrapper(resource: Bitmap?,myDrawableWrapper:MyDrawableWrapper) {


        val drawable: Drawable = BitmapDrawable(mContext.resources, resource)

        val w = getScreenSize(mContext).x

        val hh = drawable.intrinsicHeight
        val ww = drawable.intrinsicWidth

        val padding = dip2px(mContext, 0f)
        val high = hh * (w - padding) / ww

        val rect = Rect(padding, 0, w - padding, high)

        drawable.bounds = rect
        myDrawableWrapper.bounds = rect
        myDrawableWrapper.drawable = drawable

        mTextView.text = mTextView.text
        mTextView.invalidate()
    }

    class MyDrawableWrapper : BitmapDrawable() {
        var drawable: Drawable? = null
        override fun draw(canvas: Canvas) {
            if (drawable != null) drawable!!.draw(canvas)
        }
    }

}