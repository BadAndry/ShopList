package com.example.shoplist.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.shoplist.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel //Инициализируем ViewModel
    private lateinit var adapter: ShopListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpAdapter()
        val addButton = findViewById<FloatingActionButton>(R.id.add_shop_item)
        addButton.setOnClickListener {
            val intent = ConstantParam.startIntentAdd(this)
            startActivity(intent)
        }
        //Далее присваеваем черзе специальный метод ViewModelProvider свой класс ViewModel
        // (В данном случае MainViewModel)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        //Далее подписываемся на обьект шоп лист из LiveData через срец метод observe()
        // c лямда выражением it = List<ShopItem>
        viewModel.shopList.observe(this) {//Сюда будут приходить все новые элементы
            adapter.submitList(it)//Передаем список элементов (it) приведенных
        // к строчному типу через ListAdapet это функция submeetlist(it)
        }
    }
    //        viewModel.getShopList() отсюда тоже можно удалить метод он и так будет вызвваться
    private fun setUpAdapter() {
        val rvShopList = findViewById<RecyclerView>(R.id.rcView)
        adapter = ShopListAdapter()
        rvShopList.adapter = adapter
        rvShopList.recycledViewPool.setMaxRecycledViews(
            ShopListAdapter.VIEW_TYPE_ENABLED, ShopListAdapter.MAX_VIEW_TYPE)
        rvShopList.recycledViewPool.setMaxRecycledViews(
            ShopListAdapter.VIEW_TYPE_DISEBLED, ShopListAdapter.MAX_VIEW_TYPE)

        adapter.shopItemLongClickListener =  {
                viewModel.changeEnableState(it)
            }
        adapter.shopItemClickListener = {
            val intent = ConstantParam.strartIntentEdit(this, it.id)
            startActivity(intent)
        }
        swipe(rvShopList)

    }
    private fun swipe(rvShopList: RecyclerView?) {
        val callBack = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = adapter.currentList[viewHolder.adapterPosition]//В ListAdaptere заменяем
                // метод получения списка на currentList
                viewModel.removeItem(item)
            }
        }
        val itenTouchHelper = ItemTouchHelper(callBack)
        itenTouchHelper.attachToRecyclerView(rvShopList)
    }
}