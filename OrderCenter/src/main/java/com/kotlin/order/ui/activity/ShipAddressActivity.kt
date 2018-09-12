package com.kotlin.order.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.eightbitlab.rxbus.Bus
import com.kennyc.view.MultiStateView
import com.kotlin.base.ext.onClick
import com.kotlin.base.ext.startLoading
import com.kotlin.base.ui.activity.BaseMVPActivity
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.kotlin.order.R
import com.kotlin.order.common.OrderConstant
import com.kotlin.order.data.protocol.ShipAddress
import com.kotlin.order.event.SelectAddressEvent
import com.kotlin.order.injection.component.DaggerShipAddressComponent
import com.kotlin.order.injection.moudle.ShipAddressModule
import com.kotlin.order.presenter.ShipAddressPresenter
import com.kotlin.order.presenter.view.ShipAddressView
import com.kotlin.order.ui.adapter.ShipAddressAdapter
import com.kotlin.utilslibrary.widgets.alertView.AlertView
import com.kotlin.utilslibrary.widgets.alertView.OnItemClickListener
import kotlinx.android.synthetic.main.activity_address.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

/**
 * 作者：何兆鸿 on 2018/5/14.
 * 学无止境~
 * desc 收货人信息列表页
 */

class ShipAddressActivity : BaseMVPActivity<ShipAddressPresenter>(), ShipAddressView {

    private var mDefaultId: Int = -1 // 默认选中的地址Id
    private lateinit var mAddress: ShipAddress // 删除的地址item

    private lateinit var mDataList: MutableList<ShipAddress>
    private lateinit var mAdapter: ShipAddressAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address)

        initView()
    }

    override fun onStart() {
        super.onStart()
        loadData()
    }

    /**
     * 初始化视图
     */
    private fun initView() {

        mAddressRv.layoutManager = LinearLayoutManager(this)
        mAdapter = ShipAddressAdapter(this)
        mAddressRv.adapter = mAdapter

        //设置操作事件
        mAdapter.mOptClickListener = object : ShipAddressAdapter.OnOptClickListener {
            override fun onSetDefault(address: ShipAddress) {
                mDefaultId = address.id
                mPresenter.setDefaultShipAddress(address)
            }

            override fun onEdit(address: ShipAddress) {
                startActivity<ShipAddressEditActivity>(OrderConstant.KEY_SHIP_ADDRESS to address)
            }

            override fun onDelete(address: ShipAddress) {
                AlertView("确定删除该地址？"  ,null,  "取消", null, arrayOf("确定"), this@ShipAddressActivity, AlertView.Style.Alert, OnItemClickListener { _: Any, position: Int ->
                    if(position == 0){
                        mAddress = address
                        mPresenter.deleteShipAddress(address.id)
                    }
                }).show()
            }
        }

        //单项点击事件
        mAdapter.setOnItemClickListener(object : BaseRecyclerViewAdapter.OnItemClickListener<ShipAddress>{
            override fun onItemClick(item: ShipAddress, position: Int) {
                Bus.send(SelectAddressEvent(item))
                finish()
            }
        })


        mAddAddressBtn.onClick {
            startActivity<ShipAddressEditActivity>()
        }

    }

    /**
     * 加载数据
     */
    private fun loadData() {
        mPresenter.getShipAddressList()
        mMultiStateView.startLoading()
    }

    /**
     * Dagger注册
     */
    override fun injectComponent() {
        DaggerShipAddressComponent.builder().activityComponent(mActivityComponent).shipAddressModule(ShipAddressModule()).build().inject(this)
        mPresenter.mView = this
    }

    /**
     * 获取收货人信息回调
     */
    override fun onGetShipAddressResult(result: MutableList<ShipAddress>?) {

        if (result != null && result.size > 0) {
            mDataList = result
            mAdapter.setData(result)
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_CONTENT
        } else {
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_EMPTY
        }
    }

    /**
     * 设置默认收货人回调
     */
    override fun onSetDefaultResult(result: Boolean) {
        toast("设置默认成功")

        // 说明下：map是遇到一个就过滤一个，不累积。 forEach是遇到一个就累积一个，之后全部过滤。用哪个都可以哈。
        mDataList?.filter { it.id != mDefaultId } // 过滤出哪些被选中的，然后把数据往下传递【相当于 if(it.isSelected) return it】
                .forEach { it.shipIsDefault = 1 }
        mAdapter.notifyDataSetChanged()
    }

    /**
     * 删除收货人回调
     */
    override fun onDeleteResult(result: Boolean) {
        toast("删除成功")
        mDataList?.remove(mAddress)
        mAdapter.notifyDataSetChanged()
    }
}
