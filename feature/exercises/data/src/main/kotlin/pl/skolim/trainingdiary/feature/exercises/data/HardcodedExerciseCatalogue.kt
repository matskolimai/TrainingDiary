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
        Exercise(1, "Wyciskanie hantli na ławce płaskiej", CHEST, isTimed = false),
        Exercise(2, "Wyciskanie hantli na ławce skośnej", CHEST, isTimed = false),
        Exercise(3, "Rozpiętki z hantlami", CHEST, isTimed = false),
        Exercise(4, "Pompki", CHEST, isTimed = false),
        Exercise(5, "Pompki na poręczach (dipy)", CHEST, isTimed = false),
        Exercise(6, "Podciąganie na drążku", BACK, isTimed = false),
        Exercise(7, "Wiosłowanie hantlem w opadzie", BACK, isTimed = false),
        Exercise(8, "Wiosłowanie hantlami oburącz", BACK, isTimed = false),
        Exercise(9, "Martwy ciąg z hantlami", BACK, isTimed = false),
        Exercise(10, "Pullover z hantlem", BACK, isTimed = false),
        Exercise(11, "Wyciskanie hantli nad głowę", SHOULDERS, isTimed = false),
        Exercise(12, "Unoszenie hantli bokiem", SHOULDERS, isTimed = false),
        Exercise(13, "Unoszenie hantli przodem", SHOULDERS, isTimed = false),
        Exercise(14, "Odwrotne rozpiętki w opadzie", SHOULDERS, isTimed = false),
        Exercise(15, "Arnoldki", SHOULDERS, isTimed = false),
        Exercise(16, "Uginanie ramion z hantlami", BICEPS, isTimed = false),
        Exercise(17, "Uginanie młotkowe", BICEPS, isTimed = false),
        Exercise(18, "Uginanie skoncentrowane", BICEPS, isTimed = false),
        Exercise(19, "Podciąganie podchwytem", BICEPS, isTimed = false),
        Exercise(20, "Wyciskanie francuskie hantlem", TRICEPS, isTimed = false),
        Exercise(21, "Prostowanie ramienia w opadzie", TRICEPS, isTimed = false),
        Exercise(22, "Pompki diamentowe", TRICEPS, isTimed = false),
        Exercise(23, "Dipy na ławce", TRICEPS, isTimed = false),
        Exercise(24, "Przysiad z hantlami (goblet)", LEGS, isTimed = false),
        Exercise(25, "Wykroki z hantlami", LEGS, isTimed = false),
        Exercise(26, "Przysiad bułgarski", LEGS, isTimed = false),
        Exercise(27, "Martwy ciąg na prostych nogach", LEGS, isTimed = false),
        Exercise(28, "Wspięcia na palce", LEGS, isTimed = false),
        Exercise(29, "Hip thrust z hantlem", LEGS, isTimed = false),
        Exercise(30, "Plank", ABS, isTimed = true),
        Exercise(31, "Brzuszki", ABS, isTimed = false),
        Exercise(32, "Unoszenie nóg w zwisie", ABS, isTimed = false),
        Exercise(33, "Russian twist z hantlem", ABS, isTimed = false),
        Exercise(34, "Deska boczna", ABS, isTimed = true),
    )
