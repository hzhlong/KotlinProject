package com.kotlin.mall.widgets.share

import cn.sharesdk.framework.Platform.ShareParams


/**
 * @author 要分享的数据实体
 */
class ShareData {

    /**
     * 要分享到的平台
     */
    var mPlatformType: ShareManager.PlatformType? = null

    /**
     * 要分享到的平台的参数
     */
    var mShareParams: ShareParams? = null
}
