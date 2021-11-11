package ru.devyumagulov.kinopoiskbyyumagulov.data

import androidx.lifecycle.LiveData
import ru.devyumagulov.kinopoiskbyyumagulov.data.Entity.Film
import ru.devyumagulov.kinopoiskbyyumagulov.data.dao.FilmDao
import java.util.concurrent.Executors

class MainRepository(private val filmDao: FilmDao) {

    fun putToDb(films: List<Film>) {
        //Запросы в БД должны быть в отдельном потоке
        Executors.newSingleThreadExecutor().execute {
            filmDao.insertAll(films)
        }
    }

    fun getAllFromDb(): LiveData<List<Film>> = filmDao.getCashedFilms()
}