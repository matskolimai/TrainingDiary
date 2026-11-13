package pl.skolim.trainingdiary.feature.trainings.presentation.editor

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import pl.skolim.trainingdiary.core.designsystem.AppListRow
import pl.skolim.trainingdiary.core.designsystem.Sizes
import pl.skolim.trainingdiary.core.designsystem.Spacing
import pl.skolim.trainingdiary.feature.trainings.domain.TrainingExercise
import pl.skolim.trainingdiary.feature.trainings.presentation.R

@Composable
internal fun TrainingExerciseCard(
    trainingExercise: TrainingExercise,
    modifier: Modifier = Modifier,
) {
    val setCount = trainingExercise.sets.size
    AppListRow(
        modifier = modifier,
        minHeight = Sizes.listRowTallMinHeight,
    ) {
        Column(modifier = Modifier.padding(vertical = Spacing.medium)) {
            Text(text = trainingExercise.exercise.name, style = MaterialTheme.typography.bodyLarge)
            Text(
                text =
                    stringResource(
                        R.string.training_editor_exercise_summary,
                        trainingExercise.exercise.category.name,
                        pluralStringResource(R.plurals.training_editor_sets, setCount, setCount),
                    ),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}
