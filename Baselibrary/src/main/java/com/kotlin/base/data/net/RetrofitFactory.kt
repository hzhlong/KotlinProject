package com.kotlin.base.data.net

import com.kotlin.base.common.BaseConstant
import com.kotlin.base.utils.AppPrefsUtils
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * create by 何兆鸿 as 2018/4/17
 * 网络请求工厂
 */
class RetrofitFactory private constructor() {

    // 伴生对象：类似java的static模块，专门创建静态对象、属性的。
    companion object {
        val instance: RetrofitFactory by lazy { RetrofitFactory() }
    }

    private var retrofit: Retrofit
    private val interceptor:Interceptor

    init {
        interceptor = Interceptor {
            chain ->
                val request = chain.request()
                        .newBuilder()
                        .header("Content-Type", "application/json")
                        .header("charset", "UTF-8")
                        .header("token", AppPrefsUtils.getString(BaseConstant.KEY_SP_TOKEN))
                        .build()
                chain.proceed(request)
        }

        retrofit = Retrofit.Builder()
                .baseUrl(BaseConstant.SERVER_ADDRESS)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(initClient())
                .build()
    }

    /**
     * 创建OkHttpClient
     */
    private fun initClient(): OkHttpClient {

        val httpClient = OkHttpClient.Builder()
                .addInterceptor(initLogInterceptor())
                .addInterceptor(interceptor)
                .connectTimeout(10,TimeUnit.SECONDS)// 设置连接时间
                .readTimeout(10,TimeUnit.SECONDS)// 设置超时时间

        return httpClient.build()
    }

    /**
     * 创建日志拦截器
     */
    private fun initLogInterceptor(): Interceptor {
        // log拦截器  打印所有的log
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    /**
     * 返回retrofit的实例
     */
    fun <T> create(service:Class<T>):T{
        return retrofit.create(service)
    }

}
