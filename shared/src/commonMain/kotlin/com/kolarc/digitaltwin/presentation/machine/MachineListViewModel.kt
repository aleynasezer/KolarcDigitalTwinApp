package com.kolarc.digitaltwin.presentation.machine

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kolarc.digitaltwin.domain.repository.MachineRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MachineListViewModel(
    private val repository: MachineRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        MachineListUiState(
            isLoading = true
        )
    )

    val uiState: StateFlow<MachineListUiState> = _uiState.asStateFlow()

    init {
        loadMachines()
    }

    fun onSearchQueryChanged(query: String) {
        _uiState.update { currentState ->
            currentState.copy(
                searchQuery = query
            )
        }
    }

    fun onTabSelected(tabIndex: Int) {
        val isValidTab = tabIndex == MachineListUiState.OVERVIEW_TAB_INDEX ||
                tabIndex == MachineListUiState.LIVE_VIEW_TAB_INDEX

        if (!isValidTab) {
            return
        }

        _uiState.update { currentState ->
            currentState.copy(
                selectedTab = tabIndex
            )
        }
    }

    fun retry() {
        loadMachines()
    }

    private fun loadMachines() {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(
                    isLoading = true,
                    errorMessage = null
                )
            }

            try {
                val machines = repository.getAllMachines()

                _uiState.update { currentState ->
                    currentState.copy(
                        machines = machines,
                        isLoading = false,
                        errorMessage = null
                    )
                }
            } catch (exception: Exception) {
                _uiState.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        errorMessage = exception.message
                            ?: DEFAULT_ERROR_MESSAGE
                    )
                }
            }
        }
    }

    private companion object {
        const val DEFAULT_ERROR_MESSAGE =
            "Makine bilgileri yüklenirken bir hata oluştu."
    }
}