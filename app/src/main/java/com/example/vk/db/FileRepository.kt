package com.example.vk.db

class FileRepository(private val fileDao: FileDao) {

    //val allFiles: Flow<List<File>> = fileDao.getAllFiles()

    fun insert(file: File):Long {
        return fileDao.insert(file)
    }

    fun delete(file: File){
        fileDao.delete(file)
    }

    fun update(file: File){
        fileDao.update(file)
    }
}