package ru.devyumagulov.kinopoiskbyyumagulov.domain

import ru.devyumagulov.kinopoiskbyyumagulov.data.MainRepository

class Interactor (val repo: MainRepository) {
    fun getFilmsDB(): List<Film> = repo.filmsDataBase
}