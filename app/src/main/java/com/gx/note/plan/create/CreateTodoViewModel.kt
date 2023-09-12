package com.gx.note.plan.create

import androidx.compose.ui.input.key.Key.Companion.D
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gx.note.LoadableState
import com.gx.note.diary.editor.DiaryEditorViewModel
import com.gx.note.entity.TodoEntity
import com.gx.note.usecase.TodoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateTodoViewModel @Inject constructor(
    val todoUseCase: TodoUseCase
) : ViewModel() {

    private val _uiState =
        MutableStateFlow(TodoUiState(
            todoContent = TextFieldValue(),
            todoEntity = TodoEntity(""),
            upDateTodoContent = { todoContent ->
                viewModelScope.launch {
                    updateTodoUiState {
                        it.copy(todoContent = todoContent)
                    }
                }
            }, updateTodoEntity = { todoEntity ->
                viewModelScope.launch {
                    updateTodoUiState {
                        it.copy(todoEntity = todoEntity)
                    }
                }
            }, onSaveTodo = ::onSaveTodo
        )
        )
    val uiState = _uiState.asStateFlow()

    private fun onSaveTodo() {
        viewModelScope.launch(Dispatchers.IO) {
            val todoEntity = _uiState.value.todoEntity
            todoUseCase.insertTodo(todoEntity)
        }
    }


    private suspend fun updateTodoUiState(block: (TodoUiState) -> TodoUiState) {
        val todoUiState = _uiState.value
        _uiState.emit(
            block(todoUiState)
        )
    }


    data class TodoUiState(
        val todoContent: TextFieldValue,
        val todoEntity: TodoEntity,
        val upDateTodoContent: (TextFieldValue) -> Unit,
        val updateTodoEntity: (TodoEntity) -> Unit,
        val onSaveTodo: () -> Unit
    )
}