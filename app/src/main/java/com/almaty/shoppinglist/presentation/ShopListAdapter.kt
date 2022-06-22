package com.almaty.shoppinglist.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import com.almaty.shoppinglist.R
import com.almaty.shoppinglist.databinding.ItemShopDisabledBinding
import com.almaty.shoppinglist.databinding.ItemShopEnebledBinding
import com.almaty.shoppinglist.domain.ShopItem

class ShopListAdapter: ListAdapter<ShopItem, ShopItemViewHolder>(ShopItemDiffCallback()) {


    var onShopItemLongClickListener:((ShopItem) ->Unit)? = null
    var onShopItemClickListener:((ShopItem) ->Unit)? = null



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {

        val layout = when(viewType){
            VIEW_TYPE_ENABLED ->R.layout.item_shop_enebled
            VIEW_TYPE_DISABLED ->R.layout.item_shop_disabled
            else->throw RuntimeException("Unknown view type: $viewType")
        }
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            layout,
            parent,
            false
        )
        return ShopItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val shopItem = getItem(position)
        val binding = holder.binding
        binding.root.setOnLongClickListener {
            onShopItemLongClickListener?.invoke(shopItem)
            true
        }
        binding.root.setOnClickListener{
            onShopItemClickListener?.invoke(shopItem)
        }
        when(binding){
            is ItemShopDisabledBinding -> {
                binding.shopItem = shopItem
            }
            is ItemShopEnebledBinding ->{
                binding.shopItem = shopItem
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)

        return if(item.enabled){
            VIEW_TYPE_ENABLED
        }else{
            VIEW_TYPE_DISABLED
        }
    }

    companion object{
        const val VIEW_TYPE_ENABLED = 100
        const val VIEW_TYPE_DISABLED = 101
        const val MAX_POOL_SIZE = 30
    }
}