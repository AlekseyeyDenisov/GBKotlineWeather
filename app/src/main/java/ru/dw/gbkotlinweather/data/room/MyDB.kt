package ru.dw.gbkotlinweather.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [HistoryEntity::class], version = 1)
abstract class MyDB:RoomDatabase() {
    abstract fun historyDao():HistoryDao
}