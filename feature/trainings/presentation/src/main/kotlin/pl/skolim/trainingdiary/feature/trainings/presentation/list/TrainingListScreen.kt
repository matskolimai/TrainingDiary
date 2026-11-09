package pl.skolim.trainingdiary.feature.trainings.presentation.list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import pl.skolim.trainingdiary.core.designsystem.AppEmptyState
import pl.skolim.trainingdiary.core.designsystem.AppFab
import pl.skolim.trainingdiary.core.designsystem.AppIcons
import pl.skolim.trainingdiary.core.designsystem.AppPreview
import pl.skolim.trainingdiary.core.designsystem.AppScaffold
import pl.skolim.trainingdiary.core.designsystem.AppTopBar
import pl.skolim.trainingdiary.feature.trainings.presentation.R

@Composable
internal fun TrainingListRoot(
    onAddTraining: () -> Unit,
    viewModel: TrainingListViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    TrainingListScreen(
        state = state,
        onAction = { action ->
            when (action) {
                TrainingListAction.OnAddClick -> onAddTraining()
            }
            viewModel.onAction(action)
        },
    )
}

@Composable
internal fun TrainingListScreen(
    state: TrainingListState,
    onAction: (TrainingListAction) -> Unit,
) {
    AppScaffold(
        topBar = { AppTopBar(title = stringResource(R.string.training_list_title)) },
        fab = {
            AppFab(
                icon = AppIcons.Add,
                contentDescription = stringResource(R.string.training_list_cd_add),
                onClick = { onAction(TrainingListAction.OnAddClick) },
            )
        },
    ) { contentPadding ->
        if (!state.isLoading && state.trainings.isEmpty()) {
            AppEmptyState(
                icon = AppIcons.FitnessCenter,
                title = stringResource(R.string.training_list_empty_title),
                body = stringResource(R.string.training_list_empty_body),
                modifier = Modifier.fillMaxSize().padding(contentPadding),
            )
        }
    }
}

@Preview
@Composable
internal fun TrainingListPreviewLoading() {
    AppPreview {
        TrainingListScreen(state = TrainingListState(trainings = emptyList(), isLoading = true), onAction = {})
    }
}

@Preview
@Composable
internal fun TrainingListPreviewEmpty() {
    AppPreview {
        TrainingListScreen(state = TrainingListState(trainings = emptyList(), isLoading = false), onAction = {})
    }
}
