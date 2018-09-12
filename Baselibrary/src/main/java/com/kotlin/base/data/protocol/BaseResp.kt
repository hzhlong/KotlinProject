package com.kotlin.base.data.protocol


/**
 * create by 何兆鸿 as 2018/4/19
 * 响应数据的通用信息类
 */
/** 协变注解修饰符：in以及out;
    out是用来输出的，所以只能作为返回类型；in是用来输入的，所以只能作为消费类型。
    而从上面的copy方法中可以看出，out类似于java中的extends，用来界定类型上限，in类似于java中的super，用来界定类型下限**/
class BaseResp<out T>(val status: Int, val message: String, val data: T)
