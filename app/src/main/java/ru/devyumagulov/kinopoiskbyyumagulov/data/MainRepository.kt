package ru.devyumagulov.kinopoiskbyyumagulov.data

import kotlinx.coroutines.flow.Flow
import ru.devyumagulov.kinopoiskbyyumagulov.data.Entity.Film
import ru.devyumagulov.kinopoiskbyyumagulov.data.dao.FilmDao
import java.util.concurrent.Executors

class MainRepository(private val filmDao: FilmDao) {

    fun putToDb(films: List<Film>) {
        filmDao.insertAll(films)
    }

    fun getAllFromDb(): Flow<List<Film>> = filmDao.getCashedFilms()
}