package com.haazzz.haabase.bean


/**
 * Author Haa-zzz
 * Time 2021/8/5
 * 字体样式基类, 所有的字体样式都先封装成这个类，最后做统一处理
 */
class BaseStyle(
    var isBold: Boolean = false,       //加粗
    var isItalic: Boolean = false,    //斜体的，默认不是
    var isUnderline: Boolean = false,  //下划线，默认没有
    var isStreak: Boolean = false,     //删除线
    var fontSize: Int? = null,            //
    var foregroundColor: Int? = null,       // 默认字体颜色
    var backGroundColor: Int? = null,       //背景色
    var attachment: String? = null,   //是否是链接
    var isListOrdered: Boolean = false,      //是否是有序列表
    var isListUnordered: Boolean = false,    //是否是无序列表

    var isAlignCenter: Boolean = false,
    var isAlignLeft: Boolean = false,
    var isAlignRight: Boolean = false,
) {
    var start: Int = 0
    var end: Int = 0
}