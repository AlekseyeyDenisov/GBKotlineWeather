package ru.dw.gbkotlinweather.repository

class RepositoryImpl : Repository {
    override fun getDataServer(): Weather {
        Thread.sleep(2000L)
        return Weather()
    }

    override fun getWorldWeatherFromLocalStorage(): List<Weather> {
        //Thread.sleep(300L)
        //return getWorldCities()
        return getNewRussianCities()
    }

    override fun getRussianWeatherFromLocalStorage(): List<Weather> {
        return getRussianCities()
    }
}