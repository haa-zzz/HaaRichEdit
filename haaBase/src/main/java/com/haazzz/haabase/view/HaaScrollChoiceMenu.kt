package com.haazzz.haabase.view

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.annotation.RequiresApi
import com.haazzz.haabase.R
import com.haazzz.haabase.databinding.HaaChoicemenuBinding
import com.haazzz.haabase.util.*

/**
 * Author : Haa-zzz
 * Time : 2021/8/7
 * 横向选择器
 */
@RequiresApi(Build.VERSION_CODES.N)
class HaaScrollChoiceMenu : RelativeLayout {
    private lateinit var mContext: Context
    private lateinit var binding: HaaChoicemenuBinding
    private var editView: EditView? = null

    private lateinit var hashMap: MutableMap<Int, Boolean>
    private lateinit var alignSet: MutableSet<Int>
    private lateinit var fcSet :MutableSet<Int>
    private lateinit var gcSet:MutableSet<Int>
    private var isAttach = false

    constructor(context: Context?) : super(context) {
        initView(context)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initView(context)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initView(context)
    }


    private fun initView(context: Context?) {
        mContext = context ?: return
        val view = LayoutInflater.from(context).inflate(R.layout.haa_choicemenu, this)
        binding = HaaChoicemenuBinding.bind(view)
        initHashMap()
        setAllGone()
        initOnclick()


    }

    private fun initHashMap() {
        hashMap = HashMap()
        alignSet = HashSet()
        fcSet = HashSet()
        gcSet = HashSet()

        hashMap[INT_ATTACHMENT] = false
        hashMap[INT_ALIGN] = false
        hashMap[INT_BGCOLOR] = false
        hashMap[INT_FGCOLOR] = false
        hashMap[INT_FONT] = false
        hashMap[INT_FONTSZIE] = false


        hashMap[INT_H1] = false
        hashMap[INT_H2] = false
        hashMap[INT_H3] = false
        hashMap[INT_H4] = false
        hashMap[INT_H5] = false
        hashMap[INT_H6] = false

        hashMap[INT_FONT_BLOD] = false
        hashMap[INT_FONT_ITALIC] = false
        hashMap[INT_FONT_STRIKETHROUGH] = false
        hashMap[INT_FONT_UNDERLINE] = false

    }


    private fun initOnclick() {

        binding.bottom.cmAttachment.setOnClickListener {
            setAllGone()
            if (hashMap[INT_ATTACHMENT] == false) {

                setSingleVisible(binding.attach.attachmentMain, binding.bottom.cmAttachment,
                    R.drawable.attachment,INT_ATTACHMENT)

            } else {
                setSingleGone(binding.attach.attachmentMain, binding.bottom.cmAttachment,
                    R.drawable.attachment2,INT_ATTACHMENT)

                if(isAttach){
                    editView?.setAttachment(binding.attach.attachmentEdit.text.toString())
                    isAttach = false
                }
            }

        }

        binding.bottom.cmFontSize.setOnClickListener {
            setAllGone()
            when {
                hashMap[INT_FONTSZIE] == false  -> {

                    setSingleVisible(binding.fontSize.fsMain, binding.bottom.cmFontSize, R.drawable.fontsize_,INT_FONTSZIE)
                }
                isChoiceFontSize() -> {
                    setSingleVisible(binding.fontSize.fsMain, binding.bottom.cmFontSize, R.drawable.fontsize_,-1)
                }
                else -> {
                    setSingleGone(binding.fontSize.fsMain, binding.bottom.cmFontSize, R.drawable.font_size,INT_FONTSZIE)
                    //editView?.setFontSize(DEFULTSZIE)
                }
            }
        }
        binding.bottom.cmFont.setOnClickListener {
            setAllGone()
            when {
                hashMap[INT_FONT] == false -> {

                    setSingleVisible(binding.font.fontMain, binding.bottom.cmFont, R.drawable.text_,INT_FONT)
                }
                isChoiceFont() -> {
                    setSingleVisible(binding.font.fontMain, binding.bottom.cmFont, R.drawable.text_,-1)
                }
                else -> {
                    setSingleGone(binding.font.fontMain, binding.bottom.cmFont, R.drawable.text,INT_FONT)
                }
            }
        }
        binding.bottom.cmColor.setOnClickListener{
            setAllGone()
            when {
                hashMap[INT_FGCOLOR] == false -> {

                    setSingleVisible(binding.fc.fgMain, binding.bottom.cmColor, R.drawable.fontcolor_,
                        INT_FGCOLOR)
                }
                isChoiceFC() -> {
                    setSingleVisible(binding.fc.fgMain, binding.bottom.cmColor, R.drawable.fontcolor_,-1)
                }
                else -> {
                    setSingleGone(binding.fc.fgMain, binding.bottom.cmColor, R.drawable.fontcolor,
                        INT_FGCOLOR)
                }
            }
        }
        binding.bottom.cmGc.setOnClickListener{
            setAllGone()
            when {
                hashMap[INT_BGCOLOR] == false -> {

                    setSingleVisible(binding.gc.gcMain, binding.bottom.cmGc, R.drawable.gc_,
                        INT_BGCOLOR)
                }
                isChoiceGc() -> {
                    setSingleVisible(binding.gc.gcMain, binding.bottom.cmGc, R.drawable.gc_,-1)
                }
                else -> {
                    setSingleGone(binding.gc.gcMain, binding.bottom.cmGc, R.drawable.gc,
                        INT_BGCOLOR)
                }
            }
        }
        binding.bottom.cmOther.setOnClickListener{

        }
        initItemClick()
    }


