package com.example.land2308githubuser

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import io.reactivex.rxjava3.core.Flowable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn


class MainActivityViewModel : ViewModel() {
    val flow = Pager(
        PagingConfig(pageSize = 30)
    ) {
        UserPagingSource()
    }.flow.cachedIn(viewModelScope)
}