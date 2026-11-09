package pl.skolim.trainingdiary.core.presentation

import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey

// Adds the key unless it is already on top, so a double tap navigates once.
fun NavBackStack<NavKey>.push(key: NavKey) {
    if (lastOrNull() != key) add(key)
}

// Removes the top entry only if it is the key, so a racing second pop can't remove the screen below.
fun NavBackStack<NavKey>.pop(key: NavKey) {
    if (lastOrNull() == key) removeAt(lastIndex)
}
