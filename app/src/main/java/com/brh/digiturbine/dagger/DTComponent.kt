package com.brh.digiturbine.dagger

import com.brh.digiturbine.ui.main.DetailFragment
import com.brh.digiturbine.ui.main.ListFragment
import com.brh.digiturbine.ui.main.MainViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DTModule::class])
interface DTComponent {

    fun inject(vm: MainViewModel)
    fun inject(fragment: ListFragment)
    fun inject(detailFragment: DetailFragment)
}