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
        val films = interactor.getFilmsDB()
        filmListLiveData.postValue(films)
    }
}