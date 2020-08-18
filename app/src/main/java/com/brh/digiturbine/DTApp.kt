package com.brh.digiturbine

import android.app.Application
import com.brh.digiturbine.dagger.DTComponent
import com.brh.digiturbine.dagger.DaggerDTComponent

class DTApp : Application() {

    override fun onCreate() {
        super.onCreate()

        component = DaggerDTComponent.create()
    }

    companion object {
        lateinit var component: DTComponent
            private set
    }
}