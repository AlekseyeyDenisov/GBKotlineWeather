package ru.dw.gbkotlinweather.data.api_yandex

import com.google.gson.annotations.SerializedName

data class WeatherDTO(

	@field:SerializedName("now_dt")
	val nowDt: String,

	@field:SerializedName("fact")
	val fact: Fact,

	@field:SerializedName("now")
	val now: Int,

	@field:SerializedName("forecast")
	val forecast: Forecast,

	@field:SerializedName("info")
	val info: Info
)

data class PartsItem(

	@field:SerializedName("polar")
	val polar: Boolean,

	@field:SerializedName("pressure_mm")
	val pressureMm: Int,

	@field:SerializedName("icon")
	val icon: String,

	@field:SerializedName("prec_period")
	val precPeriod: Int,

	@field:SerializedName("wind_dir")
	val windDir: String,

	@field:SerializedName("temp_max")
	val tempMax: Int,

	@field:SerializedName("feels_like")
	val feelsLike: Int,

	@field:SerializedName("wind_gust")
	val windGust: Double,

	@field:SerializedName("temp_min")
	val tempMin: Int,

	@field:SerializedName("condition")
	val condition: String,

	@field:SerializedName("temp_avg")
	val tempAvg: Int,

	@field:SerializedName("pressure_pa")
	val pressurePa: Int,

	@field:SerializedName("humidity")
	val humidity: Int,

	@field:SerializedName("wind_speed")
	val windSpeed: Double,

	@field:SerializedName("daytime")
	val daytime: String,

	@field:SerializedName("part_name")
	val partName: String,

	@field:SerializedName("prec_mm")
	val precMm: Float,

	@field:SerializedName("prec_prob")
	val precProb: Int
)

data class Info(

	@field:SerializedName("lon")
	val lon: Double,

	@field:SerializedName("url")
	val url: String,

	@field:SerializedName("lat")
	val lat: Double
)

data class Fact(

	@field:SerializedName("polar")
	val polar: Boolean,

	@field:SerializedName("temp")
	val temp: Int,

	@field:SerializedName("icon")
	val icon: String,

	@field:SerializedName("pressure_mm")
	val pressureMm: Int,

	@field:SerializedName("wind_dir")
	val windDir: String,

	@field:SerializedName("feels_like")
	val feelsLike: Int,

	@field:SerializedName("wind_gust")
	val windGust: Double,

	@field:SerializedName("condition")
	val condition: String,

	@field:SerializedName("pressure_pa")
	val pressurePa: Int,

	@field:SerializedName("humidity")
	val humidity: Int,

	@field:SerializedName("season")
	val season: String,

	@field:SerializedName("wind_speed")
	val windSpeed: Double,

	@field:SerializedName("daytime")
	val daytime: String,

	@field:SerializedName("obs_time")
	val obsTime: Int
)

data class Forecast(

	@field:SerializedName("date")
	val date: String,

	@field:SerializedName("sunrise")
	val sunrise: String,

	@field:SerializedName("week")
	val week: Int,

	@field:SerializedName("moon_text")
	val moonText: String,

	@field:SerializedName("date_ts")
	val dateTs: Int,

	@field:SerializedName("sunset")
	val sunset: String,

	@field:SerializedName("parts")
	val parts: List<PartsItem?>,

	@field:SerializedName("moon_code")
	val moonCode: Int
)
