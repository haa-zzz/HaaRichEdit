package com.haazzz.haabase.resolver

import android.graphics.Color
import android.text.*
import android.text.Html.TagHandler
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StrikethroughSpan
import android.util.Log
import com.haazzz.haabase.util.TAG

import org.xml.sax.XMLReader

/**
 * 自定义解析，img 和 fontSize color
 *
 * <p dir="ltr">
 *     &#40644;&#26223;&#29788;&#35828;&#19968;&#19979;
 *     <span style="font-size:32px";>
 *         <span style="text-decoration:line-through;">
 *             &#20180;&#32454;
 *         </span>
 *      </span>
 * </p>
 */

class MyTagHandler : TagHandler {
    private val attributes = HashMap<String, ArrayList<String>>()      //存储对应的标签
    private var startIndex = 0  //标签起始位置
    private var stopIndex = 0   //标签结束位置

    override fun handleTag(opening: Boolean, tag: String, output: Editable, xmlReader: XMLReader) {
        processAttributes(xmlReader)

            if (opening) {
                startSpan(output)
            } else {
               
                endSpan(output)
                attributes.clear()
            }
    }

    private fun startSpan(output: Editable) {
        startIndex = output.length

    }

    private fun endSpan(output: Editable) {
        stopIndex = output.length
        for( ha in attributes.keys){

        }
        //解析style属性
        attributes["style"]?.let {
            for (style in it){
                if (!TextUtils.isEmpty(style)) {
                    analysisStyle(startIndex, stopIndex, output, style)
                }
            }
        }
        //解析size属性
        var size = attributes["size"]?.get(0)

        if (!TextUtils.isEmpty(size)) {
            size = size!!.split("px".toRegex()).toTypedArray()[0]
        }

        if (!TextUtils.isEmpty(size)) {
            val fontSize = Integer.valueOf(size!!)
            val absoluteSizeSpan = AbsoluteSizeSpan(fontSize, true)
            output.setSpan(absoluteSizeSpan, startIndex, stopIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
    }

    /**
     *利用反射获取html标签的属性值
     */
    private fun processAttributes(xmlReader: XMLReader) {
        try {
            val elementField = xmlReader.javaClass.getDeclaredField("theNewElement")
            elementField.isAccessible = true
            val element = elementField[xmlReader]
            val attsField = element.javaClass.getDeclaredField("theAtts")
            attsField.isAccessible = true
            val atts = attsField[element]
            val dataField = atts.javaClass.getDeclaredField("data")
            dataField.isAccessible = true
            Log.d(TAG, "processAttributes: ${dataField[atts]}")
            val data = dataField[atts] as Array<*>
            val lengthField = atts.javaClass.getDeclaredField("length")
            lengthField.isAccessible = true
            val len = lengthField[atts] as Int
            /**
             * MSH: Look for supported attributes and add to hash map.
             * This is as tight as things can get :)
             * The data index is "just" where the keys and values are stored.
             */
            for (i in 0 until len) {
                Log.d(TAG, "processAttributes: data[i * 5 + 1]=${data[i * 5 + 1]} data[i * 5 + 4]=${data[i * 5 + 4]}")
                if(attributes[data[i * 5 + 1]] == null){
                    attributes[data[i * 5 + 1] as String] = arrayListOf(data[i * 5 + 4] as String)
                }else{
                    attributes[data[i * 5 + 1]]?.add(data[i * 5 + 4] as String)
                }
            }
        } catch (e: Exception) {
        }
    }

    /**
     * 解析style属性
     * @param startIndex
     * @param stopIndex
     * @param editable
     * @param style
     */
    private fun analysisStyle(startIndex: Int, stopIndex: Int, editable: Editable, style: String?) {
        val attrArray = style?.split(";".toRegex())?.toTypedArray()
        
        val attrMap: MutableMap<String, String> = HashMap()
        if (null != attrArray) {
            for (attr in attrArray) {
                val keyValueArray = attr.split(":".toRegex()).toTypedArray()
                if (keyValueArray.size == 2) {
                    // 去除前后空格
                    attrMap[keyValueArray[0].trim { it <= ' ' }] = keyValueArray[1].trim { it <= ' ' }
                }
            }
        }

        //删除线
        val textDecoration = attrMap["text-decoration"]
        if (!TextUtils.isEmpty(textDecoration) && textDecoration == "line-through") {
            val strikethroughSpan = StrikethroughSpan()
            editable.setSpan(
                strikethroughSpan,
                startIndex,
                stopIndex,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        //颜色
        val colorStr = attrMap["color"]
        if (!TextUtils.isEmpty(colorStr)) {
            val color = Color.parseColor(colorStr)
            val colorSpan = ForegroundColorSpan(color)
            editable.setSpan(
                colorSpan,
                startIndex,
                stopIndex,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        //字号
        var fontSize = attrMap["font-size"]

        if (!TextUtils.isEmpty(fontSize)) {
            fontSize = fontSize!!.split("px".toRegex()).toTypedArray()[0]
        }
        if (!TextUtils.isEmpty(fontSize)) {
            val fontSizeDp = Integer.valueOf(fontSize!!)
            val absoluteSizeSpan = AbsoluteSizeSpan(fontSizeDp, true)
            editable.setSpan(
                absoluteSizeSpan,
                startIndex,
                stopIndex,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
    }
    
}