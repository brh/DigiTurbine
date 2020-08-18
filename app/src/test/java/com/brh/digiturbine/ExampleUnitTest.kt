package com.brh.digiturbine

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.*
import com.brh.digiturbine.model.AdItem
import com.brh.digiturbine.repository.DTRepository
import com.brh.digiturbine.ui.main.MainViewModel
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import javax.inject.Inject


//@RunWith(JUnit4::class)
class ExampleUnitTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var lifecycleOwner: LifecycleOwner
    @Inject
    lateinit var repository: DTRepository
    lateinit var component: TestDTComponent
    lateinit var lifecycle: Lifecycle
    lateinit var viewModel: MainViewModel


    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        lifecycle = LifecycleRegistry(lifecycleOwner)
        component = DaggerTestDTComponent.create()
        component.inject(this)
        viewModel = MainViewModel(repository)
    }

    @Test
    fun networkCallAndReflectionCheck() {
        runBlocking {
            val response = repository.getItemList()
            val data: List<AdItem> = (response as State.Content<*>).data as List<AdItem>
            data[0]::class.java.declaredFields.forEach {fd-> println(fd.name) }
            AdItem::class.java.declaredFields[0].name
        }
    }

    @Test
    fun liveDataCheck() {
        val countDownLatch = CountDownLatch(3)
        val list = mutableListOf<State>()
        viewModel.listLiveData.observeForever {
            list.add(it)
            countDownLatch.countDown()
        }
        viewModel.fetchData()
        countDownLatch.await(5, TimeUnit.MINUTES)
        assertEquals("Count off", 3, list.size)
        list.forEachIndexed {idx, obj->
            assertTrue("Wrong type $obj", when(idx){
                0->{obj is State.Idle}
                1->{obj is State.Loading}
                else -> {obj is State.Content<*>}
            } )
        }
    }


}