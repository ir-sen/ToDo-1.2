package com.example.todo_12.domain

class GetShopListUseCase(private val repository: ShopListRepository) {

    fun getShopList(): List<ShopItem> {
        return repository.getShopList()
    }
}