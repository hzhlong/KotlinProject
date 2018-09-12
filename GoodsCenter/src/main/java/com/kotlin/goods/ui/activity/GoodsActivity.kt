package com.kotlin.goods.ui.activity

import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.GridLayoutManager
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder
import cn.bingoogolapple.refreshlayout.BGARefreshLayout
import cn.bingoogolapple.refreshlayout.BGAStickinessRefreshViewHolder
import com.kennyc.view.MultiStateView
import com.kotlin.base.ext.startLoading
import com.kotlin.base.ui.activity.BaseMVPActivity
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.kotlin.goods.R
import com.kotlin.goods.common.GoodsConstant
import com.kotlin.goods.data.protocol.Goods
import com.kotlin.goods.injection.component.DaggerGoodsComponent
import com.kotlin.goods.injection.moudle.GoodsModule
import com.kotlin.goods.presenter.GoodsListPresenter
import com.kotlin.goods.presenter.view.GoodsListView
import com.kotlin.goods.ui.adapter.GoodsAdapter
import kotlinx.android.synthetic.main.activity_goods.*
import org.jetbrains.anko.startActivity

/**
 * 作者：何兆鸿 on 2018/4/26.
 * 学无止境~
 * desc 商品列表界面
 */
class GoodsActivity : BaseMVPActivity<GoodsListPresenter>(), GoodsListView, BGARefreshLayout.BGARefreshLayoutDelegate {

    private lateinit var mGoodsAdapter:GoodsAdapter
    private var mCurrentPage: Int = 1
    private var mMaxPage: Int = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_goods)
        initView()
        initRefreshLayout()
        loadData()

    }

    /**
     * 初始化视图
     */
    private fun initView() {
        mGoodsRv.layoutManager = GridLayoutManager(this,2)
        mGoodsAdapter = GoodsAdapter(this)
        mGoodsRv.adapter = mGoodsAdapter

        mGoodsAdapter.setOnItemClickListener(object : BaseRecyclerViewAdapter.OnItemClickListener<Goods>{
            override fun onItemClick(item: Goods, position: Int) {
                startActivity<GoodsDetailActivity>(GoodsConstant.KEY_GOODS_ID to item.id)
            }
        })
    }

    /**
     *  初始化刷新视图
     */
    private fun initRefreshLayout() {
        // 为BGARefreshLayout 设置代理
        mRefreshLayout.setDelegate(this)
        // 设置下拉刷新和上拉加载更多的风格     参数1：应用程序上下文，参数2：是否具有上拉加载更多功能
        val viewHolder = BGAStickinessRefreshViewHolder(this, true)
        viewHolder.setStickinessColor(R.color.colorRefreshColor)
        viewHolder.setRotateImage(R.mipmap.bga_refresh_stickiness)
        // 设置下拉刷新和上拉加载更多的风格
        mRefreshLayout.setRefreshViewHolder(viewHolder)
    }

    /**
     * 加载数据
     */
    private fun loadData() {
        if(intent.getIntExtra(GoodsConstant.KEY_SEARCH_GOODS_TYPE,0) != 0){
            //按关键字搜索
            mPresenter.getGoodsListByKeyword(intent.getStringExtra(GoodsConstant.KEY_GOODS_KEYWORD), mCurrentPage)
        }else{
            //搜索商品类型
            mPresenter.getGoodsList(intent.getIntExtra(GoodsConstant.KEY_CATEGORY_ID,1), mCurrentPage)
        }
        mMultiStateView.startLoading()

    }

    override fun injectComponent() {
        DaggerGoodsComponent.builder().activityComponent(mActivityComponent).goodsModule(GoodsModule()).build().inject(this)

        mPresenter.mView = this
    }

    override fun onGetGoodsListResult(result: MutableList<Goods>?) {
        mRefreshLayout.endLoadingMore()
        mRefreshLayout.endRefreshing()
        if (result != null && result.size > 0) {
            mMaxPage = result[0].maxPage

            if(mCurrentPage == 1) {
                mGoodsAdapter.setData(result)
            }else{
                mGoodsAdapter.dataList.addAll(result)
                mGoodsAdapter.notifyDataSetChanged()
            }
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_CONTENT // 内容的状态
        } else {
            //没有数据
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_EMPTY // 空的状态
        }
    }

    // 加载更多
    override fun onBGARefreshLayoutBeginLoadingMore(refreshLayout: BGARefreshLayout?): Boolean {
        return if(mCurrentPage < mMaxPage) {
            Handler().postDelayed({
                mCurrentPage++
                loadData()
            },2000)
            true
        }else{
            false
        }
    }

    // 上拉刷新
    override fun onBGARefreshLayoutBeginRefreshing(refreshLayout: BGARefreshLayout?) {
        Handler().postDelayed({
            mCurrentPage = 1
            loadData()
        },2000)
    }
}