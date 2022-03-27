package ru.dw.gbkotlinweather.repository

class RepositoryIpl:Repository {
    override fun getDataWeatherServer(): Weather {
       return Weather()
    }

    override fun getDataLocal(): Weather {
        return Weather()
    }
}