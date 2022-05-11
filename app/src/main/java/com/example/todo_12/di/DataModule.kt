package com.example.todo_12.di

import android.app.Application
import com.example.todo_12.data.AppDataBase
import com.example.todo_12.data.RepositoryImpl
import com.example.todo_12.data.ShopListDao
import com.example.todo_12.domain.ShopListRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @ApplicationScope
    @Binds
    fun bindShopListRepository(impl: RepositoryImpl): ShopListRepository


    companion object {

        @ApplicationScope
        @Provides
        fun provideShopDao(
            application: Application
        ): ShopListDao {
            return AppDataBase.getInstance(application).shopListDao()
        }
    }

}