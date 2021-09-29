package ru.devyumagulov.kinopoiskbyyumagulov.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.devyumagulov.kinopoiskbyyumagulov.App
import ru.devyumagulov.kinopoiskbyyumagulov.domain.Film
import ru.devyumagulov.kinopoiskbyyumagulov.domain.Interactor

class HomeFragmentViewModel : ViewModel() {
    val filmListLiveData = MutableLiveData<List<Film>>()
    private var interactor: Interactor = App.instance.interactor

    init {
        interactor.getFilmsFromApi(1, object : ApiCallback {
            override fun onSuccess(films: List<Film>) {
                filmListLiveData.postValue(films)
            }

            override fun onFailure() {
            }
        })
    }

    interface ApiCallback {
        fun onSuccess(films: List<Film>)
        fun onFailure()
    }
}