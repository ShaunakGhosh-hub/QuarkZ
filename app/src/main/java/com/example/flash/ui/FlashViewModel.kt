package com.example.flash.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flash.data.Category
import com.example.flash.data.Item
import com.example.flash.data.ProductDetail
import com.example.flash.data.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FlashViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(FlashUiState())
    val uiState: StateFlow<FlashUiState> = _uiState.asStateFlow()

    private val _selectedProduct = MutableStateFlow<ProductDetail?>(null)
    val selectedProduct: StateFlow<ProductDetail?> = _selectedProduct.asStateFlow()

    private val repository = ProductRepository()

    init {
        fetchCategories()
    }

    // ---------------- FETCHING DATA ----------------
    private fun fetchCategories() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val categoryList = repository.getCategories()
                _uiState.update {
                    it.copy(categories = categoryList, isLoading = false)
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(error = e.message, isLoading = false)
                }
            }
        }
    }

    fun selectCategory(category: Category) {
        _uiState.update { it.copy(selectedCategory = category, products = emptyList()) }
        fetchProductsForCategory(category.name)
    }

    private fun fetchProductsForCategory(categoryName: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val productList = repository.getProducts(categoryName)
                _uiState.update {
                    it.copy(products = productList, isLoading = false)
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(error = e.message, isLoading = false)
                }
            }
        }
    }

    // ---------------- PRODUCT DETAILS ----------------
    fun setSelectedProduct(product: ProductDetail) {
        _selectedProduct.value = product
    }

    fun clearSelectedProduct() {
        _selectedProduct.value = null
    }

    // ---------------- CART LOGIC ----------------
    fun addToCart(item: Item) {
        _uiState.update { currentState ->
            val mutableCart = currentState.cartItems.toMutableMap()
            mutableCart[item] = (mutableCart[item] ?: 0) + 1
            currentState.copy(
                cartItems = mutableCart,
                cartTotal = calculateTotal(mutableCart)
            )
        }
    }

    fun removeFromCart(item: Item) {
        _uiState.update { currentState ->
            val mutableCart = currentState.cartItems.toMutableMap()
            val currentQuantity = mutableCart[item] ?: 0

            if (currentQuantity > 1) {
                mutableCart[item] = currentQuantity - 1
            } else {
                mutableCart.remove(item)
            }

            currentState.copy(
                cartItems = mutableCart,
                cartTotal = calculateTotal(mutableCart)
            )
        }
    }

    fun deleteFromCart(item: Item) {
        _uiState.update { currentState ->
            val mutableCart = currentState.cartItems.toMutableMap()
            mutableCart.remove(item)
            currentState.copy(
                cartItems = mutableCart,
                cartTotal = calculateTotal(mutableCart)
            )
        }
    }

    private fun calculateTotal(cart: Map<Item, Int>): Double {
        return cart.entries.sumOf { (item, quantity) ->
            val discountedPrice = item.price * (1 - (item.discountPercent / 100.0))
            discountedPrice * quantity
        }
    }

    // ✅ FIXED: Now correctly adds item with size into map structure
    fun addToCartWithSize(product: ProductDetail, size: String) {
        val item = Item(
            name = "${product.name} ($size)", // Distinguish size in cart
            brand = product.brand,
            categoryName = product.categoryName,
            price = product.price,
            description = product.description?.ifEmpty { "No description available." }
                ?: "No description available.",
            discountPercent = product.discountPercent,
            imageUrl = product.imageUrl
        )

        _uiState.update { currentState ->
            val mutableCart = currentState.cartItems.toMutableMap()
            mutableCart[item] = (mutableCart[item] ?: 0) + 1
            currentState.copy(
                cartItems = mutableCart,
                cartTotal = calculateTotal(mutableCart)
            )
        }
    }

    // ---------------- PRODUCT DETAIL FETCH ----------------
    fun fetchProductDetail(productId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                // Example placeholder, can integrate repository.getProductDetail(productId)
                _uiState.update { it.copy(isLoading = false) }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(error = e.message, isLoading = false)
                }
            }
        }
    }

    // ---------------- UTILITIES ----------------
    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }

    fun getCartItemsCount(): Int {
        return _uiState.value.cartItems.values.sum()
    }
}
