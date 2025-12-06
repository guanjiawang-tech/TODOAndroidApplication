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
    val formatter = DateTimeFormatter.ISO_DATE

    // 先过滤重复任务或过期任务
    val filtered = todos.filter { todo ->
        if (todo.repeatType == 1) true
        else todo.deadline?.take(10)?.takeIf { it.isNotBlank() }?.let {
            val date = LocalDate.parse(it, formatter)
            when (todo.status) {
                0 -> !date.isBefore(targetDate)
                1 -> date == targetDate
                else -> false
            }
        } ?: false
    }

    val sorted = when (filter) {
        "状态" -> filtered.sortedWith(compareBy(
            { it.status },                        // 已完成的状态先/后
            { it.priority },                      // 再按优先级
            { classifyOrder[it.classify] ?: 99 } // 再按类型
        ))
        "优先级" -> filtered.sortedWith(compareBy(
            { it.priority },                      // 先按优先级
            { it.status },                        // 每个优先级中已完成放下面
            { classifyOrder[it.classify] ?: 99 } // 再按类型
        ))
        "类型" -> filtered.sortedWith(compareBy(
            { classifyOrder[it.classify] ?: 99 }, // 先按类型
            { it.status },                        // 每个类型中已完成放下面
            { it.priority }                       // 再按优先级
        ))
        else -> filtered.sortedWith(compareBy(
            { it.status },                        // 默认先未完成，已完成放下面
            { it.priority },                      // 再按优先级
            { classifyOrder[it.classify] ?: 99 } // 再按类型
        ))
    }

    return if (ascending) sorted else sorted.reversed()
}