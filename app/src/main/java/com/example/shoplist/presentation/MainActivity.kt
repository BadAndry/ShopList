package com.example.shoplist.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.shoplist.R
import com.example.shoplist.databinding.ActivityMainBinding
import com.example.shoplist.di.ApplicationComponent
import com.example.shoplist.presentation.AddItemActivity.Companion.startIntentAdd
import com.example.shoplist.presentation.AddItemActivity.Companion.strartIntentEdit
import com.google.android.material.floatingactionbutton.FloatingActionButton
import javax.inject.Inject

class MainActivity : AppCompatActivity(), ShopItemFragment.OnEditingFinishedListener{

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: ShopListAdapter
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val component by lazy {
        (application as ShopItemApplication).component
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpAdapter()
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        viewModel.shopList.observe(this) {
            adapter.submitList(it)
        }
        binding.addShopItem.setOnClickListener {
            if(rightFragmentLand()) {
                val intent = startIntentAdd(this)
                startActivity(intent)
            } else {
                launchFragmenLand(ShopItemFragment.startFragmentAdd())
            }
        }
        viewModel = ViewModelProvider(this,viewModelFactory )[MainViewModel::class.java]
        viewModel.shopList.observe(this) {
        }
    }



    override fun onEditingFinishedListener() {
        Toast.makeText(application, "Success", Toast.LENGTH_LONG).show()
    }

    private fun rightFragmentLand(): Boolean{
        return binding.shopItemContainer == null
    }

    private fun launchFragmenLand(fragment: Fragment){
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id.shop_item_container, fragment)
            .addToBackStack(null)
            .commit()
    }
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
                val item = adapter.currentList[viewHolder.adapterPosition]
                viewModel.removeItem(item)
            }
        }
        val itemTouchHelper = ItemTouchHelper(callBack)
        itemTouchHelper.attachToRecyclerView(rvShopList)
    }
}



