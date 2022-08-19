package com.example.chapter_14_tasksapp.model

import android.content.Context

class TaskDatabaseSingleton {
    companion object {
        @Volatile
        private var INSTANCE: TaskDatabase? = null
        fun getInstance(context: Context): TaskDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = TaskDatabase.getDatabase(context).also { INSTANCE = it }
                }
                return instance
            }
        }
    }
}

// TOD немного напомню про синхронизированный singleton? у котлина object это уже
//  делает автоматически, если залезть внутрь Java кода, в который компилируется котлин, - там
//  внутри будет double synchronized lock, компилятор сам сгенерирует такой код
//  https://kotlinlang.org/docs/object-declarations.html#object-expressions
//  https://stackoverflow.com/questions/56825097/synchronized-singleton-in-kotlin

// то есть если бы был код вроде
// private val INSTANCE: TaskDatabase = Room.databaseBuilder().build()
// то никакой доп синхронизации не нужно было бы