package com.brh.digiturbine

import com.brh.digiturbine.dagger.DTComponent
import com.brh.digiturbine.dagger.DTModule
import dagger.Component
import dagger.Module
import javax.inject.Singleton

@Singleton
@Component(modules = [TestDTModule::class])
interface TestDTComponent : DTComponent {
    fun inject(vm: ExampleUnitTest)
}

@Module
class TestDTModule: DTModule() {

}
