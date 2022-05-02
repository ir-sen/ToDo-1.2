package com.example.todo_12.presentation

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.todo_12.CoroutineTestRule
import com.example.todo_12.domain.ShopItem
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class MainViewModelTest{

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()



    val roboto = MainViewModel(ApplicationProvider.getApplicationContext())
    val testShopItem = ShopItem("Name" , 4, false, 3)
    @Test
    fun `test enable state`() {
        roboto.changeEnableState(ShopItem("Name" , 4, false, 3))
    }





}