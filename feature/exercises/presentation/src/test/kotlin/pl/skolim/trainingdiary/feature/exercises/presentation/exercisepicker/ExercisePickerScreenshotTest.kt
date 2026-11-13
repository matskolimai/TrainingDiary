package pl.skolim.trainingdiary.feature.exercises.presentation.exercisepicker

import org.junit.Rule
import org.junit.Test
import pl.skolim.trainingdiary.core.screenshottesting.appPaparazzi

class ExercisePickerScreenshotTest {
    @get:Rule
    val paparazzi = appPaparazzi()

    @Test
    fun exercises() = paparazzi.snapshot { ExercisePickerPreviewExercises() }
}
