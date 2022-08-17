package com.example.chapter_14_tasksapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


// DO ну и это никак не пакет `model`, у тебя же это данные, прям база данных лежит же,
//  а по клину это data/database

// DO даже сам room говорит тебе о том, что это Entity, лучше так и назыввть - TaskEntity

// DO у тебя data class который использует только хранение данных, почему поял как var,
//  потом будут одни проблемы например в том же списке - val + copy() если нужно (по книге так было)
@Entity(tableName = "task_table")
data class TaskEntity(

    // DO может быть задача с пустым именем?
    @ColumnInfo(name = "task_name")
    val taskName: String = "Default Name",

    // DO а почему все поля через подчеркивание, а айдишник что-то вдруг решил выделиться?
    @PrimaryKey(autoGenerate = true)
    val taskId: Long = 0L,

    @ColumnInfo(name = "task_done")
    val taskDone: Boolean = false
)