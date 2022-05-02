package com.example.todo_12.data

import com.example.todo_12.domain.ShopItem
import org.junit.Assert.*
import org.junit.Test
import java.lang.NullPointerException

class ShopListMapperTest{

    @Test
    fun `get map entatiy to DbModel`() {

        val dbModel = ShopListMapper()
        val itemShop = ShopItem(id = 0, count = 1, enable = true, name = "Just")
        val expect = dbModel.mapEntityToDbModel(itemShop)
        val actual = ShopItemDbModel(id = 0, count = 1, enable = true, name = "Just")

        assertEquals(expect, actual)
    }

    @Test
    fun justTest() {
        assertEquals(4+3, 8)
    }
}