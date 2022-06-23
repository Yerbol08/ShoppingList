package com.almaty.shoppinglist.domain

class GetShopItemUseCase(private val shopListRepository: ShopListRepository) {
    suspend fun getShopItem(shopItem: Int):ShopItem{
        return shopListRepository.getShopItem(shopItem)
    }
}