package ru.devyumagulov.kinopoiskbyyumagulov.di.modules

import dagger.Module
import dagger.Provides
import ru.devyumagulov.kinopoiskbyyumagulov.data.MainRepository
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Provides
    @Singleton
    fun provideRepository() = MainRepository()
}