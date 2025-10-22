package com.example.flash.data

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class ProductRepository {

    private val db = Firebase.firestore

    /**
     * Fetches the list of all categories from the "categories" collection.
     */
    suspend fun getCategories(): List<Category> {
        return try {
            db.collection("categories").get().await().toObjects(Category::class.java)
        } catch (e: Exception) {
            // In a real app, you would log this error
            emptyList()
        }
    }

    /**
     * Fetches all products that match a specific category name.
     */
    suspend fun getProducts(categoryName: String): List<Item> {
        return try {
            db.collection("products")
                .whereEqualTo("categoryName", categoryName)
                .get()
                .await()
                .toObjects(Item::class.java)
        } catch (e: Exception) {
            // In a real app, you would log this error
            emptyList()
        }
    }
}