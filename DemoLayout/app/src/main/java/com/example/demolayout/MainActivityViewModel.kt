package com.example.demolayout

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel : ViewModel() {
    var value = MutableLiveData<String>("https://s3.cloud.cmctelecom.vn/tinhte2/2020/03/4936913_cute-cats-wallpapers-8____by____twalls.jpg")

    public fun increment(){
        value.value = ("${value.value!!.toInt() + 1}")
    }
}