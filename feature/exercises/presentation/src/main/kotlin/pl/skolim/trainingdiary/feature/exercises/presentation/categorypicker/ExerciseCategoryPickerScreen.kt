package pl.skolim.trainingdiary.feature.exercises.presentation.categorypicker

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
import pl.skolim.trainingdiary.core.designsystem.AppCountBadge
import pl.skolim.trainingdiary.core.designsystem.AppListRow
import pl.skolim.trainingdiary.core.designsystem.AppPreview
import pl.skolim.trainingdiary.core.designsystem.AppScaffold
import pl.skolim.trainingdiary.core.designsystem.AppTopBarWithBack
import pl.skolim.trainingdiary.core.designsystem.Sizes
import pl.skolim.trainingdiary.core.designsystem.Spacing
import pl.skolim.trainingdiary.feature.exercises.presentation.R

@Composable
internal fun ExerciseCategoryPickerRoot(viewModel: ExerciseCategoryPickerViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    ExerciseCategoryPickerScreen(state = state, onAction = viewModel::onAction)
}

@Composable
internal fun ExerciseCategoryPickerScreen(
    state: ExerciseCategoryPickerState,
    onAction: (ExerciseCategoryPickerAction) -> Unit,
) {
    AppScaffold(
        topBar = {
            AppTopBarWithBack(
                title = stringResource(R.string.exercise_category_picker_title),
                onBackClick = { onAction(ExerciseCategoryPickerAction.OnBackClick) },
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
            items(items = state.categories, key = { it.id }) { category ->
                ExerciseCategoryRow(name = category.name, exerciseCount = category.exerciseCount)
            }
        }
    }
}

@Composable
private fun ExerciseCategoryRow(
    name: String,
    exerciseCount: Int,
    modifier: Modifier = Modifier,
) {
    AppListRow(
        trailing = { AppCountBadge(count = exerciseCount, modifier = Modifier.padding(end = Spacing.medium)) },
        modifier = modifier,
        minHeight = Sizes.listRowTallMinHeight,
    ) {
        Text(text = name, style = MaterialTheme.typography.bodyLarge)
    }
}

@Preview
@Composable
internal fun ExerciseCategoryPickerPreviewCategories() {
    AppPreview {
        ExerciseCategoryPickerScreen(
            state =
                ExerciseCategoryPickerState(
                    categories =
                        listOf(
                            ExerciseCategoryUi(id = 1, name = "Klatka piersiowa", exerciseCount = 5),
                            ExerciseCategoryUi(id = 2, name = "Plecy", exerciseCount = 5),
                            ExerciseCategoryUi(id = 3, name = "Barki", exerciseCount = 5),
                            ExerciseCategoryUi(id = 4, name = "Biceps", exerciseCount = 4),
                            ExerciseCategoryUi(id = 5, name = "Triceps", exerciseCount = 4),
                            ExerciseCategoryUi(id = 6, name = "Nogi", exerciseCount = 6),
                            ExerciseCategoryUi(id = 7, name = "Brzuch", exerciseCount = 5),
                        ),
                ),
            onAction = {},
        )
    }
}
