package com.example.jetpacktodo.di

import android.content.Context
import androidx.room.Room
import com.example.jetpacktodo.databse.ToDoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideToDoDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        ToDoDatabase::class.java,
        "TODODATABASE"
    ).build()



    @Singleton
    @Provides
    fun ProvideTaskDao(database: ToDoDatabase) = database.getTaskDao()
}