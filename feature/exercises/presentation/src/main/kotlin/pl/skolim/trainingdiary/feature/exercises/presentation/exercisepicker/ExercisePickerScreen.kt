package pl.skolim.trainingdiary.feature.exercises.presentation.exercisepicker

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import pl.skolim.trainingdiary.core.designsystem.AppListRow
import pl.skolim.trainingdiary.core.designsystem.AppPreview
import pl.skolim.trainingdiary.core.designsystem.AppScaffold
import pl.skolim.trainingdiary.core.designsystem.AppTopBarWithBack
import pl.skolim.trainingdiary.core.designsystem.Sizes
import pl.skolim.trainingdiary.core.designsystem.Spacing
import pl.skolim.trainingdiary.core.domain.Exercise
import pl.skolim.trainingdiary.core.domain.ExerciseCategory
import pl.skolim.trainingdiary.feature.exercises.presentation.ExercisePickerKey
import pl.skolim.trainingdiary.feature.exercises.presentation.R

@Composable
internal fun ExercisePickerRoot(
    key: ExercisePickerKey,
    viewModel: ExercisePickerViewModel = koinViewModel { parametersOf(key) },
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    ExercisePickerScreen(state = state, onAction = viewModel::onAction)
}

@Composable
internal fun ExercisePickerScreen(
    state: ExercisePickerState,
    onAction: (ExercisePickerAction) -> Unit,
) {
    AppScaffold(
        topBar = {
            AppTopBarWithBack(
                title = stringResource(R.string.exercise_picker_title),
                onBackClick = { onAction(ExercisePickerAction.OnBackClick) },
            )
        },
    ) { contentPadding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(contentPadding),
            contentPadding =
                PaddingValues(
                    start = Spacing.large,
                    top = Spacing.small,
                    end = Spacing.large,
                    bottom = Spacing.extraLarge,
                ),
            verticalArrangement = Arrangement.spacedBy(Spacing.medium),
        ) {
            items(items = state.exercises, key = { it.id }) { exercise ->
                AppListRow(
                    onClick = { onAction(ExercisePickerAction.OnExerciseClick(exercise.id)) },
                    minHeight = Sizes.listRowCompactMinHeight,
                ) {
                    Text(text = exercise.name, style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }
}

@Preview
@Composable
internal fun ExercisePickerPreviewExercises() {
    val legs = ExerciseCategory(id = 6, name = "Nogi")
    AppPreview {
        ExercisePickerScreen(
            state =
                ExercisePickerState(
                    exercises =
                        listOf(
                            Exercise(id = 24, name = "Przysiad z hantlami (goblet)", category = legs),
                            Exercise(id = 25, name = "Wykroki z hantlami", category = legs),
                            Exercise(id = 26, name = "Przysiad bułgarski", category = legs),
                            Exercise(id = 27, name = "Martwy ciąg na prostych nogach", category = legs),
                            Exercise(id = 28, name = "Wspięcia na palce", category = legs),
                            Exercise(id = 29, name = "Hip thrust z hantlem", category = legs),
                        ),
                ),
            onAction = {},
        )
    }
}
