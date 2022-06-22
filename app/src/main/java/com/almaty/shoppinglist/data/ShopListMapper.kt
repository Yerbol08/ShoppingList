package com.almaty.shoppinglist.data

import com.almaty.shoppinglist.domain.ShopItem

class ShopListMapper {
    fun mapEntityToDbModel(shopItem: ShopItem)= ShopItemDBModel(
        id = shopItem.id,
        name = shopItem.name,
        count = shopItem.count,
        enabled = shopItem.enabled
    )

    fun mapDbModelToEntity(shopItemDB: ShopItemDBModel)= ShopItem(
        id = shopItemDB.id,
        name = shopItemDB.name,
        count = shopItemDB.count,
        enabled = shopItemDB.enabled
    )
    fun mapListDbModelToListEntity(list: List<ShopItemDBModel>) = list.map {
        mapDbModelToEntity(it)
    }
}