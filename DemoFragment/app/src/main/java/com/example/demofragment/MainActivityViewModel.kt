package com.example.demofragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel : ViewModel() {
    val count = MutableLiveData(0)

    fun increment(){
        count.postValue(count.value!! + 1)
    }
}