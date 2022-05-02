package com.example.todo_12.data

import com.example.todo_12.domain.ShopItem
// приобразования одной сущности в другую
class ShopListMapper {

    fun mapEntityToDbModel(shopItem: ShopItem): ShopItemDbModel {
        return ShopItemDbModel(
            id = shopItem.id,
            count = shopItem.count,
            enable = shopItem.enable,
            name = shopItem.name
        )
    }

    fun mapDbModelToEntity(shopItem: ShopItemDbModel): ShopItem {
        return ShopItem(
            id = shopItem.id,
            count = shopItem.count,
            enable = shopItem.enable,
            name = shopItem.name
        )
    }

    fun mapListDbModelToListEntity(list: List<ShopItemDbModel>) = list.map {
        mapDbModelToEntity(it)
    }
}