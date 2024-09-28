package com.example.shoplist.domain

class AddItemListUseCase(private val shopListRepository: ShopListRepository) {
    fun addItemToList(shopItem: ShopItem){
        shopListRepository.addItemToList(shopItem)

    }
}