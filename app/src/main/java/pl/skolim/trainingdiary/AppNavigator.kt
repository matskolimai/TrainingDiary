package pl.skolim.trainingdiary

import androidx.navigation3.runtime.NavKey
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

// Queues navigation requests for AppNavDisplay to apply. It holds no back stack: the stack is recreated with the
// Activity, while this singleton and the ViewModels calling it outlive it. The unlimited buffer keeps requests made
// while the UI is stopped until it is started again.
class AppNavigator {
    private val _requests = Channel<NavRequest>(Channel.UNLIMITED)
    val requests: Flow<NavRequest> = _requests.receiveAsFlow()

    fun push(key: NavKey) {
        _requests.trySend(NavRequest.Push(key))
    }

    fun pop(key: NavKey) {
        _requests.trySend(NavRequest.Pop(key))
    }
}

sealed interface NavRequest {
    data class Push(
        val key: NavKey,
    ) : NavRequest

    data class Pop(
        val key: NavKey,
    ) : NavRequest
}
