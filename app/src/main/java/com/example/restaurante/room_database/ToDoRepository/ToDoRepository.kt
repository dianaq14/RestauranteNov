package com.example.restaurante.room_database.ToDoRepository

import com.example.restaurante.room_database.ToDo
import com.example.restaurante.room_database.ToDoDAO

class ToDoRepository (private val toDoDAO: ToDoDAO) {
    suspend fun getAllTasks(): List<ToDo>{
        return toDoDAO.getAllTask()
    }
    suspend fun insertTask(toDo : ToDo): Long{
        return toDoDAO.insertTask(toDo)
    }
    suspend fun insertTasks(toDo: List<ToDo>?): List<Long>{
        return toDoDAO.insertTasks(toDo)
    }

}