package com.haazzz.haabase.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.graphics.Point
import android.text.Editable
import android.text.TextWatcher
import android.view.WindowManager
import android.widget.EditText
import java.util.*

/**
 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
 *
 * @param context 上下文
 * @param dpValue 尺寸dip
 * @return 像素值
 */
fun dip2px(context: Context, dpValue: Float): Int {
    val scale = context.resources.displayMetrics.density
    return (dpValue * scale + 0.5f).toInt()
}

/**
 * 判断是否是 Gif
 */
fun isGif(path: String): Boolean {
    val index = path.lastIndexOf('.')
    return index > 0 && "gif".uppercase(Locale.getDefault()) == path.substring(index + 1)
        .uppercase(Locale.getDefault())
}
/**
 * 获取屏幕尺寸
 *
 * @param context 上下文
 * @return 屏幕尺寸像素值，下标为0的值为宽，下标为1的值为高
 */
fun getScreenSize(context: Context): Point {
    // 获取屏幕宽高
    val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val screenSize = Point()
    wm.defaultDisplay.getSize(screenSize)
    return screenSize
}

/**
 * 压缩BitMap
 */

fun zoomBitmapToFixWidth(bitmap: Bitmap, maxWidth: Int): Bitmap {
    val w = bitmap.width
    val h = bitmap.height
    val newHeight = maxWidth * h / w
    return zoomBitmap(bitmap, maxWidth, newHeight)
}

fun zoomBitmap(bitmap: Bitmap, width: Int, height: Int): Bitmap {
    val w = bitmap.width
    val h = bitmap.height
    val matrix = Matrix()
    val scaleWidth = width.toFloat() / w
    val scaleHeight = height.toFloat() / h
    matrix.postScale(scaleWidth, scaleHeight)
    return Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true)
}

