package com.example.chapter_14_tasksapp.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.chapter_14_tasksapp.data.TaskEntity

@Database(entities = [TaskEntity::class], version = 1, exportSchema = false)

abstract class TaskDatabase : RoomDatabase() {

    abstract val taskDao: TaskDao

    companion object {
        private const val DATABASE_NAME = "task_database"
        fun getDatabase(context: Context): TaskDatabase =
            Room.databaseBuilder(
                context,
                TaskDatabase::class.java,
                DATABASE_NAME
            ).build()
    }
}