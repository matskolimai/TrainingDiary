package pl.skolim.trainingdiary.feature.trainings.presentation.editor

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import pl.skolim.trainingdiary.core.designsystem.AppIcons
import pl.skolim.trainingdiary.core.designsystem.AppOutlinedButton
import pl.skolim.trainingdiary.core.designsystem.AppPreview
import pl.skolim.trainingdiary.core.designsystem.AppScaffold
import pl.skolim.trainingdiary.core.designsystem.AppTextField
import pl.skolim.trainingdiary.core.designsystem.AppTopBarWithBack
import pl.skolim.trainingdiary.core.designsystem.Spacing
import pl.skolim.trainingdiary.core.domain.Exercise
import pl.skolim.trainingdiary.core.domain.ExerciseCategory
import pl.skolim.trainingdiary.feature.trainings.domain.ExerciseSet
import pl.skolim.trainingdiary.feature.trainings.domain.TrainingExercise
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
                TrainingExerciseList(
                    exercises = state.exercises,
                    onAddExerciseClick = { onAction(TrainingEditorAction.OnAddExerciseClick) },
                    onExerciseClick = { onAction(TrainingEditorAction.OnExerciseClick(it)) },
                    modifier = Modifier.weight(1f),
                )
            } else {
                Spacer(modifier = Modifier.weight(1f))
            }
            AppButton(
                text = stringResource(R.string.training_editor_save),
                onClick = { onAction(TrainingEditorAction.OnSaveClick) },
                modifier = Modifier.fillMaxWidth(),
                enabled = state.canSave,
            )
        }
    }
}

// „Dodaj ćwiczenie” is the list's last item, so it scrolls with the cards and never covers one.
@Composable
private fun TrainingExerciseList(
    exercises: List<TrainingExercise>,
    onAddExerciseClick: () -> Unit,
    onExerciseClick: (index: Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(vertical = Spacing.extraLarge),
        verticalArrangement = Arrangement.spacedBy(Spacing.medium),
    ) {
        itemsIndexed(items = exercises) { index, trainingExercise ->
            TrainingExerciseCard(trainingExercise = trainingExercise, onClick = { onExerciseClick(index) })
        }
        item {
            AppOutlinedButton(
                text = stringResource(R.string.training_editor_add_exercise),
                icon = AppIcons.Add,
                onClick = onAddExerciseClick,
                // 16dp below the last card: the 12dp gap plus 4dp.
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .then(if (exercises.isEmpty()) Modifier else Modifier.padding(top = Spacing.extraSmall)),
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

// One, three and five Sets show the three Polish plural forms.
@Preview
@Composable
internal fun TrainingEditorPreviewAddWithExercises() {
    val backCategory = ExerciseCategory(id = 2, name = "Plecy")
    val biceps = ExerciseCategory(id = 4, name = "Biceps")

    fun trainingExercise(
        name: String,
        category: ExerciseCategory,
        setCount: Int,
    ) = TrainingExercise(
        exercise = Exercise(id = 0, name = name, category = category),
        sets = List(setCount) { ExerciseSet(reps = 12, weightGrams = 0) },
    )
    AppPreview {
        TrainingEditorScreen(
            state =
                TrainingEditorState(
                    name = "Pull day",
                    exercises =
                        listOf(
                            trainingExercise("Pullover z hantlem", backCategory, setCount = 3),
                            trainingExercise("Uginanie młotkowe", biceps, setCount = 1),
                            trainingExercise("Wiosłowanie hantlem w opadzie", backCategory, setCount = 5),
                        ),
                ),
            onAction = {},
        )
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
