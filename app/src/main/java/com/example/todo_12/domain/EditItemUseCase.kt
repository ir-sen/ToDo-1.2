package com.example.todo_12.domain

import javax.inject.Inject

class EditItemUseCase @Inject constructor(private val repository: ShopListRepository) {

    suspend fun editItem(item: ShopItem) {
        repository.editItem(item)
    }
}