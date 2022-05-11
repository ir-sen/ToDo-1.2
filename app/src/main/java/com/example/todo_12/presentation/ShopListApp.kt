package com.example.todo_12.presentation

import android.app.Application
import com.example.todo_12.di.DaggerApplicationComponent

class ShopListApp: Application() {

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }

}