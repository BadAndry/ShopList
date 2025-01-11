package com.example.shoplist.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.shoplist.R
import com.example.shoplist.databinding.ActivityMainBinding
import com.example.shoplist.presentation.AddItemActivity.Companion.startIntentAdd
import com.example.shoplist.presentation.AddItemActivity.Companion.strartIntentEdit
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel //Инициализируем ViewModel
    private lateinit var adapter: ShopListAdapter
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpAdapter()

        binding.addShopItem.setOnClickListener {
            if(rightFragmentLand()) {
                val intent = startIntentAdd(this)
                startActivity(intent)
            } else {
                launchFragmenLand(ShopItemFragment.startFragmentAdd())
            }
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
    private fun rightFragmentLand(): Boolean{
        return binding.shopItemContainer == null
    }

    private fun launchFragmenLand(fragment: Fragment){
        supportFragmentManager.popBackStack()//Нужен что бьы addToBackStack не гулял по предыдущим данным,
        // а вернулся в положение изначальное
        supportFragmentManager.beginTransaction()
            .replace(R.id.shop_item_container, fragment) //Что бы в одном контейнере не было два похожих фрагмента
            // лучше испольщовать реплейс
            .addToBackStack(null) //Специальный метод который дает возможность
            // не закрыть приложение а вернуть в исходное активти и BackPresed
            .commit()
    }
    //        viewModel.getShopList() отсюда тоже можно удалить метод он и так будет вызвваться
    private fun setUpAdapter() {
        adapter = ShopListAdapter()
        binding.rcView.adapter = adapter
        binding.rcView.recycledViewPool.setMaxRecycledViews(
            ShopListAdapter.VIEW_TYPE_ENABLED, ShopListAdapter.MAX_VIEW_TYPE)
        binding.rcView.recycledViewPool.setMaxRecycledViews(
            ShopListAdapter.VIEW_TYPE_DISEBLED, ShopListAdapter.MAX_VIEW_TYPE)

        adapter.shopItemLongClickListener =  {
                viewModel.changeEnableState(it)
            }
        adapter.shopItemClickListener = {
            if(rightFragmentLand()) {
                val intent = strartIntentEdit(this, it.id)
                startActivity(intent)
            } else {
                launchFragmenLand(ShopItemFragment.stratFragmentEdit(it.id))
            }
        }
        swipe(binding.rcView)

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



