package ru.devyumagulov.kinopoiskbyyumagulov.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.devyumagulov.kinopoiskbyyumagulov.data.Entity.Film

@Dao
interface FilmDao {
    //Запрос на всю таблицу
    @Query("SELECT * FROM cashed_films")
    fun getCashedFilms(): Flow<List<Film>>

    //Кладем списком в БД, в случае конфликта перезаписываем
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<Film>)
}