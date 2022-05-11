package com.example.todo_12.domain

import javax.inject.Inject

// служат для взаимодействия data и domain слоя
class AddItemUseCase @Inject constructor(private val repository: ShopListRepository) {

    suspend fun addItem(item: ShopItem) {
        repository.addItem(item)
    }
}