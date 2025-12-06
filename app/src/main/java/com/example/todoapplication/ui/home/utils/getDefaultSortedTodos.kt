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

    return todos.filter { todo ->
        if (todo.repeatType == 1) {
            true // 重复任务总是显示
        } else {
            val deadline = todo.deadline?.take(10)?.takeIf { it.isNotBlank() }?.let {
                LocalDate.parse(it, formatter)
            }

            when {
                todo.status == 0 -> deadline != null && !deadline.isBefore(targetDate) // 未完成，今天及以前
                todo.status == 1 -> deadline != null && deadline == targetDate // 已完成，只显示今天
                else -> false
            }
        }
    }.sortedWith(
        compareBy<TodoItem> { it.status }                // status升序
            .thenByDescending { it.priority }           // 优先级降序
            .thenBy { classifyOrder[it.classify] ?: 99 } // classify排序
    )
}