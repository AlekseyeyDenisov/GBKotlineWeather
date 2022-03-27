package ru.dw.gbkotlinweather.repository

class RepositoryImpl:Repository {
    override fun getWeatherFromServer(): Weather {
        Thread.sleep(2000L)
        return Weather()
    }

    override fun getWeatherFromLocalStorage(): Weather {
        Thread.sleep(500L)
        return Weather()
    }
}