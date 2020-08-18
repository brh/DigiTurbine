package com.brh.digiturbine.dagger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.brh.digiturbine.DTApp
import com.brh.digiturbine.network.DTService
import com.brh.digiturbine.repository.DTRepository
import com.brh.digiturbine.ui.main.MainViewModel
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import java.lang.RuntimeException


@Module
open class DTModule {

    @Provides
    fun provideRetrofit(): DTService  {
        return Retrofit.Builder()
            .baseUrl("http://ads.appia.com")
            .build().create(DTService::class.java)
    }

    @Provides
    fun provideVMFactory(repository: DTRepository): ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return when(modelClass){
                    MainViewModel::class.java->{
                        MainViewModel(repository) as T
                    }
                    else->throw RuntimeException("Need to expand VMFactory")
                }
            }

        }
    }
}