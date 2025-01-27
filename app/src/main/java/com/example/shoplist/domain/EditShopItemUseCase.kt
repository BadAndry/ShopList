package com.example.shoplist.domain

import javax.inject.Inject

class EditShopItemUseCase @Inject constructor(private val shopListRepository: ShopListRepository) {
    suspend fun editChangeShopItem(shopItem: ShopItem){
        shopListRepository.editChangeShopItem(shopItem)

    }
}