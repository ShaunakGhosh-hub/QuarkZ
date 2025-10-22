package com.example.flash.data

/**
 * Represents a single category document from Firestore.
 * The empty constructor is needed for Firestore's automatic data mapping.
 */
data class Category(
    val name: String = "",
    val imageUrl: String = ""
) {
    constructor() : this("", "")
}