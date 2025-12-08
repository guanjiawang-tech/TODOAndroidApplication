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

    /**
     * 1. 过滤条件（保留重复任务或未过期任务）
     */
    val filtered = todos.filter { todo ->
        if (todo.repeatType == 1) return@filter true

        val dateStr = todo.deadline?.take(10)?.takeIf { it.isNotBlank() } ?: return@filter false
        val date = LocalDate.parse(dateStr, formatter)

        // 未完成显示未来任务；完成只显示当天
        when (todo.status) {
            1 -> date == targetDate   // 已完成显示当天的
            0 -> !date.isBefore(targetDate) // 未完成显示未来及当天
            else -> false
        }
    }

    /**
     * 2. 排序字段优先级定义（完成 → 上）
     *    status: 1 → 0 (完成排前面)
     */
    val comparator = when (filter) {

        "状态" -> compareBy<TodoItem>(
            { 1 - it.status },                     // 🔥 已完成排上面
            { it.priority },
            { classifyOrder[it.classify] ?: 99 }
        )

        "优先级" -> compareBy(
            { it.priority },
            { 1 - it.status },                     // 次排序时也遵循完成优先
            { classifyOrder[it.classify] ?: 99 }
        )

        "类型" -> compareBy(
            { classifyOrder[it.classify] ?: 99 },
            { 1 - it.status },
            { it.priority }
        )

        else -> compareBy(
            { 1 - it.status },
            { it.priority },
            { classifyOrder[it.classify] ?: 99 }
        )
    }

    return if (ascending) filtered.sortedWith(comparator)
    else filtered.sortedWith(comparator.reversed())
}