package com.example.todo_12.domain

class AddItemUseCase(private val repository: ShopListRepository) {

    suspend fun addItem(item: ShopItem) {
        repository.addItem(item)
    }
}