package dev.shipi.mylittlespiders.presentation.entry.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.shipi.mylittlespiders.domain.model.NewEntry
import dev.shipi.mylittlespiders.domain.usecase.AddEntry
import dev.shipi.mylittlespiders.domain.usecase.CheckNetworkState
import dev.shipi.mylittlespiders.lib.presentation.ViewState
import dev.shipi.mylittlespiders.presentation.entry.EntryFormViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.lang.Exception
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class AddEntryViewModel @Inject constructor(
    private val addEntry: AddEntry,
    private val checkNetworkState: CheckNetworkState
) : ViewModel() {

    private val _state = MutableStateFlow<ViewState<EntryFormViewModel>>(ViewState.Loading)
    val state = _state.asStateFlow()

    fun showEntryDetails(friendId: String?) {
        viewModelScope.launch {
            _state.update {
                val id = friendId?.toLongOrNull()
                    ?: return@update ViewState.Error(Exception("Friend id missing or invalid format!"))
                ViewState.Data(
                    EntryFormViewModel.create(id),
                    checkNetworkState()
                )
            }
        }
    }

    fun addEntry() {
        viewModelScope.launch {
            val viewState = _state.value
            if (viewState !is ViewState.Data || viewState.data.hasErrors()
            ) {
                return@launch
            }
            val data = viewState.data.state.value
            addEntry(
                data.friendId,
                NewEntry(
                    data.date.value ?: LocalDate.now(),
                    data.text.value,
                    data.respect.value
                )
            )
        }
    }
}