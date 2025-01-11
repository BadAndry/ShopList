package com.example.shoplist.domain

class AddItemListUseCase (private val shopListRepository: ShopListRepository) {
    suspend fun addItemToList(shopItem: ShopItem){
        shopListRepository.addItemToList(shopItem)

    }
}