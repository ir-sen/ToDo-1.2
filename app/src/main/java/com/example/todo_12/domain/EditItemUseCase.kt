package com.example.todo_12.domain

class EditItemUseCase(private val repository: ShopListRepository) {

    suspend fun editItem(item: ShopItem) {
        repository.editItem(item)
    }
}