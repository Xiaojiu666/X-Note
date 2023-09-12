package com.gx.note.usecase

import com.gx.note.entity.TodoEntity
import com.gx.note.repo.TodoRepository
import javax.inject.Inject

class TodoUseCase  @Inject constructor(
    private val todoRepository: TodoRepository
) {

    fun insertTodo(todoEntity: TodoEntity) {
        todoRepository.insertTodo(todoEntity)
    }
}