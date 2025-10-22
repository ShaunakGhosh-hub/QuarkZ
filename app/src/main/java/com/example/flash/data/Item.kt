package com.example.flash.data

/**
 * Represents a single product document from Firestore.
 * The empty constructor is needed for Firestore's automatic data mapping.
 */
data class Item(
    val name: String = "",
    val brand: String = "",
    val categoryName: String = "",
    val price: Double = 0.0,
    val description: String? = null,
    val discountPercent: Int = 0,
    val imageUrl: String = ""
) {
    // Properly aligned secondary constructor for Firestore
    constructor() : this("", "", "", 0.0, null, 0, "")
}


/**
 * Represents product variants (sizes and stock)
 */
data class ProductVariant(
    val size: String = "",
    val stock: Int = 0
) {
    constructor() : this("", 0)
}

/**
 * Extended product details for the product detail screen
 */
data class ProductDetail(
    val id: String = "",
    val name: String = "",
    val brand: String = "",
    val categoryName: String = "",
    val price: Double = 0.0,
    val description: String? = null,
    val discountPercent: Int = 0,
    val imageUrl: String = "",
    val variants: List<ProductVariant> = emptyList()
) {
    constructor() : this("", "", "", "", 0.0, null, 0, "", emptyList())
}

/**
 * Related products for "Pair it with" section
 */
data class RelatedProduct(
    val id: String = "",
    val name: String = "",
    val price: Double = 0.0,
    val imageUrl: String = ""
) {
    constructor() : this("", "", 0.0, "")
}