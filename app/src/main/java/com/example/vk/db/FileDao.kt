package com.example.vk.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface FileDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(file: File):Long

    @Delete
    fun delete(file: File)

    @Update
    fun update(file: File)

    @Query("Select * from fileTable order by id ASC")
    fun getAllFiles(): Flow<List<File>>
}