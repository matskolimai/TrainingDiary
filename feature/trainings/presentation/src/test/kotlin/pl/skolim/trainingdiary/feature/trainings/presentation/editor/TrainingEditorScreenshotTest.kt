package pl.skolim.trainingdiary.feature.trainings.presentation.editor

import org.junit.Rule
import org.junit.Test
import pl.skolim.trainingdiary.core.screenshottesting.appPaparazzi

class TrainingEditorScreenshotTest {
    @get:Rule
    val paparazzi = appPaparazzi()

    @Test
    fun addEmpty() = paparazzi.snapshot { TrainingEditorPreviewAddEmpty() }

    @Test
    fun addFilled() = paparazzi.snapshot { TrainingEditorPreviewAddFilled() }

    @Test
    fun addWithExercises() = paparazzi.snapshot { TrainingEditorPreviewAddWithExercises() }

    @Test
    fun editLoading() = paparazzi.snapshot { TrainingEditorPreviewEditLoading() }

    @Test
    fun editLoaded() = paparazzi.snapshot { TrainingEditorPreviewEditLoaded() }
}
