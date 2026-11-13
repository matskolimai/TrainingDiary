package pl.skolim.trainingdiary.feature.trainings.presentation.seteditor

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import pl.skolim.trainingdiary.core.designsystem.AppListRow
import pl.skolim.trainingdiary.core.designsystem.AppNumberCircle
import pl.skolim.trainingdiary.core.designsystem.AppPreview
import pl.skolim.trainingdiary.core.designsystem.AppScaffold
import pl.skolim.trainingdiary.core.designsystem.AppTopBarWithBack
import pl.skolim.trainingdiary.core.designsystem.AppValuePill
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
    AppScaffold(
        topBar = {
            AppTopBarWithBack(
                title = stringResource(R.string.set_editor_title),
                onBackClick = { onAction(SetEditorAction.OnBackClick) },
            )
        },
    ) { contentPadding ->
        Column(modifier = Modifier.fillMaxSize().padding(contentPadding)) {
            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding =
                    PaddingValues(
                        start = Spacing.large,
                        top = Spacing.small,
                        end = Spacing.large,
                        bottom = Spacing.extraLarge,
                    ),
                verticalArrangement = Arrangement.spacedBy(Spacing.medium),
            ) {
                itemsIndexed(items = state.sets) { index, set ->
                    SetCard(number = index + 1, set = set)
                }
            }
            AppButton(
                text = stringResource(R.string.set_editor_add_to_training),
                onClick = { onAction(SetEditorAction.OnAddToTrainingClick) },
                modifier = Modifier.fillMaxWidth().padding(Spacing.large),
            )
        }
    }
}

@Composable
private fun SetCard(
    number: Int,
    set: ExerciseSet,
    modifier: Modifier = Modifier,
) {
    AppListRow(
        modifier = modifier,
        leading = { AppNumberCircle(number = number) },
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(Spacing.small)) {
            AppValuePill(
                text = stringResource(R.string.set_editor_weight, formatWeight(set.weightGrams)),
                focused = false,
                onClick = {},
            )
            AppValuePill(
                text = stringResource(R.string.set_editor_reps, set.reps),
                focused = false,
                onClick = {},
            )
        }
    }
}

// Whole kilograms only for now: weight can't change yet, so it is always 0 kg.
private fun formatWeight(weightGrams: Int): String = (weightGrams / GRAMS_PER_KG).toString()

private const val GRAMS_PER_KG = 1_000

@Preview
@Composable
internal fun SetEditorPreviewNew() {
    AppPreview {
        SetEditorScreen(state = SetEditorState(), onAction = {})
    }
}
