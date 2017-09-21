package com.popmart.kotlinandroid

import android.graphics.Rect
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_main.view.*
import org.jetbrains.anko.AnkoLogger

class MainActivity : AppCompatActivity(), AnkoLogger {

    private val DEFAULT_TEXT = "葩趣是一个关于潮流玩具的专业社交平台，旨在给各位潮流玩具爱好者提供一个舒适、有趣的交流环境。葩趣以不同的玩具社交圈子为载体，让玩家能够更容易的找到与自己爱好相同的朋友。同时，葩趣坚持提供最前沿的潮流玩具资讯与活动情报，也为玩具爱好者提供了全面的玩具库，帮助玩家更快捷的收藏分类自己的玩具。葩趣，让玩具更好玩！"
    private val DEFAULT_AD_TEXT = "这里是广告位，Hah~"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DivideDecoration())
        recyclerView.addOnScrollListener(StickyItemScrollListener())

//        recyclerView.adapter = initAdapter()
        recyclerView.adapter = initMultiAdapter()
    }

    private fun initAdapter(): BaseAdapter<String> {
        val dataList = (0..20).map { "%d >> %s".format(it, DEFAULT_TEXT) }.toMutableList()
        return BaseAdapter(R.layout.item_main, dataList) { view, s ->
            view.contentTv.text = s
            view.timeTv.text = "2017-09-17 23:30:00".toDefaultFormattedTime()
        }
    }

    private fun initMultiAdapter(): MultiTypeBaseAdapter<String> {
        val typeFactory = object : ITypeFactory<String> {

            override fun type(data: String): ITypeFactory.TypeData {
                return when (data.subSequence(0, 2).toString().trim().toInt() % 2 + 1) {
                    1 -> ITypeFactory.TypeData(1, R.layout.item_main)
                    2 -> ITypeFactory.TypeData(2, R.layout.item_main_2)
                    else -> throw Exception("")
                }
            }

            override fun createViewHolder(view: View, type: Int): BaseViewHolder<String> {
                return when (type) {
                    1 -> BaseViewHolder(view) { view, s ->
                        view.contentTv.text = s
                        view.timeTv.text = "2017-09-17 23:30:00".toDefaultFormattedTime()
                    }
                    2 -> BaseViewHolder<String>(view) { view, s ->
                        view.contentTv.text = s
                    }
                    else -> throw Exception("cannot find viewType")
                }
            }
        }
        val dataList = (0..20).map { "%d >> %s".format(it, if (it%2 == 0) DEFAULT_TEXT else DEFAULT_AD_TEXT) }.toMutableList()
        return MultiTypeBaseAdapter<String>(dataList, typeFactory)
    }

    class DivideDecoration : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
            super.getItemOffsets(outRect, view, parent, state)
            outRect?.top = 50
        }
    }
}
