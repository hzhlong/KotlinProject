package com.kotlin.message.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.Bus.bus
import com.kennyc.view.MultiStateView
import com.kotlin.base.ext.startLoading
import com.kotlin.base.ui.fragment.BaseMvpFragment
import com.kotlin.message.R
import com.kotlin.message.data.protocol.Message
import com.kotlin.message.injection.component.DaggerMessageComponent
import com.kotlin.message.injection.moudle.MessageModule
import com.kotlin.message.presenter.MessagePresenter
import com.kotlin.message.presenter.view.MessageView
import com.kotlin.message.ui.adapter.MessageAdapter
import com.kotlin.provider.event.MessageBadgeEvent
import kotlinx.android.synthetic.main.fragment_message.*

/**
 * 作者：何兆鸿 on 2018/4/24.
 * 学无止境~
 * 消息列表Fragment
 */
class MessageFragment : BaseMvpFragment<MessagePresenter>(), MessageView {

    private lateinit var mAdapter: MessageAdapter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater?.inflate(R.layout.fragment_message, container,false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) { // View已经加载完成
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    /**
     *  加载数据，fragment无论创建还是重现界面每次都会执行onStart()方法的
     */
    override fun onStart() {
        super.onStart()
        loadData()
    }


    /**
     *  初始化视图
     */
    private fun initView() {

        mMessageRv.layoutManager = LinearLayoutManager(context)
        mAdapter = MessageAdapter(context)
        mMessageRv.adapter = mAdapter
    }


    /**
     * 加载数据
     */
    private fun loadData() {
        mMultiStateView.startLoading()
        mPresenter.getMessageList()
    }

    override fun onGetMessageResult(result: MutableList<Message>?) {
        if (result != null && result.size > 0) {
            mAdapter.setData(result)
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_CONTENT // 内容的状态
        } else {
            //没有数据
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_EMPTY // 空的状态
        }
    }

    // fragment的方法之一：framgent的隐藏显示状态监听
    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if(!hidden){ // 如果是显示状态，那就代表消息已看过，那么底部导航栏的消息角标就要隐藏
           Bus.send(MessageBadgeEvent(false))
        }
    }


    /**
     *  Dagger注册
     */
    override fun injectComponent() {
        DaggerMessageComponent.builder().activityComponent(mActivityComponent).messageModule(MessageModule()).build().inject(this)

        mPresenter.mView = this
    }
}