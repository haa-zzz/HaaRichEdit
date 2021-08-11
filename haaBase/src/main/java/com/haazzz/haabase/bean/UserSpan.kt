package com.haazzz.haabase.bean

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.text.TextPaint
import android.text.style.BulletSpan
import android.text.style.CharacterStyle
import android.text.style.ImageSpan


/**
 * 只是用来标识是否是重复按入
 */
class ListOrderedSpan : CharacterStyle() {
    override fun updateDrawState(tp: TextPaint?) {

    }
}
class RichImageSpan(context: Context?, b : Bitmap?, private val mUri: Uri) : ImageSpan(context!!, b!!) {
    override fun getSource(): String {
        return mUri.toString()
    }
}