package com.example.flash.ui

import com.example.flash.data.Category
import com.example.flash.data.Item

data class FlashUiState(
    // Data from Firestore
    val categories: List<Category> = emptyList(),
    val products: List<Item> = emptyList(),
    val selectedCategory: Category? = null,

    // UI status
    val isLoading: Boolean = false,
    val error: String? = null,

    // Cart state remains the same
    val cartItems: Map<Item, Int> = emptyMap(),
    val cartTotal: Double = 0.0
)