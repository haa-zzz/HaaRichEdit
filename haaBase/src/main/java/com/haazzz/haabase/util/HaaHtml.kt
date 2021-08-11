package com.haazzz.haabase.util

import android.content.Context
import android.os.Build
import android.text.Editable
import android.text.Html
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.haazzz.haabase.resolver.ImageGetter
import com.haazzz.haabase.resolver.MyTagHandler

/**
 * 使用Html.toHtml把[Editable]转换成Html格式，
 *
 *
 *
 * 转换后的格式大致如下：
 * <p dir="ltr">&#21564;&#21564;&#21564;&#22075;&#22075;&#22075;&#29461;&#29492;<b>&#19977;&#21482;&#26494;&#40736;&#26080;&#32447;&#32593;&#38476;&#29983;&#21448;&#29087;&#24713;</b><b><span style="font-size:32px";>&#20302;&#24037;&#36164;&#26377;</span></b><br>
<b><span style="font-size:32px";>img</span></b></p>
<p dir="ltr"><b><span style="font-size:32px";><img src="content://com.android.providers.media.documents/document/image%3A108815"></span></b><br><br></p>
<p dir="ltr"><b><span style="font-size:32px";>&#24248;&#20154;&#33258;&#25200;&#20043;&#35874;&#35874;</span></b></p>

 使用webView可以看到完整显示
 */
@RequiresApi(Build.VERSION_CODES.N)
fun textToHtml(editable: Editable) : String{
    return Html.toHtml(editable, Html.TO_HTML_PARAGRAPH_LINES_CONSECUTIVE)
}
@RequiresApi(Build.VERSION_CODES.N)
fun htmlToEditable(html : String, context: Context, targetView : TextView){
    val spanned = Html.fromHtml(html,Html.FROM_HTML_MODE_LEGACY, ImageGetter(context, targetView), MyTagHandler())
}