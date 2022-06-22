package com.almaty.shoppinglist.presentation

import androidx.databinding.BindingAdapter
import com.almaty.shoppinglist.R
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("errorInputName")
fun bindErrorInputName(textInputLayout: TextInputLayout, boolean: Boolean){
    val message = if (boolean){
        textInputLayout.context.getString(R.string.error_input_name)
    }
    else {
        null
    }
    textInputLayout.error = message
}

@BindingAdapter("errorInputCount")
fun bindErrorInputCount(textInputLayout: TextInputLayout, boolean: Boolean){
    val message = if (boolean){
        textInputLayout.context.getString(R.string.error_input_count)
    }
    else {
        null
    }
    textInputLayout.error = message
}