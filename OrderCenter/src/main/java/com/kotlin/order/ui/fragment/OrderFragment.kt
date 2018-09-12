package com.kotlin.order.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.arouter.launcher.ARouter
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.kennyc.view.MultiStateView
import com.kotlin.base.ext.startLoading
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.kotlin.base.ui.fragment.BaseMvpFragment
import com.kotlin.order.R
import com.kotlin.order.common.OrderConstant
import com.kotlin.order.data.protocol.Order
import com.kotlin.order.injection.component.DaggerOrderComponent
import com.kotlin.order.injection.moudle.OrderModule
import com.kotlin.order.presenter.OrderListPresenter
import com.kotlin.order.presenter.view.OrderListView
import com.kotlin.order.ui.activity.OrderDetailActivity
import com.kotlin.order.ui.adapter.OrderAdapter
import com.kotlin.provider.common.ProviderConstant
import com.kotlin.provider.event.UpdatOrderListEvent
import com.kotlin.provider.router.RouterPath
import com.kotlin.utilslibrary.widgets.alertView.AlertView
import com.kotlin.utilslibrary.widgets.alertView.OnItemClickListener
import kotlinx.android.synthetic.main.fragment_order.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast

/**
 * 作者：何兆鸿 on 2018/5/18.
 * 学无止境~
 * desc 订单列表Fragment
 */
class OrderFragment : BaseMvpFragment<OrderListPresenter>(), OrderListView {

    private lateinit var mAdapter : OrderAdapter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater?.inflate(R.layout.fragment_order, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) { // View已经加载完成
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObserve()
    }

    override fun onStart() {
        super.onStart()
        loadData()
    }

    /**
     * 初始化视图和事件
     */
    private fun initView() {
        mOrderRv.layoutManager = LinearLayoutManager(activity)
        mAdapter = OrderAdapter(activity)
        mOrderRv.adapter = mAdapter

        /**
         * 订单对应操作
         */
        mAdapter.listener = object : OrderAdapter.OnOptClickListener{
            override fun onOptClick(optType: Int, order: Order) {
                when (optType){
                    OrderConstant.OPT_ORDER_PAY -> {     // 去支付
                        ARouter.getInstance().build(RouterPath.PaySDK.PATH_PAY)
                                .withInt(ProviderConstant.KEY_ORDER_ID,order.id)
                                .withLong(ProviderConstant.KEY_ORDER_PRICE,order.totalPrice)
                                .navigation()
                    }
                    OrderConstant.OPT_ORDER_CONFIRM -> { // 确定收货
                        mPresenter.confirmOrder(order.id)
                    }
                    OrderConstant.OPT_ORDER_CANCEL -> {  // 取消订单
                        //mPresenter.cacnelOrder(order.id)
                        showCancelDialog(order)
                    }
                }
            }
        }

        /**
         * 列表单项点击事件
         */
        mAdapter.setOnItemClickListener(object : BaseRecyclerViewAdapter.OnItemClickListener<Order>{
            override fun onItemClick(item: Order, position: Int) {
                startActivity<OrderDetailActivity>(ProviderConstant.KEY_ORDER_ID to item.id)
            }
        })
    }

    /**
     * 刷新界面
     */
    private fun initObserve() {
        Bus.observe<UpdatOrderListEvent>()
                .subscribe { t: UpdatOrderListEvent ->
                    run {
                        t.let {
                            if(t.status == 2){ // 支付成功后，待收货界面要刷新
                                loadData()
                            }
                            if(t.status == 3){ // 收货成功后，已完成界面要刷新
                                loadData()
                            }
                            if(t.status == 4){ // 取消订单后，已取消界面要刷新
                                loadData()
                            }
                        }
                    }
                }.registerInBus(this)
    }

    /**
     * 取消订单对话框
     */
    private fun showCancelDialog(order : Order) {
        AlertView("确定取消该订单？"  ,null,  "取消", null, arrayOf("确定"), activity, AlertView.Style.Alert, OnItemClickListener { _: Any, position: Int ->
            if(position == 0){
                mPresenter.cacnelOrder(order.id)
            }
        }).show()
    }

    /**
     * 加载数据
     */
    private fun loadData() {
        mMultiStateView.startLoading()
        mPresenter.getOrderList(arguments.getInt(OrderConstant.KEY_ORDER_STATUS , -1))
    }

    /**
     * Dagger注册
     */
    override fun injectComponent() {
        DaggerOrderComponent.builder().activityComponent(mActivityComponent).orderModule(OrderModule()).build().inject(this)
        mPresenter.mView = this
    }


    /**
     * 获取订单列表回调
     */
    override fun onGetOrderListResult(result: MutableList<Order>?) {
        if(result != null && result.size > 0) {
            mAdapter.setData(result)
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_CONTENT
        }else{
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_EMPTY
        }
    }

    /**
     * 确认订单回调
     */
    override fun onConfirmOrderResult(result: Boolean) {
        toast("确认收货成功")
        loadData()
        Bus.send(UpdatOrderListEvent(3)) // 收货成功后，已完成界面要刷新
    }

    /**
     * 取消订单回调
     */
    override fun onCancelOrderResult(result: Boolean) {
        toast("取消订单成功")
        loadData()
        Bus.send(UpdatOrderListEvent(4)) // 取消订单后，已取消界面要刷新
    }

    /**
     * 取消事件监听
     */
    override fun onDestroy() {
        super.onDestroy()
        Bus.unregister(this)
    }
}