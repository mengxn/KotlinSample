package com.popmart.kotlinandroid

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by mengxn on 2017/9/20.
 */
class BaseAdapter<T>(private val layoutId: Int, private val dataList: MutableList<T> = arrayListOf(), private val bind: (View, T) -> Unit) : RecyclerView.Adapter<BaseAdapter.ViewHolder<T>>() {

    override fun onBindViewHolder(holder: ViewHolder<T>?, position: Int) {
        holder?.let { holder.bind(dataList[holder.adapterPosition]) }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder<T> {
        val view = LayoutInflater.from(parent?.context).inflate(layoutId, parent, false)
        return ViewHolder(view, bind)
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

    fun append(dataList : List<T>) {
        this.dataList.addAll(dataList)
        notifyItemRangeInserted(itemCount - dataList.size, itemCount)
    }

    class ViewHolder<in T>(view : View, private val bind: (View, T) -> Unit) : RecyclerView.ViewHolder(view){
        fun bind(item : T){
            bind(itemView, item)
        }
    }
}