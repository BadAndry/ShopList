package com.example.shoplist.domain

class GetItemByIdUseCase(private val shopListRepository: ShopListRepository) {
    suspend fun getItemById(id: Int): ShopItem {
        return shopListRepository.getItemById(id)

    }
}