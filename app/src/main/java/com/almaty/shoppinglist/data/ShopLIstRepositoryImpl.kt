package com.almaty.shoppinglist.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import com.almaty.shoppinglist.domain.ShopItem
import com.almaty.shoppinglist.domain.ShopListRepository

class ShopLIstRepositoryImpl(
    application: Application
):ShopListRepository{
    private val shopListDao = AppDatabase.getInstance(application).shopListDAO()
    private val mapper = ShopListMapper()
    override fun addShopItem(shopItem: ShopItem) {
        shopListDao.addShopItem(shopItemDb = mapper.mapEntityToDbModel(shopItem))
    }

    override fun deleteShopItem(shopItem: ShopItem) {
        shopListDao.deleteShopItem(shopItem.id)
    }

    override fun editShopItem(shopItem: ShopItem) {
        shopListDao.addShopItem(shopItemDb = mapper.mapEntityToDbModel(shopItem))
    }

    override fun getShopItem(shopItemId: Int): ShopItem {
        val dbModel = shopListDao.getShopItem(shopItemId = shopItemId)
        return mapper.mapDbModelToEntity(dbModel)
    }

    override fun getShopList(): LiveData<List<ShopItem>> = Transformations.map(shopListDao.getShopList())
    {
        mapper.mapListDbModelToListEntity(it)
    }
}