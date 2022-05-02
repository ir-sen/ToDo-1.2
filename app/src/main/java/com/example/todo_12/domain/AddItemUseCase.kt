package com.example.todo_12.domain

// служат для взаимодействия data и domainare слоя
class AddItemUseCase(private val repository: ShopListRepository) {

    suspend fun addItem(item: ShopItem) {
        repository.addItem(item)
    }
}