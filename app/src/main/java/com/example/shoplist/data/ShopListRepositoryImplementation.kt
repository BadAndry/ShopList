package com.example.shoplist.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.map
import com.example.shoplist.domain.ShopItem
import com.example.shoplist.domain.ShopListRepository


class ShopListRepositoryImplementation(
application: Application
): ShopListRepository {


    private val shopListDao = AppDataBase.getInstanceDb(application).shopListDao()

    private val mappers = Mappers()


    override suspend fun editChangeShopItem(shopItem: ShopItem) {
        shopListDao.addShopItem(mappers.mapEntityToShopItemDb(shopItem))
    }

    override suspend fun getItemById(id: Int): ShopItem {
        val dbModule = shopListDao.getShopItem(id)
        return mappers.mapDbModelToEntity(dbModule)
    }

    override suspend fun addItemToList(shopItem: ShopItem) {
        shopListDao.addShopItem(mappers.mapEntityToShopItemDb(shopItem))
    }

    override suspend fun removeItem(shopItem: ShopItem) {
        shopListDao.deleteShopItem(shopItem.id)
    }

    override fun getShopList(): LiveData<List<ShopItem>> = shopListDao.getShopListList().map {
        mappers.mapDModelToShopItemList(it)
}
}

