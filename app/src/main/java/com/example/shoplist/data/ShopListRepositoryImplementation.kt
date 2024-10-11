package com.example.shoplist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shoplist.domain.ShopItem
import com.example.shoplist.domain.ShopListRepository
import kotlin.random.Random

//Мы делаем реализацию интерфейса репозитория в дате классе посредством создания object класса,
// это класс который имеет свойста singleTone ->
// единственный класс, где бы не обратились будет всегда один и тот же экземпляр
// Мы создаем обычно с названием Implementation(реализация)
object ShopListRepositoryImplementation: ShopListRepository {
    //Тут можем создать liveData для автоматического обновления, чтр бы не вызывать постоянно getItemList
    private val shopListLD = MutableLiveData<List<ShopItem>>()

//Так как нет бьазы данных, мы создаем переменную, что бы хранить в ней данные
    private val shopList = sortedSetOf(Comparator<ShopItem> { o1, o2 -> o1.id compareTo  o2.id })

//Этим методом мы сортируем наши итемы, что бы при редактирование они не падали в конец списка
//Тут будем хранить коллекцию элементов

    //Так как при добавлении элемента за id отвечает обычно база данных, но у нас такой нет
    // надо создать такую переменную, которая будет хранить id
    // и инкрементировать ее после ++,
    private var autoIncrement = 0

    init {
        for(i in 1 until 10){
            val list = ShopItem("Name $i", i, true)
            addItemToList(list)
        }
    }



    override fun addItemToList(shopItem: ShopItem) {
        if(shopItem.id == ShopItem.UNDEF_ID){ // проверка нужна, что бы изменять можно было,
            // если элемент неопределенный имет id
            // , то мы его установим  autoIncrement,
        shopItem.id = autoIncrement
        autoIncrement++}
        shopList.add(shopItem)// тут просто добавялем эллемент в коллецию нашей переменной
        updateShopLisy()
    }

    override fun getShopList(): LiveData<List<ShopItem>> {
        return shopListLD

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
        updateShopLisy()
    }

    //Создаем функцию, в которую передаем shopList для обновления и возвращаем копию
    private fun updateShopLisy(){
        shopListLD.value = shopList.toList()//Тут мы возвращаем коллекцию,
        // но не ее саму, а ее копию, посредством метода  toList, что бы нельзя было получить доступ
        // к этой коллекции из других мест программы
    }
}