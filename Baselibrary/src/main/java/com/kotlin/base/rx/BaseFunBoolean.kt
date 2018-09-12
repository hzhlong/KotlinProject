package com.kotlin.base.rx

import com.kotlin.base.common.ResultCode
import com.kotlin.base.data.protocol.BaseResp
import io.reactivex.Observable
import io.reactivex.functions.Function

/**
 * 作者：何兆鸿 on 2018/4/21.
 * 学无止境~
 * 返回值为Boolean类型的通用Func
 */

class BaseFunBoolean<T> : Function<BaseResp<T>, Observable<Boolean>>{
    override fun apply(t: BaseResp<T>): Observable<Boolean> {
        if(t.status != ResultCode.SUCCESS){
            return Observable.error(BaseException(t.status,t.message))
        }
        return Observable.just(true)
    }

}
