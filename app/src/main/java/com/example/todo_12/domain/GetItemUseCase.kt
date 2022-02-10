package com.example.todo_12.domain

class GetItemUseCase(private val repository: ShopListRepository) {

    suspend fun getItem(id: Int): ShopItem {
        return repository.getItem(id)
    }
}