package com.popmart.kotlinandroid

import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.ViewGroup

/**
 * Created by mengxn on 2017/9/21.
 */
open class MultiTypeBaseAdapter<T>(private val dataList: MutableList<T>, private val typeFactory: ITypeFactory<T>) : RecyclerView.Adapter<BaseViewHolder<T>>() {

    constructor(typeFactory: ITypeFactory<T>) : this(arrayListOf(), typeFactory)

    private val typeDataArray = SparseArray<ITypeFactory.TypeData>()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder<T> {
        parent?.let {
            val view = LayoutInflater.from(parent.context).inflate(typeDataArray[viewType].layoutId, parent, false)
            return typeFactory.createViewHolder(view, viewType)
        }
        throw Exception("cannot find viewType")
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: BaseViewHolder<T>?, position: Int) {
        holder?.let { holder.bind(dataList[holder.adapterPosition]) }
    }

    override fun getItemViewType(position: Int): Int {
        val typeData = typeFactory.type(dataList[position])
        typeDataArray.put(typeData.type, typeData)
        return typeData.type
    }

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


