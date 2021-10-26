package ru.devyumagulov.kinopoiskbyyumagulov.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.devyumagulov.kinopoiskbyyumagulov.App
import ru.devyumagulov.kinopoiskbyyumagulov.domain.Film
import ru.devyumagulov.kinopoiskbyyumagulov.domain.Interactor
import javax.inject.Inject

class HomeFragmentViewModel : ViewModel() {
    val filmListLiveData = MutableLiveData<List<Film>>()

    //Инициализируем интерактор
    @Inject
    lateinit var interactor: Interactor

    init {
        App.instance.dagger.inject(this)
        getFilms()
    }

    fun getFilms() {
        interactor.getFilmsFromApi(1, object : ApiCallback {
            override fun onSuccess(films: List<Film>) {
                filmListLiveData.postValue(films)
            }

            override fun onFailure() {
                filmListLiveData.postValue(interactor.getFilmsFromDb())
            }
        })
    }

    interface ApiCallback {
        fun onSuccess(films: List<Film>)
        fun onFailure()
    }
}