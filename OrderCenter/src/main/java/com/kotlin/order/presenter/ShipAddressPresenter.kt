package com.kotlin.order.presenter

import com.kotlin.base.ext.execute
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import com.kotlin.order.data.protocol.ShipAddress
import com.kotlin.order.presenter.view.ShipAddressView
import com.kotlin.order.service.impl.ShipAddressServiceImpl
import javax.inject.Inject

/**
 * create by 何兆鸿 as 2018/4/17
 * 收货人列表Presenter
 */
class ShipAddressPresenter @Inject constructor(): BasePresenter<ShipAddressView>() {

    @Inject
    lateinit var shipAddressService: ShipAddressServiceImpl

    // 获取收货人列表
    fun getShipAddressList(){
        /**
         * 业务逻辑
         */
        if(!checkNetWork()){ // 检查网络是否可用
            return
        }
        mView.showLoading()

        shipAddressService.getShipAddressList()
                .execute(object : BaseSubscriber<MutableList<ShipAddress>?>(mView){
                    override fun onNext(t: MutableList<ShipAddress>?) {
                        mView.onGetShipAddressResult(t)
                    }
                },lifecycleProvider)
    }

    // 设置默认收货人信息
    fun setDefaultShipAddress(address:ShipAddress) {
        /**
         * 业务逻辑
         */
        if(!checkNetWork()){ // 检查网络是否可用
            return
        }
        mView.showLoading()

        shipAddressService.editShipAddress(address)
                .execute(object : BaseSubscriber<Boolean>(mView){
                    override fun onNext(t: Boolean) {
                        mView.onSetDefaultResult(t)
                    }
                },lifecycleProvider)
    }

    // 删除收货人信息
    fun deleteShipAddress(id:Int) {
        /**
         * 业务逻辑
         */
        if(!checkNetWork()){ // 检查网络是否可用
            return
        }
        mView.showLoading()

        shipAddressService.deleteShipAddress(id)
                .execute(object : BaseSubscriber<Boolean>(mView){
                    override fun onNext(t: Boolean) {
                        mView.onDeleteResult(t)
                    }
                },lifecycleProvider)
    }


}