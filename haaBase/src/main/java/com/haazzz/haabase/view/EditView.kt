package com.haazzz.haabase.view

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.net.Uri
import android.os.Build
import android.text.Spannable
import android.text.style.*
import android.util.AttributeSet
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatEditText
import com.haazzz.haabase.bean.BaseStyle
import com.haazzz.haabase.bean.ListOrderedSpan
import com.haazzz.haabase.api.ImagePlate
import com.haazzz.haabase.util.textToHtml

/**
 * Author : Haa-zzz
 * Time : 2021/8/7
 * 富文本实体类
 * 继承自AppCompatEditText,通过设置各种Span来完成样式变换
 */
class EditView : AppCompatEditText {

    private lateinit var mContext: Context


    constructor(context: Context) : super(context) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initView(context)
    }

    /*
    初始化
     */
    private fun initView(context: Context) {
        mContext = context
    }

    fun setBold() {
        setSpan(BaseStyle(isBold = true), StyleSpan::class.java)
    }

    fun setItalic() {
        setSpan(BaseStyle(isItalic = true), StyleSpan::class.java)
    }

    fun setUnderline() {
        setSpan(BaseStyle(isUnderline = true), UnderlineSpan::class.java)
    }

    fun setStreak() {
        setSpan(BaseStyle(isStreak = true), StrikethroughSpan::class.java)
    }

    fun setFontSize(size: Int) {
        setSpan(BaseStyle(fontSize = size), AbsoluteSizeSpan::class.java)
    }

    fun setFGColor(color: Int) {
        setSpan(BaseStyle(foregroundColor = color), ForegroundColorSpan::class.java)
    }

    fun setBGColor(color: Int) {
        setSpan(BaseStyle(backGroundColor = color), BackgroundColorSpan::class.java)
    }

    fun setAttachment(uri: String) {
        setSpan(BaseStyle(attachment = uri), URLSpan::class.java)
    }
    fun setListUnordered(){
        //setSpan(BaseStyle(isListUnordered = true),BulletSpan::class.java)

    }

    fun setListOrdered() {
        //setSpan(BaseStyle(isListOrdered = true), ListOrderedSpan::class.java)
    }

    fun setImage(pathUri: Uri) {

        val plate = ImagePlate(this, mContext)
        plate.image(pathUri)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun createHtml(): String {
        return textToHtml(editableText)
    }

    /**
     * 通用 setSpan
     *
     */
    private fun <T> setSpan(style: BaseStyle, tClass: Class<T>) {
        val start = selectionStart
        val end = selectionEnd

        //再次按下需要取消，所有先要找到当前位置 tClass 对应的的Span
        val spans = editableText.getSpans(start, end, tClass)

        //这里是只处理当前按下的样式，所以需要从当前位置的SpanS中找到现在按下的是哪个，再进行处理。
        //主要是处理 isBold和isItalic是同类型，
        var isSpans = false
        val oldStyleS: ArrayList<BaseStyle> = findOldSpan(spans, style)
        if (oldStyleS.isNotEmpty()) {
            isSpans = true
            var pre = start
            var cur = end
            var isIntegrate = false

            for (oldSpan in oldStyleS) {
                if (start >= oldSpan.start && end <= oldSpan.end && start != end) {

                    editableText.setSpan(
                        findTypeofSpan(oldSpan),
                        oldSpan.start,
                        start,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    editableText.setSpan(
                        findTypeofSpan(oldSpan),
                        end,
                        oldSpan.end,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    break
                } else {
                    isIntegrate = true
                    pre = pre.coerceAtMost(oldSpan.start)
                    cur = cur.coerceAtLeast(oldSpan.end)
                }
            }
            if (isIntegrate)
                editableText.setSpan(
                    findTypeofSpan(style),
                    pre,
                    cur,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
        }
        if (isSpans) {
            return
        }
        if (start != end) {
            editableText.setSpan(
                findTypeofSpan(style),
                start,
                end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        } else {
            editableText.setSpan(
                findTypeofSpan(style),
                start,
                end,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE
            )
        }
    }

    /**
     *
     */
    private fun <T> findOldSpan(spans: Array<T>, baseStyle: BaseStyle): ArrayList<BaseStyle> {
        val list = arrayListOf<BaseStyle>()

        for (span in spans) {

            val style = BaseStyle()
            style.start = editableText.getSpanStart(span)
            style.end = editableText.getSpanEnd(span)
            when (span) {
                is StyleSpan -> {

                    if (span.style == Typeface.BOLD && baseStyle.isBold) {
                        style.isBold = true
                    }
                    if (span.style == Typeface.ITALIC && baseStyle.isItalic) {
                        style.isItalic = true
                    }
                }
                is AbsoluteSizeSpan -> {
                    style.fontSize = span.size
                }
                is ForegroundColorSpan -> {
                    style.foregroundColor = span.foregroundColor
                }
                is BackgroundColorSpan -> {
                    style.backGroundColor = span.backgroundColor
                }
                is UnderlineSpan -> {
                    style.isUnderline = true

                }
                is StrikethroughSpan -> {
                    style.isStreak = true
                }
                is URLSpan -> {
                    style.attachment = span.url
                }
//                is BulletSpan ->{
//                    style.isListUnordered = true
//                }
            }
            editableText.removeSpan(span)
            list.add(style)
        }
        return list
    }

    /**
     * 根据baseStyle拿到Span实例
     */
    private fun findTypeofSpan(baseStyle: BaseStyle): CharacterStyle? {
        when {
            baseStyle.isBold -> {
                return StyleSpan(Typeface.BOLD)
            }
            baseStyle.isItalic -> {
                return StyleSpan(Typeface.ITALIC)
            }
            baseStyle.isUnderline -> {
                return UnderlineSpan()
            }
            baseStyle.isStreak -> {
                return StrikethroughSpan()
            }
            baseStyle.fontSize != null -> {
                return AbsoluteSizeSpan(baseStyle.fontSize!!, true)
            }
            baseStyle.foregroundColor != null -> {
                return ForegroundColorSpan(baseStyle.foregroundColor!!)
            }
            baseStyle.backGroundColor != null -> {
                return BackgroundColorSpan(baseStyle.backGroundColor!!)
            }
            baseStyle.attachment != null -> {
                return URLSpan(baseStyle.attachment)
            }

            else -> return null
        }
    }

}