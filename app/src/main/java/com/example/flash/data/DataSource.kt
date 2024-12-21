package com.example.flash.data

import androidx.annotation.StringRes
import com.example.flash.R

object DataSource {
    fun loadCategories(): List<Categories> {
        return listOf(
            Categories(stringResourceId = R.string.shirts, imageResourceId =  R.drawable.shirt),
            Categories(R.string.t_shirts, R.drawable.tshirts),
            Categories(R.string.pants, R.drawable.jeans),
            Categories(R.string.hoodies, R.drawable.hoodies),
            Categories(R.string.sweaters, R.drawable.sweater),
            Categories(R.string.jackets, R.drawable.jackets),
            Categories(R.string.socks, R.drawable.socks),
            Categories(R.string.belts, R.drawable.belts),
            Categories(R.string.hats, R.drawable.hats),
            Categories(R.string.scarves, R.drawable.scarves)
            )

    }
    fun loadItems(
        @StringRes categoryName: Int
    ): List<Item> {

        return listOf(
            Item(R.string.AdidasShirt,R.string.shirts,"1",100,R.drawable.adidasshirt),
            Item(R.string.AdidasTShirt,R.string.t_shirts,"1",100,R.drawable.adidastshirts),
            Item(R.string.AdidasPants,R.string.pants,"1",100,R.drawable.adidaspants),
            Item(R.string.NikeShirt,R.string.shirts,"1",100,R.drawable.nukishirt),
            Item(R.string.NikeTShirt,R.string.t_shirts,"1",100,R.drawable.niketshirts),
            Item(R.string.NikePants,R.string.pants,"1",100,R.drawable.nikepants),
            Item(R.string.PumaShirt,R.string.shirts,"1",100,R.drawable.pumashirts),
            Item(R.string.PumaTShirt,R.string.t_shirts,"1",100,R.drawable.pumatshirts),
            Item(R.string.PumaPants,R.string.pants,"1",100,R.drawable.pumapants),
            Item(R.string.GucciShirt,R.string.shirts,"1",100,R.drawable.guccishirt),
            Item(R.string.GucciTShirt,R.string.t_shirts,"1",100,R.drawable.guccitshirts),
            Item(R.string.GucciPants,R.string.pants,"1",100,R.drawable.guccipants),
            Item(R.string.SnitchShirt,R.string.shirts,"1",100,R.drawable.snitchshirt),
            Item(R.string.SnitchTShirt,R.string.t_shirts,"1",100,R.drawable.snitchtshirts),
            Item(R.string.SnitchPants,R.string.pants,"1",100,R.drawable.snitchpants)
        ).filter{
            it.itemCategoryId == categoryName
        }
    }
}