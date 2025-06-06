package com.app.saffron.ui.flower

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.saffron.data.models.FlowerListState
import com.app.saffron.data.repository.FlowerRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FlowerViewModel(
    private val flowerRepo: FlowerRepo,
): ViewModel() {

    private val _listState = MutableStateFlow(FlowerListState())

    val listState = _listState
        .onStart { fetchFlowers() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(2000), _listState.value)

    private fun fetchFlowers() {
        viewModelScope.launch {
            _listState.value = _listState.value.copy(isLoading = true)
            val list = flowerRepo.getFlowers()
            _listState.value = _listState.value.copy(flowers = list, isLoading = false)
        }
    }
}