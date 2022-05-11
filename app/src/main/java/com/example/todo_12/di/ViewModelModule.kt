package com.example.todo_12.di

import androidx.lifecycle.ViewModel
import com.example.todo_12.presentation.ItemViewModel
import com.example.todo_12.presentation.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindShopViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ItemViewModel::class)
    fun bindItemViewModel(viewModel: ItemViewModel): ViewModel

}