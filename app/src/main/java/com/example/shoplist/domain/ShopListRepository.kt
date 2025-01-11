package com.example.shoplist.domain

import androidx.lifecycle.LiveData

interface ShopListRepository {
    fun getShopList(): LiveData<List<ShopItem>>
    suspend fun editChangeShopItem(shopItem: ShopItem)
    suspend fun getItemById(id: Int): ShopItem
    suspend fun addItemToList(shopItem: ShopItem)
    suspend fun removeItem(shopItem: ShopItem)

}

//Мы создали в домейн слое бизнес логику, есть все методы, которые могут понадобится пользователю,
// и я вся бизнес логика UseCase зависят от интерфейса репозитория,
// это полностью законченная бизнеслогика приложения, которая ни от чего не зависит
//Домейн слой работает только с интерфейсом репозитория, он не должен знать об обработке данных
// и не должен зависит от других источников, типа баззы данных, интернета и т.д.
// Поэтому UseCase зависят от интерфейса репозитория, а не от конкретной реализации

//Data слой предоставляет конкретную реализацию репозитория