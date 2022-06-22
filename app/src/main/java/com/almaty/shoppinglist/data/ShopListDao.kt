package com.almaty.shoppinglist.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ShopListDao {

    @Query("SELECT * FROM shop_item")
    fun getShopList():LiveData<List<ShopItemDBModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addShopItem(shopItemDb:ShopItemDBModel)

    @Query("DELETE FROM shop_item WHERE id=:shopItemId")
    fun deleteShopItem(shopItemId:Int)

    @Query("SELECT * FROM shop_item WHERE id =:shopItemId LIMIT 1")
    fun getShopItem(shopItemId:Int):ShopItemDBModel
}