package ru.devyumagulov.kinopoiskbyyumagulov.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.devyumagulov.kinopoiskbyyumagulov.data.MainRepository
import ru.devyumagulov.kinopoiskbyyumagulov.data.db.DatabaseHelper
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabaseHelper(context: Context) = DatabaseHelper(context)

    @Provides
    @Singleton
    fun provideRepository(databaseHelper: DatabaseHelper) = MainRepository(databaseHelper)
}