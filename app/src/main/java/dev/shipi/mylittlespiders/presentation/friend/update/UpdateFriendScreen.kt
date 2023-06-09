package dev.shipi.mylittlespiders.presentation.friend.update

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import dev.shipi.mylittlespiders.components.ViewState
import dev.shipi.mylittlespiders.presentation.friend.FriendForm
import dev.shipi.mylittlespiders.presentation.friend.FriendFormViewModel

@Composable
fun UpdateFriendScreen(
    viewModel: UpdateFriendViewModel,
    navigateToFriendDetails: (Long?) -> Unit,
    onNavigateBack: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    FriendForm(
        state = state,
        title = "Edit roommate",
        submit = "Ok",
        onSubmit = {
            viewModel.updateFriend()
            if (state is ViewState.Data) {
                navigateToFriendDetails(
                    (state as ViewState.Data<FriendFormViewModel>).data.state.value.friendId
                )
            } else {
                onNavigateBack()
            }
        },
        onNavigateBack = onNavigateBack
    )
}