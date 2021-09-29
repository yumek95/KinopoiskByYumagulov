package ru.devyumagulov.kinopoiskbyyumagulov.domain

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.devyumagulov.kinopoiskbyyumagulov.data.API
import ru.devyumagulov.kinopoiskbyyumagulov.data.Entity.TmdbResultsDto
import ru.devyumagulov.kinopoiskbyyumagulov.data.MainRepository
import ru.devyumagulov.kinopoiskbyyumagulov.data.TmdbApi
import ru.devyumagulov.kinopoiskbyyumagulov.utils.Converter
import ru.devyumagulov.kinopoiskbyyumagulov.viewmodel.HomeFragmentViewModel

class Interactor (val repo: MainRepository, val retrofitService: TmdbApi) {
    //В конструктор мы будм передавать коллбэк из вьюмоделе, чтобы реагировать на то, когда фильмы будут получены
    //и страницу, котороую нужно загрузить (это для пагинации)
    fun getFilmsFromApi(page: Int, callback: HomeFragmentViewModel.ApiCallback) {
        retrofitService.getFilms(API.KEY, "ru-RU", page).enqueue(object : Callback<TmdbResultsDto> {
            override fun onResponse(call: Call<TmdbResultsDto>, response: Response<TmdbResultsDto>) {
                //При успехе мы вызываем метод передаем onSuccess и в этот коллбэк список фильмов
                callback.onSuccess(Converter.convertApiListToDtoList(response.body()?.tmdbFilms))
            }

            override fun onFailure(call: Call<TmdbResultsDto>, t: Throwable) {
                //В случае провала вызываем другой метод коллбека
                callback.onFailure()
            }
        })
    }
}