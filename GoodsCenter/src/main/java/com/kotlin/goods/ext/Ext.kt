package com.kotlin.goods.ext

import android.widget.EditText
import com.kotlin.goods.R
import org.jetbrains.anko.find
import ren.qinc.numberbutton.NumberButton

/**
 * 作者：何兆鸿 on 2018/4/27.
 * 学无止境~
 * 扩展类，逻辑层面
 */

/**
 * 三方控件扩展:商品详情的SKU中加减按钮
 */
fun NumberButton.getEditText():EditText{
    return find(R.id.text_count)
}