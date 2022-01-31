package com.example.todo_12.domain

class GetItemUseCase(private val repository: ShopListRepository) {

    fun getItem(id: Int): ShopItem {
        return repository.getItem(id)
    }
}