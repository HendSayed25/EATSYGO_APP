package com.example.eatsygo_app.source.local

import com.example.eatsygo_app.model.entity.ProductEntity
import com.example.eatsygo_app.source.remote.ClotheApi
import javax.inject.Inject

class ClotheRepository @Inject constructor(
    private val apiService: ClotheApi, private val productDao: ClotheDao
) {

    suspend fun fetchAndSaveProducts() {

        if (productDao.getProductsCount() == 0) {
            // Fetch products from the API
            val products = apiService.getProducts()

            // Convert the fetched products to Room entities
            val productEntities = products.map {
                ProductEntity(
                    it.id,
                    it.title,
                    it.price,
                    it.description,
                    it.category,
                    it.image,
                    it.rating,
                    isFavourite = false,
                    isCartIn = false,
                    itemCount = 0
                )
            }

            // Save products to Room
            productDao.insertAll(productEntities)
        }
    }

    suspend fun updateProduct(product: ProductEntity) {
        productDao.updateProduct(product)
    }

    suspend fun getAllProducts(): List<ProductEntity> {
        return productDao.getAllProducts() // Return the list of products from Room
    }

}