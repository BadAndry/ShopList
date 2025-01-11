package com.example.shoplist.domain

class RemoveItemListUseCase(private val shopListRepository: ShopListRepository) {
    suspend fun removeItem(shopItem: ShopItem){
        shopListRepository.removeItem(shopItem)
    }
}