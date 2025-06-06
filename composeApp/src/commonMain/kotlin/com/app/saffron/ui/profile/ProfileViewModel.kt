package com.app.saffron.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.saffron.data.models.User
import com.app.saffron.data.models.UserState
import com.app.saffron.data.repository.AuthRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val authRepo: AuthRepo
): ViewModel() {

    private val _profileState = MutableStateFlow(UserState())

    val profileState = _profileState.onStart {
        fetchProfile()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(2000), _profileState.value)

    private fun fetchProfile() {
        viewModelScope.launch {
            _profileState.value = _profileState.value.copy(isLoading = true)
            val user = authRepo.profileDetails()
            _profileState.value = _profileState.value.copy(user = user, isLoading = false)
        }
    }
}