package com.example.chapter_14_tasksapp.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.chapter_14_tasksapp.data.TaskEntity

@Database(entities = [TaskEntity::class], version = 1, exportSchema = false)

abstract class TaskDatabase : RoomDatabase() {

    abstract val taskDao: TaskDao

    // TOD может так в книге конечно было, но очень не нравится что хнарение базы
    //  лежит прямо внутри нее, сделал бы тут метод типа getDatabase/get, а сохранил где-то
    //  в другом месте/классе в синглтон
//    опять же, так было по документации
    companion object {
        private const val DATABASE_NAME = "task_database"
        private var INSTANCE: TaskDatabase? = null

        // TOD есть сомнение что ты все равно некорректно реализовать двойную синхронизацию, проверь
//        проверил, вроде всё в соответствии с документацией было
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
                context,
                TaskDatabase::class.java,
                DATABASE_NAME
            ).build()
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

//@Database(entities = [TaskEntity::class], version = 1, exportSchema = false)
//abstract class TaskDatabase : RoomDatabase() {
//    abstract val taskDao: TaskDao
//
//    companion object {
//        fun getInstance(context: Context): TaskDatabase {
//            return Room.databaseBuilder(
//                context.applicationContext,
//                TaskDatabase::class.java,
//                "tasks_database"
//            )
//                .build()
//        }
//    }
//}
