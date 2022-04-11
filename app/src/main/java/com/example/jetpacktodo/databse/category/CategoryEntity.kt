package com.example.jetpacktodo.databse.category

import androidx.annotation.DrawableRes
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_category")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id:Int,
    @ColumnInfo(name = "category_name")
    var categoryName:String,
    @DrawableRes
    @ColumnInfo(name = "cat_icon")
    var catIcon:Int
)