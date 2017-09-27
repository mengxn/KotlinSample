package com.popmart.kotlinandroid

import android.view.View

/**
 * Created by mengxn on 2017/9/20.
 */

class BaseAdapter<T>(private val layoutId: Int, dataList: MutableList<T>, private val bind: (View, T) -> Unit) : MultiTypeBaseAdapter<T>(dataList, object : ITypeFactory<T>{

    override fun type(data: T): ITypeFactory.TypeData {
        return ITypeFactory.TypeData(0, layoutId)
    }

    override fun createViewHolder(view: View, type: Int): BaseViewHolder<T> {
        return BaseViewHolder(view, bind)

    }
}){
    constructor(layoutId: Int, bind: (View, T) -> Unit): this(layoutId, arrayListOf(), bind)
}