package pl.skolim.trainingdiary.feature.trainings.presentation.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalResources
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import pl.skolim.trainingdiary.core.designsystem.AppBottomSheet
import pl.skolim.trainingdiary.core.designsystem.AppEmptyState
import pl.skolim.trainingdiary.core.designsystem.AppFab
import pl.skolim.trainingdiary.core.designsystem.AppIcons
import pl.skolim.trainingdiary.core.designsystem.AppPreview
import pl.skolim.trainingdiary.core.designsystem.AppScaffold
import pl.skolim.trainingdiary.core.designsystem.AppSheetItem
import pl.skolim.trainingdiary.core.designsystem.AppTopBar
import pl.skolim.trainingdiary.core.designsystem.Sizes
import pl.skolim.trainingdiary.core.designsystem.Spacing
import pl.skolim.trainingdiary.core.presentation.ObserveAsEvents
import pl.skolim.trainingdiary.feature.trainings.domain.Training
import pl.skolim.trainingdiary.feature.trainings.presentation.R

@Composable
internal fun TrainingListRoot(viewModel: TrainingListViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val resources = LocalResources.current

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is TrainingListEvent.TrainingDeleted -> {
                scope.launch {
                    // showSnackbar queues, so a newer delete replaces the shown snackbar instead of waiting behind it.
                    snackbarHostState.currentSnackbarData?.dismiss()
                    val result =
                        snackbarHostState.showSnackbar(
                            message = resources.getString(R.string.training_list_deleted, event.name),
                            actionLabel = resources.getString(R.string.training_list_undo),
                        )
                    if (result == SnackbarResult.ActionPerformed) viewModel.onAction(TrainingListAction.OnUndoClick)
                }
            }
        }
    }

    TrainingListScreen(
        state = state,
        snackbarHostState = snackbarHostState,
        onAction = viewModel::onAction,
    )
}

@Composable
internal fun TrainingListScreen(
    state: TrainingListState,
    onAction: (TrainingListAction) -> Unit,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
) {
    AppScaffold(
        topBar = { AppTopBar(title = stringResource(R.string.training_list_title)) },
        snackbarHostState = snackbarHostState,
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
            else -> TrainingList(trainings = state.trainings, onAction = onAction, modifier = modifier)
        }
    }
    state.optionsSheetTrainingId?.let { trainingId ->
        OptionsSheet(trainingId = trainingId, onAction = onAction)
    }
}

@Composable
private fun OptionsSheet(
    trainingId: Long,
    onAction: (TrainingListAction) -> Unit,
) {
    AppBottomSheet(onDismiss = { onAction(TrainingListAction.OnOptionsDismiss) }) {
        AppSheetItem(
            icon = AppIcons.Edit,
            text = stringResource(R.string.training_list_edit),
            onClick = { onAction(TrainingListAction.OnEditClick(trainingId)) },
        )
        AppSheetItem(
            icon = AppIcons.Delete,
            text = stringResource(R.string.training_list_delete),
            onClick = { onAction(TrainingListAction.OnDeleteClick(trainingId)) },
        )
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
    onAction: (TrainingListAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding =
            PaddingValues(
                start = Spacing.large,
                top = Spacing.small,
                end = Spacing.large,
                bottom = Sizes.fabClearance,
            ),
        verticalArrangement = Arrangement.spacedBy(Spacing.medium),
    ) {
        items(items = trainings, key = { it.id }) { training ->
            TrainingRow(
                name = training.name,
                onOptionsClick = { onAction(TrainingListAction.OnOptionsClick(training.id)) },
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

@Preview
@Composable
internal fun TrainingListPreviewWithTrainings() {
    AppPreview {
        TrainingListScreen(
            state = TrainingListState(trainings = previewTrainings, isLoading = false),
            onAction = {},
        )
    }
}

@Preview
@Composable
internal fun TrainingListPreviewOptionsSheet() {
    AppPreview {
        TrainingListScreen(
            state = TrainingListState(trainings = previewTrainings, isLoading = false, optionsSheetTrainingId = 2),
            onAction = {},
        )
    }
}

private val previewTrainings =
    listOf(
        Training(id = 1, name = "Push day", exercises = emptyList()),
        Training(id = 2, name = "Nogi", exercises = emptyList()),
        Training(id = 3, name = "Plecy i biceps, długa nazwa zawijana do kolejnej linii", exercises = emptyList()),
    )
