package com.example.shoplist.domain

class RemoveItemListUseCase(private val shopListRepository: ShopListRepository) {
    fun removeItem(shopItem: ShopItem){
        shopListRepository.removeItem(shopItem)
    }
}