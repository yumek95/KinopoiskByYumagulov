package ru.devyumagulov.kinopoiskbyyumagulov.domain

import androidx.lifecycle.LiveData
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

class Interactor (private val repo: MainRepository, private val retrofitService: TmdbApi, private val preference: PreferenceProvider) {
    //В конструктор мы будм передавать коллбэк из вьюмоделе, чтобы реагировать на то, когда фильмы будут получены
    //и страницу, котороую нужно загрузить (это для пагинации)
    fun getFilmsFromApi(page: Int, callback: HomeFragmentViewModel.ApiCallback) {
        //Метод getDefaultCategoryFromPreferences() будет нам получать при каждом запросе нужный нам список фильмов
        retrofitService.getFilms(getDefaultCategoryFromPreferences(), API.KEY, "ru-RU", page).enqueue(object : Callback<TmdbResultsDto> {
            override fun onResponse(call: Call<TmdbResultsDto>, response: Response<TmdbResultsDto>) {
                //При успехе мы вызываем метод передаем onSuccess и в этот коллбэк список фильмов
                val list = Converter.convertApiListToDtoList(response.body()?.tmdbFilms)
                //Кладем фильмы в БД
                repo.putToDb(list)
                callback.onSuccess()
            }

            override fun onFailure(call: Call<TmdbResultsDto>, t: Throwable) {
                //В случае провала вызываем другой метод коллбека
                callback.onFailure()
            }
        })
    }

    //Метод для сохранения настроек
    fun saveDefaultCategoryToPreferences(category: String) {
        preference.saveDefaultCategory(category)
    }
    //Метод для получения настроек
    fun getDefaultCategoryFromPreferences() = preference.getDefaultCategory()

    fun getFilmsFromDb(): LiveData<List<Film>> = repo.getAllFromDb()
}