package com.kotlin.base.ui.fragment

import com.tbruyelle.rxpermissions2.RxPermissions
import com.trello.rxlifecycle2.components.support.RxFragment


/**
 * 作者：何兆鸿 on 2018/4/21.
 * 学无止境~
 * Fragment父类
 */
open class BaseFragment : RxFragment(){

    /**================权限封装================**/
    /**
     * 分别申请多个权限,可以根据单个权限的申请成功与否开放部分功能或者做其他相应处理
     * @param permiss 权限集合
     * @param callBack 回调结果给界面，用于针对单个权限的申请成功与否开放部分功能或者做其他相应处理
     * */
    fun setDangerousPermissionByName(permiss: Array<String>, callBack: PermissionCallBack?){
        RxPermissions(activity)
                .request(*permiss)//多个权限用","隔开
                .subscribe({ aBoolean ->
                    callBack!!.permissResult(aBoolean)
                })
    }

    // 权限回调
    interface PermissionCallBack{
        fun permissResult(result: Boolean) : Unit
    }
    /**================权限封装================**/
}