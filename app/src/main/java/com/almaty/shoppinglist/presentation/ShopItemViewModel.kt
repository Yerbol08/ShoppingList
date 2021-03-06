package com.almaty.shoppinglist.presentation

import android.os.CountDownTimer
import androidx.lifecycle.ViewModel
import com.almaty.shoppinglist.data.ShopLIstRepositoryImpl
import com.almaty.shoppinglist.domain.AddShopItemUseCase
import com.almaty.shoppinglist.domain.EditShopItemUseCase
import com.almaty.shoppinglist.domain.GetShopItemUseCase
import com.almaty.shoppinglist.domain.ShopItem

class ShopItemViewModel:ViewModel() {
    private val repository = ShopLIstRepositoryImpl

    private val getShopItemUseCase = GetShopItemUseCase(repository)
    private val addShopItemUseCase = AddShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    fun getShopItem(shopItemId:Int){
        val item = getShopItemUseCase.getShopItem(shopItemId)
    }
    fun addShopItem(inputName:String?, inputCount: String?){
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldsValid = validateInput(name, count)
        if (fieldsValid){
            val shopItem = ShopItem(name, count, true)
            addShopItemUseCase.addShopItem(shopItem)
        }
    }
    fun editShopItem(inputName:String?, inputCount: String?){

        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldValid = validateInput(name, count)
        if (fieldValid){
            val shopItem = ShopItem(name, count, true)
            editShopItemUseCase.editShopItem(shopItem)
        }

    }

    private fun parseName(inputName: String?):String{
        return inputName?.trim()?:""
    }
    private fun parseCount(inputCount: String?):Int{
        return try {
            inputCount?.trim()?.toInt()?:0
        }catch (e:Exception){
            0
        }
    }

    private fun validateInput(name:String, count:Int):Boolean{
        var result = true
        if(name.isBlank()){
            //TODO: show error input name
            result = false
        }
        if (count <= 0){
            //TODO: show error input count
            result = false
        }
        return result
    }
}