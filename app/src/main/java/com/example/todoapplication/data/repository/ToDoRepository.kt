package com.example.todoapplication.data.repository

import com.example.todoapplication.data.api.Client
import com.example.todoapplication.data.api.model.InsertTodoRequest
import com.example.todoapplication.data.api.model.TodoRequest
import com.example.todoapplication.data.api.model.TodoResponse
import com.example.todoapplication.data.api.model.TodoResponseByOne
import com.example.todoapplication.data.api.model.UpdateTodoRequest
import com.example.todoapplication.data.model.TodoUpdate

class ToDoRepository {

    /**
     * 获取用户 To do 列表
     */
    suspend fun GetTodoList(userId: String): TodoResponse? {
        return try {
            Client.apiService.getTodoList(TodoRequest(userId))
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * 更新用户 To do 列表
     */
    suspend fun updateTodo(todoId: String,updates: TodoUpdate): TodoResponse? {
        return try {
            Client.apiService.updateTodo(UpdateTodoRequest(todoId,updates))
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * 插入用户 To do 列表
     */
    suspend fun insertTodo(
        todo: InsertTodoRequest
    ): TodoResponseByOne? {
        return try {
            Client.apiService.insertTodo(
                InsertTodoRequest(
                    todo.userId,
                    todo.title,
                    todo.content,
                    todo.deadline,
                    todo.status,
                    todo.priority,
                    todo.repeatType,
                )
            )
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

}