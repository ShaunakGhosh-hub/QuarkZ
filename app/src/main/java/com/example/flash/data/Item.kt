package com.example.flash.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Item(
    @StringRes val stringResourceId: Int,
    @StringRes val itemCategoryId: Int,
    val itemQuantityId:String,
    val itemPrice:Int,
    @DrawableRes val imageResourceId: Int
)
