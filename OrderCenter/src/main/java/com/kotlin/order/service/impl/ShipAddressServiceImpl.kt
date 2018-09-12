package com.kotlin.order.service.impl

import com.kotlin.base.ext.convert
import com.kotlin.base.ext.convertBoolean
import com.kotlin.order.data.protocol.ShipAddress
import com.kotlin.order.data.repository.ShipAddressRepository
import com.kotlin.order.service.ShipAddressService
import io.reactivex.Observable
import javax.inject.Inject

/**
 * create by 何兆鸿 as 2018/4/17
 * 收货人信息 业务接口实现类
 */
class ShipAddressServiceImpl @Inject constructor(): ShipAddressService {

    @Inject
    lateinit var repository: ShipAddressRepository

    // 添加收货地址
    override fun addShipAddress(shipUserName: String, shipUserMobile: String, shipAddress: String): Observable<Boolean> {
        return repository.addShipAddress(shipUserName, shipUserMobile, shipAddress).convertBoolean()
    }

    // 获取收货地址列表
    override fun getShipAddressList(): Observable<MutableList<ShipAddress>?>{
        return repository.getShipAddressList().convert()
    }

    // 修改收货地址
    override fun editShipAddress(address:ShipAddress): Observable<Boolean>{
        return repository.editShipAddress(address).convertBoolean()
    }

    // 删除收货地址
    override fun deleteShipAddress(id: Int): Observable<Boolean>{
        return repository.deleteShipAddress(id).convertBoolean()
    }


}