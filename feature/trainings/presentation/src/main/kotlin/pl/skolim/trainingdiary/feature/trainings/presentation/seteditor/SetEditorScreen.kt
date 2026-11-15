package pl.skolim.trainingdiary.feature.trainings.presentation.seteditor

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import pl.skolim.trainingdiary.core.designsystem.AppButton
import pl.skolim.trainingdiary.core.designsystem.AppCountRow
import pl.skolim.trainingdiary.core.designsystem.AppPreview
import pl.skolim.trainingdiary.core.designsystem.AppScaffold
import pl.skolim.trainingdiary.core.designsystem.AppTopBarWithBack
import pl.skolim.trainingdiary.core.designsystem.Spacing
import pl.skolim.trainingdiary.feature.trainings.domain.ExerciseSet
import pl.skolim.trainingdiary.feature.trainings.presentation.R
import pl.skolim.trainingdiary.feature.trainings.presentation.SetEditorKey

@Composable
internal fun SetEditorRoot(
    key: SetEditorKey,
    viewModel: SetEditorViewModel = koinViewModel { parametersOf(key) },
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    SetEditorScreen(state = state, onAction = viewModel::onAction)
}

@Composable
internal fun SetEditorScreen(
    state: SetEditorState,
    onAction: (SetEditorAction) -> Unit,
) {
    val isEditMode = state.editedExerciseName != null
    AppScaffold(
        topBar = {
            AppTopBarWithBack(
                title = state.editedExerciseName ?: stringResource(R.string.set_editor_title),
                onBackClick = { onAction(SetEditorAction.OnBackClick) },
            )
        },
    ) { contentPadding ->
        Column(modifier = Modifier.fillMaxSize().padding(contentPadding)) {
            AppCountRow(
                label = stringResource(R.string.set_editor_sets),
                count = state.sets.size,
                onDecrement = { onAction(SetEditorAction.OnDecrementSetsClick) },
                onIncrement = { onAction(SetEditorAction.OnIncrementSetsClick) },
                decrementEnabled = state.canRemoveSet,
                incrementEnabled = state.canAddSet,
                modifier =
                    Modifier
                        .padding(horizontal = Spacing.large)
                        .padding(top = Spacing.small, bottom = Spacing.large),
            )
            SetList(state = state, onAction = onAction, modifier = Modifier.weight(1f))
            AppButton(
                text =
                    stringResource(
                        if (isEditMode) R.string.set_editor_done else R.string.set_editor_add_to_training,
                    ),
                onClick = { onAction(SetEditorAction.OnConfirmClick) },
                modifier = Modifier.fillMaxWidth().padding(Spacing.large),
            )
        }
    }
}

@Composable
private fun SetList(
    state: SetEditorState,
    onAction: (SetEditorAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(start = Spacing.large, end = Spacing.large, bottom = Spacing.extraLarge),
        verticalArrangement = Arrangement.spacedBy(Spacing.medium),
    ) {
        itemsIndexed(items = state.sets) { index, set ->
            SetCard(
                number = index + 1,
                set = set,
                focusedField = state.focusedField.takeIf { state.focusedSetIndex == index },
                onFocus = { onAction(SetEditorAction.OnValueClick(index, it)) },
                trailing =
                    if (state.focusedSetIndex == index) {
                        {
                            ValueControls(
                                canDecrement = state.canDecrementValue,
                                canIncrement = state.canIncrementValue,
                                onDecrement = { onAction(SetEditorAction.OnDecrementValueClick) },
                                onIncrement = { onAction(SetEditorAction.OnIncrementValueClick) },
                            )
                        }
                    } else {
                        null
                    },
            )
        }
    }
}

@Preview
@Composable
internal fun SetEditorPreviewNew() {
    AppPreview {
        SetEditorScreen(state = SetEditorState(), onAction = {})
    }
}

@Suppress("MagicNumber") // Explicit preview data for an edited Training exercise.
@Preview
@Composable
internal fun SetEditorPreviewEdit() {
    AppPreview {
        SetEditorScreen(
            state =
                SetEditorState(
                    sets =
                        listOf(
                            ExerciseSet(reps = 10, weightGrams = 12_000),
                            ExerciseSet(reps = 8, weightGrams = 13_500),
                        ),
                    editedExerciseName = "Wiosłowanie hantlem w opadzie",
                ),
            onAction = {},
        )
    }
}

@Preview
@Composable
internal fun SetEditorPreviewMin() {
    AppPreview {
        SetEditorScreen(
            state = SetEditorState(sets = listOf(ExerciseSet(reps = 12, weightGrams = 0))),
            onAction = {},
        )
    }
}

@Suppress("MagicNumber") // Explicit preview data at the upper count limit.
@Preview
@Composable
internal fun SetEditorPreviewMax() {
    AppPreview {
        SetEditorScreen(
            state = SetEditorState(sets = List(10) { ExerciseSet(reps = 12, weightGrams = 0) }),
            onAction = {},
        )
    }
}

@Preview
@Composable
internal fun SetEditorPreviewMinReps() {
    AppPreview {
        SetEditorScreen(
            state =
                SetEditorState(
                    sets = listOf(ExerciseSet(reps = 1, weightGrams = 0)),
                    focusedField = SetEditorField.REPS,
                ),
            onAction = {},
        )
    }
}

@Suppress("MagicNumber") // Explicit preview data at the upper reps limit.
@Preview
@Composable
internal fun SetEditorPreviewMaxReps() {
    AppPreview {
        SetEditorScreen(
            state =
                SetEditorState(
                    sets =
                        listOf(
                            ExerciseSet(reps = 12, weightGrams = 0),
                            ExerciseSet(reps = 999, weightGrams = 0),
                            ExerciseSet(reps = 12, weightGrams = 0),
                        ),
                    focusedSetIndex = 1,
                    focusedField = SetEditorField.REPS,
                ),
            onAction = {},
        )
    }
}

@Suppress("MagicNumber") // Explicit preview data at the upper weight limit.
@Preview
@Composable
internal fun SetEditorPreviewMaxWeight() {
    AppPreview {
        SetEditorScreen(
            state =
                SetEditorState(
                    sets =
                        listOf(
                            ExerciseSet(reps = 12, weightGrams = 1_500),
                            ExerciseSet(reps = 12, weightGrams = 300_000),
                            ExerciseSet(reps = 12, weightGrams = 13_500),
                        ),
                    focusedSetIndex = 1,
                ),
            onAction = {},
        )
    }
}
