package ru.devyumagulov.kinopoiskbyyumagulov.di

import dagger.Component
import ru.devyumagulov.kinopoiskbyyumagulov.di.modules.DatabaseModule
import ru.devyumagulov.kinopoiskbyyumagulov.di.modules.DomainModule
import ru.devyumagulov.kinopoiskbyyumagulov.di.modules.RemoteModule
import ru.devyumagulov.kinopoiskbyyumagulov.viewmodel.HomeFragmentViewModel
import javax.inject.Singleton

@Singleton
@Component(
    //Внедряем все модули, нужные для этого компонента
    modules = [
        DatabaseModule::class,
        DomainModule::class,
        RemoteModule::class
    ]
)

interface AppComponent {
    //метод для того, чтобы появилась возможность внедрять зависимости в HomeFragmentViewModel
    fun inject(homeFragmentViewModel: HomeFragmentViewModel)
}