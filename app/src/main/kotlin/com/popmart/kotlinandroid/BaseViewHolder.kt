package com.popmart.kotlinandroid

import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Created by mengxn on 2017/9/21.
 */
class BaseViewHolder<in T>(view : View, private val bind: (View, T) -> Unit) : RecyclerView.ViewHolder(view){
    fun bind(item : T){
        bind(itemView, item)
    }
}