package com.example.chapter_14_tasksapp.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// TODO лучше форматировать через строчку, читать намного проще
@Database(
    entities = [Task::class],
    version = 1,
    exportSchema = false
)
abstract class TaskDatabase : RoomDatabase() {

    abstract val taskDao: TaskDao

    // TODO может так в книге конечно было, но очень не нравится что хнарение базы
    //  лежит прямо внутри нее, сделал бы тут метод типа getDatabase/get, а сохранил где-то
    //  в другом месте/классе в синглтон
    companion object {
        private var INSTANCE: TaskDatabase? = null

        // TODO есть сомнение что ты все равно некорректно реализовать двойную синхронизацию, проверь
        fun getInstance(context: Context): TaskDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = getDatabase(context).also { INSTANCE = it }
                }
                return instance
            }
        }

        private fun getDatabase(context: Context): TaskDatabase =
            Room.databaseBuilder(
                // TODO лучше пробрасывать правильный контекст уже снаружи, зачем ей тут знать
                //  что есть applicationContext и другие разные
                context.applicationContext,
                TaskDatabase::class.java,
                "task_database"
                // TODO не совсем понятно что это за строка, либо вынести магическую
                //  константу в отдельную переменную либо использовать именнованные аргументы
            ).build()

    }

    // TODO немного напомню про синхронизированный singleton? у котлина object это уже
    //  делает автоматически, если залезть внутрь Java кода, в который компилируется котлин, - там
    //  внутри будет double synchronized lock, компилятор сам сгенерирует такой код
    //  https://kotlinlang.org/docs/object-declarations.html#object-expressions
    //  https://stackoverflow.com/questions/56825097/synchronized-singleton-in-kotlin

    // то есть если бы был код вроде
    // private val INSTANCE: TaskDatabase = Room.databaseBuilder().build()
    // то никакой доп синхронизации не нужно было бы
}