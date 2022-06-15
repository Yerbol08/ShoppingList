package com.almaty.shoppinglist.presentation

import androidx.recyclerview.widget.DiffUtil
import com.almaty.shoppinglist.domain.ShopItem
import java.util.*

class ShopListDiffCallback(private val  oldList: List<ShopItem>,
                            private val newList: List<ShopItem>
                            ):DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItemId = oldList[oldItemPosition].id
        val newItemId = newList[newItemPosition].id
        return oldItemId == newItemId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem == newItem
    }
}