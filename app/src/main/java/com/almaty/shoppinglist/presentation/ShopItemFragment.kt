package com.almaty.shoppinglist.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.almaty.shoppinglist.R
import com.almaty.shoppinglist.domain.ShopItem
import com.google.android.material.textfield.TextInputLayout
import java.lang.RuntimeException

class ShopItemFragment:Fragment() {

    private lateinit var viewModel: ShopItemViewModel
    private lateinit var tilName:TextInputLayout
    private lateinit var tilCount:TextInputLayout
    private lateinit var etName:EditText
    private lateinit var etCount:EditText
    private lateinit var button_save:Button

    private var screenMode:String = MODE_UNKNOWN
    private var shopItemId:Int = ShopItem.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParam()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shop_item, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
        initViews(view)
        addTextListener()
        launchRightMode()
        obServeViewModel()

    }

    private fun obServeViewModel(){
        viewModel.errorInputCount.observe(viewLifecycleOwner){
            val message = if (it){
                getString(R.string.error_input_count)
            }
            else {
                null
            }
            tilCount.error = message
        }
        viewModel.errorInputName.observe(viewLifecycleOwner){
            val message = if (it){
                getString(R.string.error_input_name)
            }
            else {
                null
            }
            tilName.error = message
        }
        viewModel.shouldClose.observe(viewLifecycleOwner){
            activity?.onBackPressed()
        }
    }

    private fun launchRightMode(){
        when(screenMode){
            MODE_EDIT ->launchEditMode()
            MODE_ADD ->launchAddMode()
        }
    }

    private fun addTextListener(){
        etName.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputName()
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
        etCount.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputCount()
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
    }
    private fun launchEditMode(){
        viewModel.getShopItem(shopItemId)
        viewModel.shopItem.observe(viewLifecycleOwner){
            etName.setText(it.name)
            etCount.setText(it.count.toString())
        }
        button_save.setOnClickListener {
            viewModel.editShopItem(etName.text?.toString(), etCount.text?.toString())
        }
    }
    private fun launchAddMode(){
        button_save.setOnClickListener {
            viewModel.addShopItem(etName.text?.toString(), etCount.text?.toString())
        }
    }

    private fun parseParam(){
        val args = requireArguments()
        if (!args.containsKey(EXTRA_SCREEN_MODE)){
            throw RuntimeException("Param screen mode is absent")
        }
        val mode = args.getString(EXTRA_SCREEN_MODE)
        if(mode!= MODE_EDIT && mode!= MODE_ADD){
            throw RuntimeException("Unknown screen mode $mode")
        }
        screenMode = mode
        if(screenMode == MODE_EDIT){
            if (!args.containsKey(EXTRA_SHOP_ITEM_ID)){
                throw RuntimeException("Param shop item id is absent")
            }
            shopItemId = args.getInt(EXTRA_SHOP_ITEM_ID, ShopItem.UNDEFINED_ID)
        }
    }

    private fun initViews(view: View){
        tilName = view.findViewById(R.id.til_name)
        tilCount = view.findViewById(R.id.til_count)
        etName = view.findViewById(R.id.et_name)
        etCount = view.findViewById(R.id.et_count)
        button_save = view.findViewById(R.id.save_button)

    }


    companion object{
        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val EXTRA_SHOP_ITEM_ID = "extra_shop_item_id"
        private const val MODE_UNKNOWN = ""

        fun newInstanceAddItem():ShopItemFragment{

            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(
                        EXTRA_SCREEN_MODE,
                        MODE_ADD
                    )
                }
            }
        }

        fun newInstanceEditItem(shopItemId: Int):ShopItemFragment{
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_SCREEN_MODE, MODE_EDIT)
                    putInt(EXTRA_SHOP_ITEM_ID, shopItemId)
                }
            }
        }
    }

}