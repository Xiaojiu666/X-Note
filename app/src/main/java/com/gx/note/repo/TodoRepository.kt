package com.gx.note.repo

import com.gx.note.dao.TodoEntityDao
import com.gx.note.database.AppDatabase
import com.gx.note.entity.TodoEntity
import javax.inject.Inject

class TodoRepository @Inject constructor(private val todoEntityDao: TodoEntityDao) {

    fun insertTodo(todoEntity: TodoEntity) {
        todoEntityDao.insert(todoEntity)
    }
}