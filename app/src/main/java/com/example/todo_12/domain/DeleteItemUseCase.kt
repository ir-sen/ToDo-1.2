package com.example.todo_12.domain

import javax.inject.Inject

class DeleteItemUseCase @Inject constructor(private val repository: ShopListRepository) {

    suspend fun deleteItem(item: ShopItem) {
        repository.deleteItem(item)
    }
}