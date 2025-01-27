package com.example.shoplist.domain

import androidx.lifecycle.LiveData

interface ShopListRepository {
    fun getShopList(): LiveData<List<ShopItem>>
    suspend fun editChangeShopItem(shopItem: ShopItem)
    suspend fun getItemById(id: Int): ShopItem
    suspend fun addItemToList(shopItem: ShopItem)
    suspend fun removeItem(shopItem: ShopItem)

}
