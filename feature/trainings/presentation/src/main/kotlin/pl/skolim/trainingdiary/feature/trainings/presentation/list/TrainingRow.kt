package pl.skolim.trainingdiary.feature.trainings.presentation.list

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import pl.skolim.trainingdiary.core.designsystem.AppIconButton
import pl.skolim.trainingdiary.core.designsystem.AppIcons
import pl.skolim.trainingdiary.core.designsystem.AppListRow
import pl.skolim.trainingdiary.feature.trainings.presentation.R

@Composable
internal fun TrainingRow(
    name: String,
    onOptionsClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AppListRow(
        trailing = {
            AppIconButton(
                icon = AppIcons.MoreVert,
                contentDescription = stringResource(R.string.training_list_cd_more),
                onClick = onOptionsClick,
            )
        },
        modifier = modifier,
    ) {
        Text(text = name, style = MaterialTheme.typography.bodyLarge)
    }
}
