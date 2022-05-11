package com.example.todo_12.domain

import javax.inject.Inject

class GetItemUseCase @Inject constructor(private val repository: ShopListRepository) {

    suspend fun getItem(id: Int): ShopItem {
        return repository.getItem(id)
    }
}