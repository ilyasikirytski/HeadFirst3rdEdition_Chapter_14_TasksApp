package com.example.chapter_14_tasksapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_table")
data class TaskEntity(

    @ColumnInfo(name = "task_name")
    val taskName: String = "Default Name",

    @PrimaryKey(autoGenerate = true)
    val taskId: Long = 0L,

    @ColumnInfo(name = "task_done")
    val taskDone: Boolean = false
)