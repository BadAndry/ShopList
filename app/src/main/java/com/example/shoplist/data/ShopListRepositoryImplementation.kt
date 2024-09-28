package com.example.shoplist.data

import com.example.shoplist.domain.ShopItem
import com.example.shoplist.domain.ShopListRepository

//Мы делаем реализацию интерфейса репозитория в дате классе посредством создания object класса,
// это класс который имеет свойста singleTone ->
// единственный класс, где бы не обратились будет всегда один и тот же экземпляр
// Мы создаем обычно с названием Implementation(реализация)
object ShopListRepositoryImplementation: ShopListRepository {

//Так как нет бьазы данных, мы создаем переменную, что бы хранить в ней данные
    private val shopList = mutableListOf<ShopItem>() //Тут будем хранить коллекцию элементов

    //Так как при добавлении элемента за id отвечает обычно база данных, но у нас такой нет
    // надо создать такую переменную, которая будет хранить id
    // и инкрементировать ее после ++,
    private var autoIncrement = 0


    override fun addItemToList(shopItem: ShopItem) {
        if(shopItem.id == ShopItem.UNDEF_ID){ // проверка нужна, что бы изменять можно было,
            // если элемент неопределенный имет id
            // , то мы его установим  autoIncrement,
        shopItem.id = autoIncrement
        autoIncrement++}
        shopList.add(shopItem)// тут просто добавялем эллемент в коллецию нашей переменной
    }

    override fun getShopList(): List<ShopItem> {
        return shopList.toList() //Тут мы возвращаем коллекцию,
    // но не ее саму, а ее копию, посредством метода  toList, что бы нельзя было получить доступ
    // к этой коллекции из других мест программы

    }

    override fun editChangeShopItem(shopItem: ShopItem) {
        //Если мы изменяем, то сначала мы находим старый эллемент по его ID
    // удаляпем и только потом добавялем заменяемый
        //Для этого создаем переменную и удалчем этот эллемент, следом добавляем новый
        val oldElement = getItemById(shopItem.id)
        shopList.remove(oldElement)
        addItemToList(shopItem)
    }

    override fun getItemById(id: Int): ShopItem {
        return shopList.find { it.id == id } ?: throw RuntimeException("Error ${id} not found")
    }//метод find может передать null и если по нашей бизнес логике возможна передача null
    // ,то мы меняем ShopItem как nullaбельный. override fun getItemById(id: Int): ShopItem? {
    //        return shopList.find { it.id == id } если нет то через ?: throw RuntimeException

    override fun removeItem(shopItem: ShopItem) {
        shopList.remove(shopItem)// тут просто удаляем эллемент из коллеции нашей переменной
    }
}