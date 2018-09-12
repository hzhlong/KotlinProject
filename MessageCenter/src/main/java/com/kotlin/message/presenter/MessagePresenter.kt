package com.kotlin.message.presenter

import com.kotlin.base.ext.execute
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import com.kotlin.message.data.protocol.Message
import com.kotlin.message.presenter.view.MessageView
import com.kotlin.message.service.impl.MessageServiceImpl
import javax.inject.Inject

/**
 * create by 何兆鸿 as 2018/4/17
 * 消息 Presenter
 */
class MessagePresenter @Inject constructor(): BasePresenter<MessageView>() {

    @Inject
    lateinit var messageService: MessageServiceImpl

    //  获取消息列表
    fun getMessageList(){
        /**
         * 业务逻辑
         */
        if(!checkNetWork()){ // 检查网络是否可用
            return
        }
        mView.showLoading()

        messageService.getMessageList()
                .execute(object : BaseSubscriber<MutableList<Message>?>(mView){
                    override fun onNext(t: MutableList<Message>?) {
                        mView.onGetMessageResult(t)
                    }
                },lifecycleProvider)
    }

}