    private fun initItemClick() {

        binding.attach.cmImage.setOnClickListener{
            if (binding.attach.attachmentEdit.text.isNotEmpty()  ) {
                isAttach = isAttach != true
                editView?.setAttachment(binding.attach.attachmentEdit.text.toString())
            }
        }
        binding.fontSize.fsH1.setOnClickListener {
            setFontSizeDefault(INT_H1)
            if(hashMap[INT_H1] == false){
                binding.fontSize.fsH1.setImageResource(R.drawable.h1_)
            }
            editView?.setFontSize(H1)
            setHashMapValue(INT_H1)
        }
        binding.fontSize.fsH2.setOnClickListener {
            setFontSizeDefault(INT_H2)
            if(hashMap[INT_H2] == false){
                binding.fontSize.fsH2.setImageResource(R.drawable.h2_)
            }
            editView?.setFontSize(H2)
            setHashMapValue(INT_H2)
        }
        binding.fontSize.fsH3.setOnClickListener {
            setFontSizeDefault(INT_H3)
            if(hashMap[INT_H3] == false){
                binding.fontSize.fsH3.setImageResource(R.drawable.h3_)
            }
            editView?.setFontSize(H3)
            setHashMapValue(INT_H3)
        }
        binding.fontSize.fsH4.setOnClickListener {
            setFontSizeDefault(INT_H4)
            if(hashMap[INT_H4] == false){
                binding.fontSize.fsH4.setImageResource(R.drawable.h4_)
            }
            editView?.setFontSize(H4)
            setHashMapValue(INT_H4)
        }
        binding.fontSize.fsH5.setOnClickListener {
            setFontSizeDefault(INT_H5)
            if(hashMap[INT_H5] == false){
                binding.fontSize.fsH5.setImageResource(R.drawable.h5_)
            }
            editView?.setFontSize(H5)
            setHashMapValue(INT_H5)
        }
        binding.fontSize.fsH6.setOnClickListener {
            setFontSizeDefault(INT_H6)
            if(hashMap[INT_H6] == false){
                binding.fontSize.fsH6.setImageResource(R.drawable.h6_)
            }
            editView?.setFontSize(H6)
            setHashMapValue(INT_H6)
        }
        binding.font.fontBlod.setOnClickListener{
            if(hashMap[INT_FONT_BLOD] == false){
                binding.font.fontBlod.setImageResource(R.drawable.bold_)
            }else{
                binding.font.fontBlod.setImageResource(R.drawable.bold)
            }
            editView?.setBold()
            setHashMapValue(INT_FONT_BLOD)
        }
        binding.font.fontUnderline.setOnClickListener{
            if(hashMap[INT_FONT_UNDERLINE] == false){
                binding.font.fontUnderline.setImageResource(R.drawable.underline_)
            }else{
                binding.font.fontUnderline.setImageResource(R.drawable.underline)
            }
            editView?.setUnderline()
            setHashMapValue(INT_FONT_UNDERLINE)
        }
        binding.font.fontStrikethrough.setOnClickListener{
            if(hashMap[INT_FONT_STRIKETHROUGH] == false){
                binding.font.fontStrikethrough.setImageResource(R.drawable.strikethrough_)
            }else{
                binding.font.fontStrikethrough.setImageResource(R.drawable.strikethrough)
            }
            editView?.setStreak()
            setHashMapValue(INT_FONT_STRIKETHROUGH)
        }
        binding.font.fontItalic.setOnClickListener{
            if(hashMap[INT_FONT_ITALIC] == false){
                binding.font.fontItalic.setImageResource(R.drawable.italic_)
            }else{
                binding.font.fontItalic.setImageResource(R.drawable.italic)
            }
            editView?.setItalic()
            setHashMapValue(INT_FONT_ITALIC)
        }
        binding.fc.fgBlack.setOnClickListener{
            setFCDefault(BLACK)
            if( fcSet.add(BLACK) ) {
                binding.fc.fgBlack.setImageResource(R.drawable.black)
            }else{
                fcSet.remove(BLACK)
            }
            editView?.setFGColor(BLACK)
        }
        binding.fc.fgBlue.setOnClickListener{
            setFCDefault(BLUE)
            if( fcSet.add(BLUE) ) {
                binding.fc.fgBlue.setImageResource(R.drawable.blue)
            }else{
                fcSet.remove(BLUE)
            }
            editView?.setFGColor(BLUE)
        }
        binding.fc.fgDeepBlue.setOnClickListener{
            setFCDefault(DEEPBLUE)
            if( fcSet.add(DEEPBLUE) ) {
                binding.fc.fgDeepBlue.setImageResource(R.drawable.dbule)
            }else{
                fcSet.remove(DEEPBLUE)
            }
            editView?.setFGColor(DEEPBLUE)
        }

        binding.fc.fgGreen.setOnClickListener{
            setFCDefault(GREEN)
            if( fcSet.add(GREEN) ) {
                binding.fc.fgGreen.setImageResource(R.drawable.green)
            }else{
                fcSet.remove(GREEN)
            }
            editView?.setFGColor(GREEN)
        }
        binding.fc.fgRed.setOnClickListener{
            setFCDefault(RED)
            if( fcSet.add(RED) ) {
                binding.fc.fgRed.setImageResource(R.drawable.red)
            }else{
                fcSet.remove(RED)
            }
            editView?.setFGColor(RED)
        }
        binding.fc.fgYellow.setOnClickListener{
            setFCDefault(YELLOW)
            if( fcSet.add(YELLOW) ) {
                binding.fc.fgYellow.setImageResource(R.drawable.yellow)
            }else{
                fcSet.remove(YELLOW)
            }
            editView?.setFGColor(YELLOW)
        }
        binding.gc.gcRed.setOnClickListener{
            setGcDefault(RED)
            if( gcSet.add(RED) ){
                binding.gc.gcRed.setImageResource(R.drawable.red_)
            }else{
                gcSet.remove(RED)
            }
            editView?.setBGColor(RED)
        }
        binding.gc.gcPink.setOnClickListener{
            setGcDefault(PINK)
            if( gcSet.add(PINK) ){
                binding.gc.gcPink.setImageResource(R.drawable.pink_)
            }else{
                gcSet.remove(PINK)
            }
            editView?.setBGColor(Color.parseColor(COLOR_PINK))
        }
        binding.gc.gcGreen.setOnClickListener{
            setGcDefault(GREEN)
            if( gcSet.add(GREEN) ){
                binding.gc.gcGreen.setImageResource(R.drawable.green_)
            }else{
                gcSet.remove(GREEN)
            }
            editView?.setBGColor(GREEN)
        }
        binding.gc.gcDeepBlue.setOnClickListener{
            setGcDefault(DEEPBLUE)
            if( gcSet.add(DEEPBLUE) ){
                binding.gc.gcDeepBlue.setImageResource(R.drawable.dbule_)
            }else{
                gcSet.remove(DEEPBLUE)
            }
            editView?.setBGColor(DEEPBLUE)
        }
        binding.gc.gcBlue.setOnClickListener{
            setGcDefault(BLUE)
            if( gcSet.add(BLUE) ){
                binding.gc.gcBlue.setImageResource(R.drawable.blue_)
            }else{
                gcSet.remove(BLUE)
            }
            editView?.setBGColor(BLUE)
        }
        binding.gc.gcBlack.setOnClickListener{
            setGcDefault(BLACK)
            if( gcSet.add(BLACK) ){
                binding.gc.gcBlack.setImageResource(R.drawable.black_)
            }else{
                gcSet.remove(BLACK)
            }
            editView?.setBGColor(BLACK)
        }
        binding.gc.gcYellow.setOnClickListener{
            setGcDefault(YELLOW)
            if( gcSet.add(YELLOW) ){
                binding.gc.gcYellow.setImageResource(R.drawable.yellow_)
            }else{
                gcSet.remove(YELLOW)
            }
            editView?.setBGColor(YELLOW)
        }

    }
    /**
    绑定EditView
     */
    fun bindingEditView(editView: EditView) {
        this.editView = editView
    }
    fun setOtherOnClickListener(clickListener: OnClickListener){
       binding.bottom.cmOther.setOnClickListener(clickListener)
    }
    fun setImageOnClickListener(clickListener: OnClickListener){
        binding.bottom.cmImage.setOnClickListener(clickListener)
    }

