package com.kotlin.base.utils

import android.app.Activity
import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.kotlin.base.R
import com.kotlin.base.utils.transformations.CropCircleTransformation
import com.kotlin.base.utils.transformations.RoundedCornersTransformation

/**
 * 作者：何兆鸿 on 2018/4/21.
 * 学无止境~
 * Glide工具类
 */
object GlideUtils {

    /**
     * 加载普通图片
     * @param context
     * @param imageView
     * @param url
     */
    fun loadUrlImage(context: Context, url: String, imageView: ImageView?, defaultDraw: Int = R.mipmap.ic_launcher) {
        // 不能崩
        if (imageView == null) {
            return
        }
        val viewContext = imageView.context
        var activity: Activity? = null

        // View你还活着吗？
        if (viewContext is Activity) {
            activity = viewContext
            if (activity.isFinishing) {
                return
            }
        }

        if (null != activity) {

            val options = RequestOptions()
                    .error(defaultDraw)
                    //.placeholder(defaultDraw)
                    //.crossFade(1000)//设置动画时间
                    .dontAnimate()//禁止动画，防止图片变形
                    .priority(Priority.NORMAL) //下载的优先级
                    //all:缓存源资源和转换后的资源 none:不作任何磁盘缓存
                    //source:缓存源资源   result：缓存转换后的资源
                    //.override(80,80)//设置最终显示的图片像素为80*80,注意:这个是像素,而不是控件的宽高
                    .diskCacheStrategy(DiskCacheStrategy.ALL) //缓存策略-磁盘

            Glide.with(activity).load(url).apply(options).into(imageView)
        } else {

            val options = RequestOptions()
                    .error(defaultDraw)
                    // .placeholder(defaultDraw)
                    //.crossFade(1000)//设置动画时间
                    .dontAnimate()//禁止动画，防止图片变形
                    .priority(Priority.NORMAL) //下载的优先级
                    .diskCacheStrategy(DiskCacheStrategy.ALL) //缓存策略-磁盘

            Glide.with(context).load(url).apply(options).into(imageView)
        }
    }

    /**
     * 加载圆型图片
     * @param context
     * @param imageView
     * @param url
     */
    fun loadRoundImage(context: Activity, imageView: ImageView?, url: String, defaultDraw: Int = R.mipmap.ic_launcher) {
        // 不能崩
        if (imageView == null) {
            return
        }
        val viewContext = imageView.context
        var activity: Activity? = null

        // View你还活着吗？
        if (viewContext is Activity) {
            activity = viewContext
            if (activity.isFinishing) {
                return
            }
        }

        if (null != activity) {

            val options = RequestOptions
                    .bitmapTransform(CropCircleTransformation(activity))
                    .error(defaultDraw)
                    .placeholder(defaultDraw)
                    //.crossFade(1000)//设置动画时间
                    .dontAnimate()//禁止动画，防止图片变形
                    .priority(Priority.NORMAL) //下载的优先级
                    .diskCacheStrategy(DiskCacheStrategy.ALL) //缓存策略-磁盘

            Glide.with(activity).load(url).apply(options).into(imageView)
        } else {

            val options = RequestOptions
                    .bitmapTransform(CropCircleTransformation(context))
                    .error(defaultDraw)
                    .placeholder(defaultDraw)
                    //.crossFade(1000)//设置动画时间
                    .dontAnimate()//禁止动画，防止图片变形
                    .priority(Priority.NORMAL) //下载的优先级
                    .diskCacheStrategy(DiskCacheStrategy.ALL) //缓存策略-磁盘

            Glide.with(context).load(url).apply(options).into(imageView)
        }
    }

    /**
     * 加载自定义圆角半径的图片
     * @param context
     * @param imageView
     * @param url
     * @param radius 角度
     */
    fun loadBaseRadiusImage(context: Activity, imageView: ImageView?, url: String, radius: Int, defaultDraw: Int = R.mipmap.ic_launcher) {
        // 不能崩
        if (imageView == null) {
            return
        }
        val viewContext = imageView.context
        var activity: Activity? = null

        // View你还活着吗？
        if (viewContext is Activity) {
            activity = viewContext
            if (activity.isFinishing) {
                return
            }
        }

        if (null != activity) {

            val options = RequestOptions
                    .bitmapTransform(RoundedCornersTransformation(activity, radius, 0,
                            RoundedCornersTransformation.CornerType.ALL))
                    .error(defaultDraw)
                    .placeholder(defaultDraw)
                    //.crossFade(1000)//设置动画时间
                    .dontAnimate()//禁止动画，防止图片变形
                    .priority(Priority.NORMAL) //下载的优先级
                    .diskCacheStrategy(DiskCacheStrategy.ALL) //缓存策略-磁盘

            Glide.with(activity).load(url).apply(options).into(imageView)
        } else {

            val options = RequestOptions
                    .bitmapTransform(RoundedCornersTransformation(context, radius, 0,
                            RoundedCornersTransformation.CornerType.ALL))
                    .error(defaultDraw)
                    .placeholder(defaultDraw)
                    //.crossFade(1000)
                    .dontAnimate()//禁止动画，防止图片变形
                    .priority(Priority.NORMAL) //下载的优先级
                    .diskCacheStrategy(DiskCacheStrategy.ALL) //缓存策略-磁盘

            Glide.with(context).load(url).apply(options).into(imageView)
        }
    }
}
