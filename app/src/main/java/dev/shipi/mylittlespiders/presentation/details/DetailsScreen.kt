package dev.shipi.mylittlespiders.presentation.details

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import dev.shipi.mylittlespiders.domain.model.Entry
import dev.shipi.mylittlespiders.domain.model.FriendDetails
import dev.shipi.mylittlespiders.lib.presentation.ViewState
import dev.shipi.mylittlespiders.presentation.details.view.EntryRow
import java.lang.Exception
import java.util.Date

@Composable
fun DetailsScreen(viewModel: DetailsViewModel) {
    val state by viewModel.state.collectAsState()
    Details(state = state)
}

@Composable
fun Details(state: ViewState<FriendDetails>) {
    when (state) {
        is ViewState.Loading -> CircularProgressIndicator()
        is ViewState.Error -> Text(text = "${state.e.message}")
        is ViewState.Data -> Column {
            if (!state.isNetworkAvailable) {
                Text(text = "Network is unavailable!")
            }
            Column {
                Text(text = state.data.id.toString())
                Text(text = state.data.name)
                Text(text = state.data.location)
                Text(text = state.data.nightmares.toString())
                Column {
                    state.data.entries.forEach {
                        EntryRow(entry = it)
                    }
                }
            }
        }
    }
}

class DetailsStatePreviewProvider : PreviewParameterProvider<ViewState<FriendDetails>> {
    private val details = FriendDetails(
        2, "Quentin Tarantula", "Movie room", 1, listOf(
            Entry(0, Date(), "He suddenly appeared with a movie", 234),
            Entry(1, Date(), "He made a website", 21),
            Entry(
                2,
                Date(),
                "I finally had the courage to ask him how do other spiders find a partner? He said they usually meet on the web!",
                12
            ),
            Entry(3, Date(), "", 25),
        )
    )

    override val values = sequenceOf(
        ViewState.Loading,
        ViewState.Error(Exception("Preview exception")),
        ViewState.Data(details, false),
        ViewState.Data(details, true),
    )
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun DetailsPreview(@PreviewParameter(DetailsStatePreviewProvider::class) state: ViewState<FriendDetails>) {
    Details(state = state)
}