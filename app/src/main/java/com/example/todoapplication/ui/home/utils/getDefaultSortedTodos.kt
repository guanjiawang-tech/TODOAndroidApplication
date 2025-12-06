package com.example.todoapplication.ui.home.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import com.example.todoapplication.data.model.TodoItem

/**
 * 默认筛选和排序 TODO 列表
 * 规则：
 * 1. repeatType = 1
 * 2. deadline = 当天
 * 3. 排序：
 *    - 优先级降序
 *    - status升序
 *    - classify顺序：工作 → 生活 → 学习
 */
fun getDefaultSortedTodos(todos: List<TodoItem>, targetDate: LocalDate): List<TodoItem> {
    val formatter = DateTimeFormatter.ISO_DATE

    val classifyOrder = mapOf("工作" to 1, "生活" to 2, "学习" to 3)

    return todos
        .filter { todo ->
            todo.repeatType == 1 || todo.deadline?.take(10) == targetDate.format(formatter)
        }
        .sortedWith(
            compareByDescending<TodoItem> { it.priority } // 优先级高到低
                .thenBy { it.status }                     // status 0 到 1
                .thenBy { classifyOrder[it.classify] ?: 99 } // classify 排序
        )
}