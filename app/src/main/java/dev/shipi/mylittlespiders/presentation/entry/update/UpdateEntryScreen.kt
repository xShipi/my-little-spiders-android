package dev.shipi.mylittlespiders.presentation.entry.update

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import dev.shipi.mylittlespiders.presentation.entry.EntryForm

@Composable
fun UpdateEntryScreen(viewModel: UpdateEntryViewModel, onNavigateToFriend: () -> Unit) {
    val state by viewModel.state.collectAsState()
    EntryForm(
        state = state,
        title = "Edit entry",
        submit = "Ok",
        onSubmit = {
            viewModel.updateEntry()
            onNavigateToFriend()
        },
        onNavigateBack = onNavigateToFriend
    )
}

