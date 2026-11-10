package pl.skolim.trainingdiary.feature.trainings.domain

// No trim needed: trim() and isNotBlank() share Char.isWhitespace, so "not blank after trimming" is "not blank".
fun isValidTrainingName(name: String): Boolean = name.isNotBlank()
