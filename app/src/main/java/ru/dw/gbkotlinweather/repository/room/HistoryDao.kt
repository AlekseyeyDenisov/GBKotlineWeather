package ru.dw.gbkotlinweather.repository.room

import androidx.room.*

@Dao
interface HistoryDao {

//    @Query("INSERT INTO HistoryEntity () VALUES()")
//    fun nativeInsert(
//        city: String,
//        timestamp: Long, // первичный ключь city + timestamp
//        temperature: Int,
//        feelsLike: Int,
//        icon: String)


    @Insert
    fun insert(entity: HistoryEntity)

    @Delete
    fun delete(entity: HistoryEntity)

    @Update
    fun update(entity: HistoryEntity)

    @Query("SELECT * FROM history_table")
    fun getAll():List<HistoryEntity>


    @Query("SELECT * FROM history_table WHERE city=:city")
    fun getHistoryForCity(city:String):HistoryEntity

}