package com.example.shoplist.domain

import javax.inject.Inject

class AddItemListUseCase @Inject constructor(private val shopListRepository: ShopListRepository) {
    suspend fun addItemToList(shopItem: ShopItem){
        shopListRepository.addItemToList(shopItem)

    }
}