package com.example.demolayout

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel : ViewModel() {
    var value = MutableLiveData<Int>(0)

    public fun increment(){
        value.postValue(value.value!! + 1)
    }
}