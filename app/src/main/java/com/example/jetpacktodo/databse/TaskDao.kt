package com.example.jetpacktodo.databse

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.jetpacktodo.databse.category.CategoryEntity

@Dao
interface TaskDao {

    @Query("SELECT * FROM create_task")
    suspend fun getAllTask() : List<TaskEntity>

    @Insert
    suspend fun insertTask(task:TaskEntity)

    /*@Insert
    suspend fun addCategory(category: CategoryEntity)

    @Query("SELECT * FROM task_category")
    suspend fun getCategoryWise(): LiveData<List<CategoryEntity>>

    @Query("UPDATE task_category SET task_count = :count WHERE category_name = :categoryName")
    suspend fun updateCategoryTaskCount(count:Int, categoryName: String)*/
}