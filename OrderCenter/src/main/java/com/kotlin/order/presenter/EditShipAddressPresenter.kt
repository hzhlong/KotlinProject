package com.kotlin.order.presenter

import android.os.Parcelable
import com.kotlin.base.ext.execute
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import com.kotlin.order.data.protocol.ShipAddress
import com.kotlin.order.presenter.view.EditShipAddressView
import com.kotlin.order.presenter.view.ShipAddressView
import com.kotlin.order.service.impl.ShipAddressServiceImpl
import kotlinx.android.parcel.Parcelize
import javax.inject.Inject

/**
 * create by 何兆鸿 as 2018/4/17
 * 编辑收货人信息Presenter
 */
class EditShipAddressPresenter @Inject constructor(): BasePresenter<EditShipAddressView>() {

    @Inject
    lateinit var shipAddressService: ShipAddressServiceImpl

    // 添加收货人信息
    fun addShipAddress(shipUserName: String, shipUserMobile: String, shipAddress: String) {
        /**
         * 业务逻辑
         */
        if(!checkNetWork()){ // 检查网络是否可用
            return
        }
        mView.showLoading()

        shipAddressService.addShipAddress(shipUserName, shipUserMobile, shipAddress)
                .execute(object : BaseSubscriber<Boolean>(mView){
                    override fun onNext(t: Boolean) {
                        mView.onAddShipAddressResult(t)
                    }
                },lifecycleProvider)
    }

    // 修改收货人信息
    fun editShipAddress(address:ShipAddress) {
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
                        mView.onEditShipAddressResult(t)
                    }
                },lifecycleProvider)
    }
}