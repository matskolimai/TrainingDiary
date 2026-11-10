package pl.skolim.trainingdiary.feature.trainings.presentation.editor

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import pl.skolim.trainingdiary.core.designsystem.AppButton
import pl.skolim.trainingdiary.core.designsystem.AppPreview
import pl.skolim.trainingdiary.core.designsystem.AppScaffold
import pl.skolim.trainingdiary.core.designsystem.AppTextField
import pl.skolim.trainingdiary.core.designsystem.AppTopBarWithBack
import pl.skolim.trainingdiary.core.designsystem.Spacing
import pl.skolim.trainingdiary.feature.trainings.presentation.R
import pl.skolim.trainingdiary.feature.trainings.presentation.TrainingEditorKey

@Composable
internal fun TrainingEditorRoot(
    key: TrainingEditorKey,
    viewModel: TrainingEditorViewModel = koinViewModel { parametersOf(key) },
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    TrainingEditorScreen(state = state, onAction = viewModel::onAction)
}

@Composable
internal fun TrainingEditorScreen(
    state: TrainingEditorState,
    onAction: (TrainingEditorAction) -> Unit,
) {
    val titleRes = if (state.isEditMode) R.string.training_editor_title_edit else R.string.training_editor_title_add
    AppScaffold(
        topBar = {
            AppTopBarWithBack(
                title = stringResource(titleRes),
                onBackClick = { onAction(TrainingEditorAction.OnBackClick) },
            )
        },
    ) { contentPadding ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(contentPadding)
                    .consumeWindowInsets(contentPadding)
                    .imePadding()
                    .padding(Spacing.large),
        ) {
            if (!state.isLoading) {
                NameField(
                    name = state.name,
                    onNameChange = { onAction(TrainingEditorAction.OnNameChange(it)) },
                    autofocus = !state.isEditMode,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            AppButton(
                text = stringResource(R.string.training_editor_save),
                onClick = { onAction(TrainingEditorAction.OnSaveClick) },
                modifier = Modifier.fillMaxWidth(),
                enabled = state.canSave,
            )
        }
    }
}

// Focus is requested once, on entering; a SideEffect would re-request it after every recomposition.
@Suppress("UnnecessaryLaunchedEffect")
@Composable
private fun NameField(
    name: String,
    onNameChange: (String) -> Unit,
    autofocus: Boolean,
    modifier: Modifier = Modifier,
) {
    val focusRequester = remember { FocusRequester() }
    if (autofocus) {
        LaunchedEffect(Unit) { focusRequester.requestFocus() }
    }
    AppTextField(
        value = name,
        onValueChange = onNameChange,
        label = stringResource(R.string.training_editor_name_label),
        modifier = modifier.focusRequester(focusRequester),
    )
}

@Preview
@Composable
internal fun TrainingEditorPreviewAddEmpty() {
    AppPreview {
        TrainingEditorScreen(state = TrainingEditorState(name = ""), onAction = {})
    }
}

@Preview
@Composable
internal fun TrainingEditorPreviewAddFilled() {
    AppPreview {
        TrainingEditorScreen(state = TrainingEditorState(name = "Push day"), onAction = {})
    }
}

@Preview
@Composable
internal fun TrainingEditorPreviewEditLoading() {
    AppPreview {
        TrainingEditorScreen(state = TrainingEditorState(isEditMode = true, isLoading = true), onAction = {})
    }
}

@Preview
@Composable
internal fun TrainingEditorPreviewEditLoaded() {
    AppPreview {
        TrainingEditorScreen(state = TrainingEditorState(name = "Nogi", isEditMode = true), onAction = {})
    }
}
