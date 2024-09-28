package com.example.shoplist.domain

class EditShopItemUseCase(private val shopListRepository: ShopListRepository) {
    fun editChangeShopItem(shopItem: ShopItem){
        shopListRepository.editChangeShopItem(shopItem)

    }
}