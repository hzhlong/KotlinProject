package com.kotlin.base.rx

import android.util.Log
import com.kotlin.base.common.ResultCode
import com.kotlin.base.data.protocol.BaseResp
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.functions.Function
import org.json.JSONException
import org.json.JSONObject

/**
 * 作者：何兆鸿 on 2018/4/21.
 * 学无止境~
 * 返回值为T类型的通用Func
 */

class BaseFun<T> : Function<BaseResp<T>, Observable<T>> {
    override fun apply(t: BaseResp<T>): Observable<T> {
        if(t.status != ResultCode.SUCCESS){
            try {
                Log.e("===data", JSONObject(t.data.toString()).toString())
            }catch (e:JSONException){}
            return Observable.error(BaseException(t.status, t.message))
        }
        return Observable.just(t.data)
    }
}