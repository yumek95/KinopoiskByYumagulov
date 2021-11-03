package ru.devyumagulov.kinopoiskbyyumagulov.di.modules

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ru.devyumagulov.kinopoiskbyyumagulov.data.MainRepository
import ru.devyumagulov.kinopoiskbyyumagulov.data.dao.FilmDao
import ru.devyumagulov.kinopoiskbyyumagulov.data.db.AppDatabase
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun provideFilmDao(context: Context) =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "film_db"
        ).build().filmDao()

    @Provides
    @Singleton
    fun provideRepository(filmDao: FilmDao) = MainRepository(filmDao)
}