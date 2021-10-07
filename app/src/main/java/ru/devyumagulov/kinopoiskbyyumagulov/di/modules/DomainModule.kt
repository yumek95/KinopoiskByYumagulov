package ru.devyumagulov.kinopoiskbyyumagulov.di.modules

import dagger.Module
import dagger.Provides
import ru.devyumagulov.kinopoiskbyyumagulov.data.MainRepository
import ru.devyumagulov.kinopoiskbyyumagulov.data.TmdbApi
import ru.devyumagulov.kinopoiskbyyumagulov.domain.Interactor
import javax.inject.Singleton

@Module
class DomainModule {
    @Provides
    @Singleton
    fun provideInteractor(repository: MainRepository, tmdbApi: TmdbApi) = Interactor(repo = repository, retrofitService = tmdbApi)
}