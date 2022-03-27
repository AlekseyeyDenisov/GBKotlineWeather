package ru.dw.gbkotlinweather.repository

class RepositoryImpl : Repository {
    override fun getDataServer(): Weather {
        Thread.sleep(3000L)
        return Weather()
    }

    override fun getDataLocal(): Weather {
        Thread.sleep(300L)
        return Weather()
    }
}