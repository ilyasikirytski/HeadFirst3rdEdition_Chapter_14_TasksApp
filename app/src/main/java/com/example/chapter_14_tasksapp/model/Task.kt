package com.example.chapter_14_tasksapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


// TODO ну и это никак не пакет `model`, у тебя же это данные, прям база данных лежит же,
//  а по клину это data/database

// TODO даже сам room говорит тебе о том, что это Entity, лучше так и назыввть - TaskEntity

// TODO у тебя data class который использует только хранение данных, почему поял как var,
//  потом будут одни проблемы например в том же списке - val + copy() если нужно
@Entity(tableName = "task_table")
data class Task(

    // TODO а почему все поля через подчеркивание, а айдишник что-то вдруг решил выделиться?
    @PrimaryKey(autoGenerate = true)
    var taskId: Long = 0L,

    // TODO может быть задача с пустым именем?
    @ColumnInfo(name = "task_name")
    var taskName: String = "",

    @ColumnInfo(name = "task_done")
    var taskDone: Boolean = false
)