package com.popmart.kotlinandroid

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by mengxn on 2017/9/20.
 */
class BaseAdapter<T>(private val layoutId: Int, private val dataList: MutableList<T>, private val bind: (View, T) -> Unit) : RecyclerView.Adapter<BaseViewHolder<T>>() {

    constructor(layoutId: Int, bind: (View, T) -> Unit) : this(layoutId, arrayListOf(), bind)

    override fun onBindViewHolder(holder: BaseViewHolder<T>?, position: Int) {
        holder?.let { holder.bind(dataList[holder.adapterPosition]) }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder<T> {
        val view = LayoutInflater.from(parent?.context).inflate(layoutId, parent, false)
        return BaseViewHolder(view, bind)
    }

    override fun getItemCount(): Int = dataList.size

    fun setData(dataList: MutableList<T>) {
        this.dataList.clear()
        this.dataList.addAll(dataList)
        notifyDataSetChanged()
    }

    fun append(data: T) {
        dataList.add(data)
        notifyItemInserted(itemCount)
    }

    fun append(dataList: List<T>) {
        this.dataList.addAll(dataList)
        notifyItemRangeInserted(itemCount - dataList.size, itemCount)
    }
}