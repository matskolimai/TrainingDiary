package pl.skolim.trainingdiary

import androidx.navigation3.runtime.NavKey
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.receiveAsFlow
import pl.skolim.trainingdiary.core.presentation.NavResult
import kotlin.reflect.KClass

// Queues navigation requests for AppNavDisplay to apply. It holds no back stack: the stack is recreated with the
// Activity, while this singleton and the ViewModels calling it outlive it. The unlimited buffer keeps requests made
// while the UI is stopped until it is started again.
class AppNavigator {
    private val _requests = Channel<NavRequest>(Channel.UNLIMITED)
    val requests: Flow<NavRequest> = _requests.receiveAsFlow()

    // Broadcast, so every receiver sees every result and keeps its own type.
    private val _results = MutableSharedFlow<NavResult>(extraBufferCapacity = 1)
    val results: Flow<NavResult> = _results

    fun push(key: NavKey) {
        _requests.trySend(NavRequest.Push(key))
    }

    fun pop(key: NavKey) {
        _requests.trySend(NavRequest.Pop(key))
    }

    // Emits the result before queuing the pop, so the pop can never outrun it.
    fun popTo(
        keyType: KClass<out NavKey>,
        result: NavResult? = null,
    ) {
        result?.let { _results.tryEmit(it) }
        _requests.trySend(NavRequest.PopTo(keyType))
    }
}

sealed interface NavRequest {
    data class Push(
        val key: NavKey,
    ) : NavRequest

    data class Pop(
        val key: NavKey,
    ) : NavRequest

    data class PopTo(
        val keyType: KClass<out NavKey>,
    ) : NavRequest
}
