package pl.skolim.trainingdiary.feature.exercises.data

import pl.skolim.trainingdiary.core.domain.Exercise
import pl.skolim.trainingdiary.core.domain.ExerciseCatalogue
import pl.skolim.trainingdiary.core.domain.ExerciseCategory

// Ids are stored in Trainings, so they never change; list order is display order.
internal class HardcodedExerciseCatalogue : ExerciseCatalogue {
    override fun categories(): List<ExerciseCategory> = CATEGORIES

    override fun exercises(categoryId: Long): List<Exercise> = EXERCISES.filter { it.category.id == categoryId }

    override fun exercise(id: Long): Exercise = EXERCISES.single { it.id == id }
}

private val CHEST = ExerciseCategory(1, "Klatka piersiowa")
private val BACK = ExerciseCategory(2, "Plecy")
private val SHOULDERS = ExerciseCategory(3, "Barki")
private val BICEPS = ExerciseCategory(4, "Biceps")
private val TRICEPS = ExerciseCategory(5, "Triceps")
private val LEGS = ExerciseCategory(6, "Nogi")
private val ABS = ExerciseCategory(7, "Brzuch")

private val CATEGORIES = listOf(CHEST, BACK, SHOULDERS, BICEPS, TRICEPS, LEGS, ABS)

private val EXERCISES =
    listOf(
        Exercise(1, "Wyciskanie hantli na ławce płaskiej", CHEST),
        Exercise(2, "Wyciskanie hantli na ławce skośnej", CHEST),
        Exercise(3, "Rozpiętki z hantlami", CHEST),
        Exercise(4, "Pompki", CHEST),
        Exercise(5, "Pompki na poręczach (dipy)", CHEST),
        Exercise(6, "Podciąganie na drążku", BACK),
        Exercise(7, "Wiosłowanie hantlem w opadzie", BACK),
        Exercise(8, "Wiosłowanie hantlami oburącz", BACK),
        Exercise(9, "Martwy ciąg z hantlami", BACK),
        Exercise(10, "Pullover z hantlem", BACK),
        Exercise(11, "Wyciskanie hantli nad głowę", SHOULDERS),
        Exercise(12, "Unoszenie hantli bokiem", SHOULDERS),
        Exercise(13, "Unoszenie hantli przodem", SHOULDERS),
        Exercise(14, "Odwrotne rozpiętki w opadzie", SHOULDERS),
        Exercise(15, "Arnoldki", SHOULDERS),
        Exercise(16, "Uginanie ramion z hantlami", BICEPS),
        Exercise(17, "Uginanie młotkowe", BICEPS),
        Exercise(18, "Uginanie skoncentrowane", BICEPS),
        Exercise(19, "Podciąganie podchwytem", BICEPS),
        Exercise(20, "Wyciskanie francuskie hantlem", TRICEPS),
        Exercise(21, "Prostowanie ramienia w opadzie", TRICEPS),
        Exercise(22, "Pompki diamentowe", TRICEPS),
        Exercise(23, "Dipy na ławce", TRICEPS),
        Exercise(24, "Przysiad z hantlami (goblet)", LEGS),
        Exercise(25, "Wykroki z hantlami", LEGS),
        Exercise(26, "Przysiad bułgarski", LEGS),
        Exercise(27, "Martwy ciąg na prostych nogach", LEGS),
        Exercise(28, "Wspięcia na palce", LEGS),
        Exercise(29, "Hip thrust z hantlem", LEGS),
        Exercise(30, "Plank", ABS, isTimed = true),
        Exercise(31, "Brzuszki", ABS),
        Exercise(32, "Unoszenie nóg w zwisie", ABS),
        Exercise(33, "Russian twist z hantlem", ABS),
        Exercise(34, "Deska boczna", ABS, isTimed = true),
    )