    private fun isChoiceFont(): Boolean {
        if(hashMap[INT_FONT_ITALIC] == false && hashMap[INT_FONT_STRIKETHROUGH] == false
            && hashMap[INT_FONT_UNDERLINE]==false && hashMap[INT_FONT_BLOD] == false  ){
            return false
        }
        return true
    }

    private fun isChoiceFontSize(): Boolean {
        if (hashMap[INT_H1] == false && hashMap[INT_H2] == false && hashMap[INT_H3] == false
            && hashMap[INT_H4] == false && hashMap[INT_H5] == false && hashMap[INT_H6] == false
        ) {
            return false
        }
        return true
    }

    private fun setAllGone() {

        setSingleGone(binding.attach.attachmentMain, binding.bottom.cmAttachment, R.drawable.attachment2,
            -1)

        if(!isChoiceFC() ){
            setSingleGone(binding.fc.fgMain,binding.bottom.cmColor,R.drawable.fontcolor,-1)
        }else{
            binding.fc.fgMain.visibility = GONE
        }

        if( !isChoiceFontSize() ){
            setSingleGone(binding.fontSize.fsMain,binding.bottom.cmFontSize,R.drawable.font_size,-1)
        }else{
            binding.fontSize.fsMain.visibility = GONE
        }

        if(!isChoiceFont()){
            setSingleGone(binding.font.fontMain,binding.bottom.cmFont,R.drawable.text, -1)
        }else{
            binding.font.fontMain.visibility = GONE
        }
        if(!isChoiceGc() ){
            setSingleGone(binding.gc.gcMain,binding.bottom.cmGc,R.drawable.gc,-1)
        }else{
            binding.gc.gcMain.visibility = GONE
        }

    }

