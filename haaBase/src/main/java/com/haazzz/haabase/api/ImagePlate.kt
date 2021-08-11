package com.haazzz.haabase.api

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.text.SpannableString
import android.text.Spanned
import com.haazzz.haabase.bean.RichImageSpan
import com.haazzz.haabase.util.zoomBitmapToFixWidth
import com.haazzz.haabase.view.EditView

/**
 * Author  : Haa-zzz
 * Time : 2021/8/8
 * 图片加载
 */
class ImagePlate(private val view: EditView, private val mContext: Context) {

    fun image(uri: Uri) {
        val maxWidth = view.measuredWidth - view.paddingLeft - view.paddingRight
        uriToBitMap(mContext,uri,object : GlideBitmap{
            override fun getBitmap(bitmap: Bitmap?) {
                bitmap ?: return
                image(uri, zoomBitmapToFixWidth(bitmap, maxWidth ))
            }
        })
    }

    private fun image(uri: Uri, pic: Bitmap?) {
        val imgStr = "img"
        val start = view.selectionStart

        val spannableString = SpannableString("\nimg\n\n")
        val myImgSpan = RichImageSpan(mContext, pic, uri)

        spannableString.setSpan(myImgSpan, 1, imgStr.length + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        view.editableText.insert(start, spannableString) // 设置ss要添加的位置
        //设置点击事件
       // setClick(start + 1, start + spannableString.length - 2, uri.path)
        view.requestLayout()
        view.requestFocus()
    }

//    private fun setClick(start: Int, end: Int, path: String?) {
//        view.movementMethod = LinkMovementMethod.getInstance()
//        val clickSpan: ClickableSpan = object : ClickableSpan() {
//            override fun onClick(widget: View) {
//                val imm =
//                    mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//                imm.hideSoftInputFromWindow(view.windowToken, 0) //强制隐藏键盘
//                Toast.makeText(mContext, path, Toast.LENGTH_SHORT).show()
//            }
//        }
//        view.editableText.setSpan(clickSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//    }




}