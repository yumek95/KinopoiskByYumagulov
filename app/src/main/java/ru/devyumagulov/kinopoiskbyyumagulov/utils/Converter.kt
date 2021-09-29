package ru.devyumagulov.kinopoiskbyyumagulov.utils

import ru.devyumagulov.kinopoiskbyyumagulov.data.Entity.TmdbFilm
import ru.devyumagulov.kinopoiskbyyumagulov.domain.Film

object Converter {
    fun convertApiListToDtoList(list: List<TmdbFilm>?): List<Film> {
        val result = mutableListOf<Film>()
        list?.forEach {
            result.add(Film(
                title = it.title,
                poster = it.posterPath,
                description = it.overview,
                rating = it.voteAverage,
                isInFavorites = false
            ))
        }
        return result
    }
}