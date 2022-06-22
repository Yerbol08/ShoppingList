package com.almaty.shoppinglist.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.almaty.shoppinglist.R
import com.almaty.shoppinglist.databinding.ActivityMainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), ShopItemFragment.OnEditingFinishedListener{

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var shopListAdapter: ShopListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.shopList.observe(this){
            shopListAdapter.submitList(it)
        }
        binding.buttonAddShopItem.setOnClickListener {
            if (isOnePaneMode()){
                val intent = ShopItemActivity.newIntentAddItem(context = this)
                startActivity(intent)
            }
            else{
                launchFragment(ShopItemFragment.newInstanceAddItem())
            }
        }
    }

    private fun isOnePaneMode():Boolean{
        return binding.shopItemContainer == null
    }

    private fun launchFragment(fragment: Fragment){
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id.shop_item_container, fragment)
            .addToBackStack(null)
            .commit()
    }
    override fun onEditingFinished() {
        supportFragmentManager.popBackStack()
    }

    private fun setupRecyclerView(){

        with(binding.rvShopList){
            shopListAdapter = ShopListAdapter()
            adapter = shopListAdapter
            recycledViewPool.setMaxRecycledViews(
                ShopListAdapter.VIEW_TYPE_DISABLED,
                ShopListAdapter.MAX_POOL_SIZE
            )
            recycledViewPool.setMaxRecycledViews(
                ShopListAdapter.VIEW_TYPE_ENABLED,
                ShopListAdapter.MAX_POOL_SIZE
            )
        }

        setupOnClickLong()
        setupOnClick()
        setupSwipeListener(binding.rvShopList)
    }

    private fun setupSwipeListener(rvShopList: RecyclerView?) {
        val callback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = shopListAdapter.currentList[viewHolder.adapterPosition]
                viewModel.deleteShopItem(item)
            }
        }

        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rvShopList)
    }

    private fun setupOnClickLong() {
        shopListAdapter.onShopItemLongClickListener = {
            viewModel.changeEnableState(it)
        }
    }

    private fun setupOnClick() {
        shopListAdapter.onShopItemClickListener = {
            if (isOnePaneMode()){
                val intent = ShopItemActivity.newIntentEditItem(context = this, shopItemId = it.id)
                startActivity(intent)
            }
            else{
                launchFragment(ShopItemFragment.newInstanceEditItem(shopItemId = it.id))
            }
        }
    }

}