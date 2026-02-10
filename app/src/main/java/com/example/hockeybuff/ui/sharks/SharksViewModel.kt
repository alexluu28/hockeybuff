package com.example.hockeybuff.ui.sharks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hockeybuff.data.HockeyRepository
import com.example.hockeybuff.model.NewsItem
import com.example.hockeybuff.model.Score
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class SharksUiState(
    val scores: List<Score> = emptyList(),
    val news: List<NewsItem> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class SharksViewModel : ViewModel() {

    private val hockeyRepository = HockeyRepository()

    private val _uiState = MutableStateFlow(SharksUiState())
    val uiState: StateFlow<SharksUiState> = _uiState.asStateFlow()

    init {
        getSharksInfo()
    }

    private fun getSharksInfo() {
        viewModelScope.launch {
            _uiState.value = SharksUiState(isLoading = true)
            try {
                _uiState.value = SharksUiState(
                    scores = hockeyRepository.getScores(),
                    news = hockeyRepository.getNews(),
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = SharksUiState(error = e.message, isLoading = false)
            }
        }
    }
}