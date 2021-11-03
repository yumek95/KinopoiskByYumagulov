package ru.devyumagulov.kinopoiskbyyumagulov.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.devyumagulov.kinopoiskbyyumagulov.data.Entity.Film
import ru.devyumagulov.kinopoiskbyyumagulov.data.dao.FilmDao

@Database(entities = [Film::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun filmDao(): FilmDao
}