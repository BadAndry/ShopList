package com.example.shoplist.domain

import javax.inject.Inject

class RemoveItemListUseCase @Inject constructor(private val shopListRepository: ShopListRepository) {
    suspend fun removeItem(shopItem: ShopItem){
        shopListRepository.removeItem(shopItem)
    }
}