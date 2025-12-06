package com.example.todoapplication.ui.home.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import com.example.todoapplication.data.model.TodoItem


/**
 * 可配置筛选和排序 TODO 列表
 *
 * @param todos 待筛选的 todo 列表
 * @param targetDate 当前日期，用于过滤 deadline
 * @param sortField 排序字段：状态 / 优先级 / 类型
 * @param sortOrder 排序顺序：升序 / 降序
 */

fun getDefaultSortedTodos(
    todos: List<TodoItem>,
    targetDate: LocalDate,
    filter: String,
    ascending: Boolean
): List<TodoItem> {
    val classifyOrder = mapOf("工作" to 1, "生活" to 2, "学习" to 3)
    val formatter = java.time.format.DateTimeFormatter.ISO_DATE

    val filtered = todos.filter { todo ->
        if (todo.repeatType == 1) true
        else todo.deadline?.take(10)?.takeIf { it.isNotBlank() }?.let {
            val date = java.time.LocalDate.parse(it, formatter)
            when (todo.status) {
                0 -> !date.isBefore(targetDate)
                1 -> date == targetDate
                else -> false
            }
        } ?: false
    }

    val sorted = when (filter) {
        "状态" -> filtered.sortedBy { it.status }
        "优先级" -> filtered.sortedBy { it.priority }
        "类型" -> filtered.sortedBy { classifyOrder[it.classify] ?: 99 }
        else -> filtered.sortedBy { it.status } // 默认按状态
    }

    return if (ascending) sorted else sorted.reversed()
}
