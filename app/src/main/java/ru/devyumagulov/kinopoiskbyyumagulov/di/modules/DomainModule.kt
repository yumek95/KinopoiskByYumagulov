package ru.devyumagulov.kinopoiskbyyumagulov.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.devyumagulov.kinopoiskbyyumagulov.data.MainRepository
import ru.devyumagulov.kinopoiskbyyumagulov.data.Preferences.PreferenceProvider
import ru.devyumagulov.kinopoiskbyyumagulov.data.TmdbApi
import ru.devyumagulov.kinopoiskbyyumagulov.domain.Interactor
import javax.inject.Singleton

@Module
//Передаем контекст для SharedPreferences через конструктор
class DomainModule(val context: Context) {
    //Нам нужно контекст как-то провайдить, поэтому создаем такой метод
    @Provides
    fun provideContext() = context

    @Provides
    @Singleton
    //Создаем экземпляр SharedPreferences
    fun providePreferences(context: Context) = PreferenceProvider(context)

    @Provides
    @Singleton
    fun provideInteractor(repository: MainRepository, tmdbApi: TmdbApi, preferenceProvider: PreferenceProvider)
    = Interactor(repo = repository, retrofitService = tmdbApi, preference = preferenceProvider)
}