    private fun isChoiceGc(): Boolean {
        return gcSet.isNotEmpty()
    }

    private fun isChoiceFC(): Boolean {
        return fcSet.isNotEmpty()
    }

//    private fun isChoiceAlign(): Boolean {
//        return alignSet.isNotEmpty()
//    }

    private fun setGcDefault(key : Int){
        binding.gc.gcBlack.setImageResource(R.drawable.gc_black)
        binding.gc.gcBlue.setImageResource(R.drawable.gc_blue)
        binding.gc.gcDeepBlue.setImageResource(R.drawable.gc_dblue)
        binding.gc.gcGreen.setImageResource(R.drawable.gc_green)
        binding.gc.gcPink.setImageResource(R.drawable.gc_pink)
        binding.gc.gcRed.setImageResource(R.drawable.gc_red)
        binding.gc.gcYellow.setImageResource(R.drawable.gc_yellow)
        gcSet.removeIf{e -> (e!=key)}
    }
    private fun setFCDefault(key : Int){
        binding.fc.fgBlack.setImageResource(R.drawable.fg_black)
        binding.fc.fgYellow.setImageResource(R.drawable.fg_yellow)
        binding.fc.fgRed.setImageResource(R.drawable.fg_red)
        binding.fc.fgGreen.setImageResource(R.drawable.fg_green)
        binding.fc.fgDeepBlue.setImageResource(R.drawable.fg_dblue)
        binding.fc.fgBlue.setImageResource(R.drawable.fg_blue)

        fcSet.removeIf{e -> (e!=key)}
    }
    private fun setFontSizeDefault(key : Int) {
        binding.fontSize.fsH1.setImageResource(R.drawable.h1)
        binding.fontSize.fsH2.setImageResource(R.drawable.h2)
        binding.fontSize.fsH3.setImageResource(R.drawable.h3)
        binding.fontSize.fsH4.setImageResource(R.drawable.h4)
        binding.fontSize.fsH5.setImageResource(R.drawable.h5)
        binding.fontSize.fsH6.setImageResource(R.drawable.h6)
        if(INT_H1 != key){
            hashMap[INT_H1] = false
        }
        if(INT_H2 != key){
            hashMap[INT_H2] = false
        }
        if(INT_H3 != key){
            hashMap[INT_H3] = false
        }
        if(INT_H4 != key){
            hashMap[INT_H4] = false
        }
        if(INT_H5 != key){
            hashMap[INT_H5] = false
        }
        if(INT_H6 != key){
            hashMap[INT_H6] = false
        }
    }
    private fun setSingleGone(view: View, image: ImageView, resId: Int,hashKey: Int) {
        view.visibility = GONE
        setImageResId(image, resId)
        hashMap[hashKey] = false
    }
    private fun setSingleVisible(view: View, image: ImageView, resId: Int, hashKey : Int) {
        view.visibility = VISIBLE
        setImageResId(image, resId)
        hashMap[hashKey] = true
    }
    private fun setImageResId(image: ImageView, resId: Int) {
        image.setImageResource(resId)
    }
    private fun setHashMapValue(int: Int) {
        hashMap[int] = hashMap[int] != true
    }

}