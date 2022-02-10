package com.example.todo_12.domain

class DeleteItemUseCase(private val repository: ShopListRepository) {

    suspend fun deleteItem(item: ShopItem) {
        repository.deleteItem(item)
    }
}