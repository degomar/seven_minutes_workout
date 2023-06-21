package com.example.sevenminutesworkout

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update

@Dao
interface HistoryDAO {

    @Insert
    suspend fun insert(historyEntity: HistoryEntity)

    @Delete
    suspend fun delete(historyEntity: HistoryEntity)

    @Update
    suspend fun update(historyEntity: HistoryEntity)
}