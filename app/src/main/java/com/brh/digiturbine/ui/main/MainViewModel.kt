package com.brh.digiturbine.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brh.digiturbine.State
import com.brh.digiturbine.model.AdItem
import com.brh.digiturbine.repository.DTRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(val repository: DTRepository) : ViewModel() {

    val listLiveData: LiveData<State>
        get() = mutableLiveDataList

    val detailLiveData: LiveData<AdItem?>
        get() = mutableLiveDataDetail

    //hide the mutable live datas behind regular liveDatas
    private val mutableLiveDataList = MutableLiveData<State>(State.Idle)
    private val mutableLiveDataDetail = MutableLiveData<AdItem?>(null)

    fun fetchData(){
        viewModelScope.launch(Dispatchers.IO) {
            mutableLiveDataList.postValue(State.Loading)
            val response = repository.getItemList()
            mutableLiveDataList.postValue(response)
        }
    }

    fun itemSeleted(item: AdItem) {
        mutableLiveDataDetail.postValue(item)
    }
}