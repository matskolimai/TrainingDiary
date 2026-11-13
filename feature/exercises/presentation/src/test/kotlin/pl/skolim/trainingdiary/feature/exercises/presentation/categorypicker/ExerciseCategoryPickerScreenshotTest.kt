package pl.skolim.trainingdiary.feature.exercises.presentation.categorypicker

import org.junit.Rule
import org.junit.Test
import pl.skolim.trainingdiary.core.screenshottesting.appPaparazzi

class ExerciseCategoryPickerScreenshotTest {
    @get:Rule
    val paparazzi = appPaparazzi()

    @Test
    fun categories() = paparazzi.snapshot { ExerciseCategoryPickerPreviewCategories() }
}
