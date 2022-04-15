package ru.dw.gbkotlinweather.repository.api_yandex

import ru.dw.gbkotlinweather.view.viewmodel.ResponseState

fun interface OnServerResponseListener {
    fun onResponse(response:ResponseState)
}