package com.popmart.kotlinandroid

import android.graphics.Rect
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_main.view.*
import org.jetbrains.anko.AnkoLogger

class MainActivity : AppCompatActivity(), AnkoLogger{

    private val DEFAULT_TEXT = "葩趣是一个关于潮流玩具的专业社交平台，旨在给各位潮流玩具爱好者提供一个舒适、有趣的交流环境。葩趣以不同的玩具社交圈子为载体，让玩家能够更容易的找到与自己爱好相同的朋友。同时，葩趣坚持提供最前沿的潮流玩具资讯与活动情报，也为玩具爱好者提供了全面的玩具库，帮助玩家更快捷的收藏分类自己的玩具。葩趣，让玩具更好玩！"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DivideDecoration())
        recyclerView.addOnScrollListener(StickyItemScrollListener())
        var contentAdapter = ContentAdapter()
        recyclerView.adapter = contentAdapter
        contentAdapter.setData((0..20).map {"%d >> %s".format(it, DEFAULT_TEXT)})

    }

    class ContentAdapter : RecyclerView.Adapter<ContentAdapter.ContentViewHolder>(), AnkoLogger {

        private var dataList:List<String>? = null

        fun setData(dataList: List<String>) {
            this.dataList = dataList
            this.notifyDataSetChanged()
        }

        override fun getItemCount(): Int = dataList?.size ?: 0

        override fun onBindViewHolder(holder: ContentViewHolder?, position: Int) {
            holder?.let { it.itemView.contentTv.text = dataList?.get(position) }
        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ContentViewHolder {
            val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_main, parent, false)
            return ContentViewHolder(view)
        }

        class ContentViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView)
    }

    class DivideDecoration : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
            super.getItemOffsets(outRect, view, parent, state)
            outRect?.top = 50
        }
    }
}
