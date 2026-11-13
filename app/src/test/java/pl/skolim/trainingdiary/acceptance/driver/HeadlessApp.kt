package pl.skolim.trainingdiary.acceptance.driver

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStore
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runCurrent
import org.koin.core.Koin
import org.koin.core.parameter.parametersOf
import pl.skolim.trainingdiary.AppNavigator
import pl.skolim.trainingdiary.NavRequest
import pl.skolim.trainingdiary.feature.exercises.presentation.ExerciseCategoryPickerKey
import pl.skolim.trainingdiary.feature.exercises.presentation.ExercisePickerKey
import pl.skolim.trainingdiary.feature.exercises.presentation.categorypicker.ExerciseCategoryPickerViewModel
import pl.skolim.trainingdiary.feature.exercises.presentation.exercisepicker.ExercisePickerViewModel
import pl.skolim.trainingdiary.feature.trainings.presentation.TrainingEditorKey
import pl.skolim.trainingdiary.feature.trainings.presentation.TrainingListKey
import pl.skolim.trainingdiary.feature.trainings.presentation.editor.TrainingEditorViewModel
import pl.skolim.trainingdiary.feature.trainings.presentation.list.TrainingListEvent
import pl.skolim.trainingdiary.feature.trainings.presentation.list.TrainingListViewModel
import pl.skolim.trainingdiary.pop
import pl.skolim.trainingdiary.popTo
import pl.skolim.trainingdiary.push
import kotlin.time.Duration

/** Replaces the Activity and AppNavDisplay; navigation, ViewModels and everything below them are the app's own. */
@OptIn(ExperimentalCoroutinesApi::class)
class HeadlessApp(
    private val koin: Koin,
    private val scope: TestScope,
) : AutoCloseable {
    private val backStack = NavBackStack<NavKey>(TrainingListKey)
    private val entries = mutableListOf(createEntry(TrainingListKey))
    private var collection: Job? = null
    private var isInForeground = true

    var lastTrainingListEvent: TrainingListEvent? = null
        private set

    val topKey: NavKey get() = backStack.last()

    val trainingList: TrainingListViewModel get() = entries.last().viewModel as TrainingListViewModel
    val trainingEditor: TrainingEditorViewModel get() = entries.last().viewModel as TrainingEditorViewModel
    val exerciseCategoryPicker: ExerciseCategoryPickerViewModel
        get() = entries.last().viewModel as ExerciseCategoryPickerViewModel
    val exercisePicker: ExercisePickerViewModel get() = entries.last().viewModel as ExercisePickerViewModel

    private val navigation =
        scope.backgroundScope.launch {
            collectTop()
            koin.get<AppNavigator>().requests.collect { request ->
                val previousSize = backStack.size
                when (request) {
                    is NavRequest.Push -> {
                        backStack.push(request.key)
                        if (backStack.size > previousSize) entries.add(createEntry(request.key))
                    }

                    is NavRequest.Pop -> {
                        backStack.pop(request.key)
                    }

                    is NavRequest.PopTo -> {
                        backStack.popTo(request.keyType)
                    }
                }
                repeat(previousSize - backStack.size) { entries.removeLast().store.clear() }
                if (backStack.size != previousSize) collectTop()
            }
        }

    fun settle() = scope.runCurrent()

    fun leaveFor(duration: Duration) {
        isInForeground = false
        collection?.cancel()
        collection = null
        settle()
        scope.advanceTimeBy(duration)
        isInForeground = true
        scope.backgroundScope.launch { collectTop() }
        settle()
    }

    private fun createEntry(key: NavKey): Entry =
        Entry(
            when (key) {
                TrainingListKey -> koin.get<TrainingListViewModel>()
                is TrainingEditorKey -> koin.get<TrainingEditorViewModel> { parametersOf(key) }
                ExerciseCategoryPickerKey -> koin.get<ExerciseCategoryPickerViewModel>()
                is ExercisePickerKey -> koin.get<ExercisePickerViewModel> { parametersOf(key) }
                else -> error("No ViewModel registered for $key")
            },
        )

    private suspend fun collectTop() {
        collection?.cancelAndJoin()
        if (!isInForeground) return
        collection =
            scope.backgroundScope.launch {
                when (val viewModel = entries.last().viewModel) {
                    is TrainingListViewModel -> {
                        launch { viewModel.state.collect {} }
                        launch { viewModel.events.collect { lastTrainingListEvent = it } }
                    }

                    is TrainingEditorViewModel -> {
                        viewModel.state.collect {}
                    }

                    is ExerciseCategoryPickerViewModel -> {
                        viewModel.state.collect {}
                    }

                    is ExercisePickerViewModel -> {
                        viewModel.state.collect {}
                    }
                }
            }
    }

    override fun close() {
        navigation.cancel()
        collection?.cancel()
        entries.forEach { it.store.clear() }
        settle()
    }

    private class Entry(
        val viewModel: ViewModel,
    ) {
        val store = ViewModelStore().apply { put("screen", viewModel) }
    }
}
