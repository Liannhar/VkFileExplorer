package com.example.vk.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fileTable")
class File (
    @PrimaryKey(autoGenerate = true) val id:Long=0,
    @ColumnInfo(name = "hash") var hash :String,
)
{
}