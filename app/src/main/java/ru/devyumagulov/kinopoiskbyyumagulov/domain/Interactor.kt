package ru.devyumagulov.kinopoiskbyyumagulov.domain

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.devyumagulov.kinopoiskbyyumagulov.data.API
import ru.devyumagulov.kinopoiskbyyumagulov.data.Entity.Film
import ru.devyumagulov.kinopoiskbyyumagulov.data.Entity.TmdbResultsDto
import ru.devyumagulov.kinopoiskbyyumagulov.data.MainRepository
import ru.devyumagulov.kinopoiskbyyumagulov.data.Preferences.PreferenceProvider
import ru.devyumagulov.kinopoiskbyyumagulov.data.TmdbApi
import ru.devyumagulov.kinopoiskbyyumagulov.utils.Converter
import ru.devyumagulov.kinopoiskbyyumagulov.viewmodel.HomeFragmentViewModel

class Interactor(private val repo: MainRepository, private val retrofitService: TmdbApi, private val preference: PreferenceProvider) {
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)
    var progressBarState = Channel<Boolean>(Channel.CONFLATED)
    //В конструктор мы будем передавать коллбэк из вьюмодел, чтобы реагировать на то, когда фильмы будут получены
    //и страницу, котороую нужно загрузить (это для пагинации)
    fun getFilmsFromApi(page: Int) {
        //Показываем прогрессбар
        scope.launch {
            progressBarState.send(true)
        }
        //Метод getDefaultCategoryFromPreferences() будет получать при каждом запросе нужный нам список фильмов
        retrofitService.getFilms(getDefaultCategoryFromPreferences(), API.KEY, "ru-RU", page)
            .enqueue(object : Callback<TmdbResultsDto> {
                override fun onResponse(
                    call: Call<TmdbResultsDto>,
                    response: Response<TmdbResultsDto>
                ) {
                    val list = Converter.convertApiListToDtoList(response.body()?.tmdbFilms)
                    //Кладем фильмы в БД
                    //В случае успешного ответа кладем фильмы в БД и выключаем ProgressBar
                    scope.launch {
                        repo.putToDb(list)
                        progressBarState.send(false)
                    }
                }

                override fun onFailure(call: Call<TmdbResultsDto>, t: Throwable) {
                    //В случае провала выключаем ProgressBar
                    scope.launch {
                        progressBarState.send(false)
                    }
                }
            })
    }

    //Метод для сохранения настроек
    fun saveDefaultCategoryToPreferences(category: String) {
        preference.saveDefaultCategory(category)
    }

    //Метод для получения настроек
    fun getDefaultCategoryFromPreferences() = preference.getDefaultCategory()

    fun getFilmsFromDb(): Flow<List<Film>> = repo.getAllFromDb()
}