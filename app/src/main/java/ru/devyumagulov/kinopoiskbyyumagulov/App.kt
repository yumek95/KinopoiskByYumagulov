package ru.devyumagulov.kinopoiskbyyumagulov

import android.app.Application
import ru.devyumagulov.kinopoiskbyyumagulov.di.AppComponent
import ru.devyumagulov.kinopoiskbyyumagulov.di.DaggerAppComponent

class App : Application() {
    lateinit var dagger: AppComponent

    override fun onCreate() {
        super.onCreate()
        //Инициализируем экземпляр App, через который будем получать доступ к остальным переменным
        instance = this
        //Создаем компонент
        dagger = DaggerAppComponent.create()
    }

    companion object {
        //Здесь статически хранится ссылка на экземпляр App
        lateinit var instance: App
            //Приватный сеттер, чтобы нельзя было в эту переменную присвоить что-либо другое
            private set
    }
}