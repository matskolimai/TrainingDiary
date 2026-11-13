package pl.skolim.trainingdiary

import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import kotlin.reflect.KClass

// Adds the key unless it is already on top, so a double tap navigates once.
fun NavBackStack<NavKey>.push(key: NavKey) {
    if (lastOrNull() != key) add(key)
}

// Removes the top entry only if it is the key, so a racing second pop can't remove the screen below.
fun NavBackStack<NavKey>.pop(key: NavKey) {
    if (lastOrNull() == key) removeAt(lastIndex)
}

// Removes every entry above the topmost entry of the key type; does nothing if that type is not on the stack.
fun NavBackStack<NavKey>.popTo(keyType: KClass<out NavKey>) {
    val target = indexOfLast { keyType.isInstance(it) }
    if (target >= 0) repeat(lastIndex - target) { removeAt(lastIndex) }
}
