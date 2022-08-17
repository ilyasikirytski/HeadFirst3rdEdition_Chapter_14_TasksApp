package com.example.chapter_14_tasksapp.model

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.chapter_14_tasksapp.data.TaskEntity

@Dao
interface TaskDao {
    @Insert
    suspend fun insert(taskEntity: TaskEntity)

    @Update
    suspend fun update(taskEntity: TaskEntity)

    @Delete
    suspend fun delete(taskEntity: TaskEntity)

    @Query("SELECT * FROM task_table WHERE  taskId = :taskId")
    fun getById(taskId: Long): LiveData<TaskEntity>

    @Query("SELECT * FROM task_table ORDER BY taskId DESC")
    fun getAll(): LiveData<List<TaskEntity>>

}