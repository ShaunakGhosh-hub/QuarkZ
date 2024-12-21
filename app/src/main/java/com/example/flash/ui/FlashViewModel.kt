package com.example.flash.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FlashViewModel: ViewModel()  {
    private val _uiState = MutableStateFlow(FlashUiState())
    val uiState: StateFlow<FlashUiState> = _uiState.asStateFlow()

    fun updateClickText(updatedText:String){
        _uiState.update {
            it.copy(clickStatus = updatedText)
        }
    }
    fun updateSelectedCategory(updatedCategory: Int){
        _uiState.update {
            it.copy(selectedCategory = updatedCategory)
        }
    }
}