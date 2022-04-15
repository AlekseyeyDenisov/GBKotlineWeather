package ru.dw.gbkotlinweather.repository.api_yandex

import com.google.gson.annotations.SerializedName

data class WeatherDTO(

	@field:SerializedName("now_dt")
	val nowDt: String? = null,

	@field:SerializedName("fact")
	val fact: Fact? = null,

	@field:SerializedName("now")
	val now: Int? = null,

	@field:SerializedName("forecast")
	val forecast: Forecast? = null,

	@field:SerializedName("info")
	val info: Info? = null
)

data class PartsItem(

	@field:SerializedName("polar")
	val polar: Boolean? = null,

	@field:SerializedName("pressure_mm")
	val pressureMm: Int? = null,

	@field:SerializedName("icon")
	val icon: String? = null,

	@field:SerializedName("prec_period")
	val precPeriod: Int? = null,

	@field:SerializedName("wind_dir")
	val windDir: String? = null,

	@field:SerializedName("temp_max")
	val tempMax: Int? = null,

	@field:SerializedName("feels_like")
	val feelsLike: Int? = null,

	@field:SerializedName("wind_gust")
	val windGust: Double? = null,

	@field:SerializedName("temp_min")
	val tempMin: Int? = null,

	@field:SerializedName("condition")
	val condition: String? = null,

	@field:SerializedName("temp_avg")
	val tempAvg: Int? = null,

	@field:SerializedName("pressure_pa")
	val pressurePa: Int? = null,

	@field:SerializedName("humidity")
	val humidity: Int? = null,

	@field:SerializedName("wind_speed")
	val windSpeed: Double? = null,

	@field:SerializedName("daytime")
	val daytime: String? = null,

	@field:SerializedName("part_name")
	val partName: String? = null,

	@field:SerializedName("prec_mm")
	val precMm: Float? = null,

	@field:SerializedName("prec_prob")
	val precProb: Int? = null
)

data class Info(

	@field:SerializedName("lon")
	val lon: Double? = null,

	@field:SerializedName("url")
	val url: String? = null,

	@field:SerializedName("lat")
	val lat: Double? = null
)

data class Fact(

	@field:SerializedName("polar")
	val polar: Boolean? = null,

	@field:SerializedName("temp")
	val temp: Int? = null,

	@field:SerializedName("icon")
	val icon: String? = null,

	@field:SerializedName("pressure_mm")
	val pressureMm: Int? = null,

	@field:SerializedName("wind_dir")
	val windDir: String? = null,

	@field:SerializedName("feels_like")
	val feelsLike: Int? = null,

	@field:SerializedName("wind_gust")
	val windGust: Double? = null,

	@field:SerializedName("condition")
	val condition: String? = null,

	@field:SerializedName("pressure_pa")
	val pressurePa: Int? = null,

	@field:SerializedName("humidity")
	val humidity: Int? = null,

	@field:SerializedName("season")
	val season: String? = null,

	@field:SerializedName("wind_speed")
	val windSpeed: Double? = null,

	@field:SerializedName("daytime")
	val daytime: String? = null,

	@field:SerializedName("obs_time")
	val obsTime: Int? = null
)

data class Forecast(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("sunrise")
	val sunrise: String? = null,

	@field:SerializedName("week")
	val week: Int? = null,

	@field:SerializedName("moon_text")
	val moonText: String? = null,

	@field:SerializedName("date_ts")
	val dateTs: Int? = null,

	@field:SerializedName("sunset")
	val sunset: String? = null,

	@field:SerializedName("parts")
	val parts: List<PartsItem?>? = null,

	@field:SerializedName("moon_code")
	val moonCode: Int? = null
)
