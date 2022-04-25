package ru.dw.gbkotlinweather.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "history_table")
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Long,
    val city:String,
    val lat:Double,
    val lon:Double,
    //val timestamp:Int, // первичный ключь city + timestamp
    val temperature: Int,
    val feelsLike: Int,
    val icon: String
)
