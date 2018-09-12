package com.kotlin.base.widgets

import android.content.Context
import android.util.AttributeSet
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import com.ashokvarma.bottomnavigation.ShapeBadgeItem
import com.ashokvarma.bottomnavigation.TextBadgeItem
import com.kotlin.base.R

/**
 * 作者：何兆鸿 on 2018/4/23.
 * 学无止境~
 * 底部导航栏
 */
class BottomNavBar @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : BottomNavigationBar(context, attrs, defStyleAttr) {

    //购物车Tab 标签
    private val mCartBadge:TextBadgeItem // 显示文字的【系统自带】
    //消息Tab 标签
    private val mMsgBadge:ShapeBadgeItem // 显示图标的【系统自带】


    init{
        // 首页
        val homeItem = BottomNavigationItem(R.drawable.btn_nav_home_press,resources.getString(R.string.nav_bar_home))
                .setInactiveIconResource(R.drawable.btn_nav_home_normal) // 未选中的图标
                .setActiveColorResource(R.color.common_blue) // 选中的字体颜色
                .setInActiveColorResource(R.color.text_normal) // 未选中的字体颜色

        // 分页
        val categoryItem = BottomNavigationItem(R.drawable.btn_nav_category_press, resources.getString(R.string.nav_bar_category))
                .setInactiveIconResource(R.drawable.btn_nav_category_normal) // 未选中的图标
                .setActiveColorResource(R.color.common_blue) // 选中的字体颜色
                .setInActiveColorResource(R.color.text_normal) // 未选中的字体颜色

        // 购物车
        val cartItem = BottomNavigationItem(R.drawable.btn_nav_cart_press, resources.getString(R.string.nav_bar_cart))
                .setInactiveIconResource(R.drawable.btn_nav_cart_normal) // 未选中的图标
                .setActiveColorResource(R.color.common_blue) // 选中的字体颜色
                .setInActiveColorResource(R.color.text_normal) // 未选中的字体颜色

        mCartBadge = TextBadgeItem()
        cartItem.setBadgeItem(mCartBadge)

        // 消息
        val msgItem = BottomNavigationItem(R.drawable.btn_nav_msg_press, resources.getString(R.string.nav_bar_msg))
                .setInactiveIconResource(R.drawable.btn_nav_msg_normal) // 未选中的图标
                .setActiveColorResource(R.color.common_blue) // 选中的字体颜色
                .setInActiveColorResource(R.color.text_normal) // 未选中的字体颜色

        mMsgBadge = ShapeBadgeItem()
        mMsgBadge.setShape(ShapeBadgeItem.SHAPE_OVAL)
        msgItem.setBadgeItem(mMsgBadge)

        // 我的
        val userItem = BottomNavigationItem(R.drawable.btn_nav_user_press, resources.getString(R.string.nav_bar_user))
                .setInactiveIconResource(R.drawable.btn_nav_user_normal) // 未选中的图标
                .setActiveColorResource(R.color.common_blue) // 选中的字体颜色
                .setInActiveColorResource(R.color.text_normal) // 未选中的字体颜色

        setMode(BottomNavigationBar.MODE_FIXED) // 固定大小 BottomNavigationBar.MODE_SHIFTING：不固定大小
        setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC) // 背景色是官方白色 BottomNavigationBar.BACKGROUND_STYLE_RIPPLE：背景色是点击按钮的颜色（ActiveColor）
        setBarBackgroundColor(R.color.common_white)

        addItem(homeItem)
                .addItem(categoryItem)
                .addItem(cartItem)
                .addItem(msgItem)
                .addItem(userItem)
                .setFirstSelectedPosition(0) // 默认选择第一个
                .initialise()

        // 初始化下角标
        checkCartBadge(0)
        checkMsgBadge(false)
    }

    /**
     * 检查购物车Tab是否显示标签（判断购物车是否有值）
     */
    fun checkCartBadge(count:Int){
        if (count == 0){
            mCartBadge.hide()
        }else{
            mCartBadge.show()
            mCartBadge.setText("$count")
        }
    }

    /**
     * 检查消息Tab是否显示标签（是否显示消息圆点）
     */
    fun checkMsgBadge(isVisible:Boolean){
        if(isVisible){
            mMsgBadge.show()
        }else{
            mMsgBadge.hide()
        }
    }
}
