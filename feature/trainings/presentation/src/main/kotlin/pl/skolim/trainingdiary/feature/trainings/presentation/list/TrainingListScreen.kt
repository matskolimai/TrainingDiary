package pl.skolim.trainingdiary.feature.trainings.presentation.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
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
import pl.skolim.trainingdiary.core.designsystem.Sizes
import pl.skolim.trainingdiary.core.designsystem.Spacing
import pl.skolim.trainingdiary.feature.trainings.domain.Training
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
        val modifier = Modifier.fillMaxSize().padding(contentPadding)
        when {
            state.isLoading -> Unit
            state.trainings.isEmpty() -> EmptyState(modifier = modifier)
            else -> TrainingList(trainings = state.trainings, modifier = modifier)
        }
    }
}

@Composable
private fun EmptyState(modifier: Modifier = Modifier) {
    AppEmptyState(
        icon = AppIcons.FitnessCenter,
        title = stringResource(R.string.training_list_empty_title),
        body = stringResource(R.string.training_list_empty_body),
        modifier = modifier,
    )
}

@Composable
private fun TrainingList(
    trainings: List<Training>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding =
            PaddingValues(
                start = Spacing.large,
                top = Spacing.large,
                end = Spacing.large,
                bottom = Sizes.fabClearance,
            ),
        verticalArrangement = Arrangement.spacedBy(Spacing.medium),
    ) {
        item {
            Text(
                text = pluralStringResource(R.plurals.training_list_count, trainings.size, trainings.size),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary,
                // The list's own 16dp start padding plus this makes the label's 32dp.
                modifier = Modifier.padding(start = Spacing.large),
            )
        }
        items(items = trainings, key = { it.id }) { training ->
            TrainingRow(name = training.name, onOptionsClick = {})
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

@Preview
@Composable
internal fun TrainingListPreviewWithTrainings() {
    AppPreview {
        TrainingListScreen(
            state =
                TrainingListState(
                    trainings =
                        listOf(
                            Training(id = 1, name = "Push day"),
                            Training(id = 2, name = "Nogi"),
                            Training(id = 3, name = "Plecy i biceps, długa nazwa zawijana do kolejnej linii"),
                        ),
                    isLoading = false,
                ),
            onAction = {},
        )
    }
}
