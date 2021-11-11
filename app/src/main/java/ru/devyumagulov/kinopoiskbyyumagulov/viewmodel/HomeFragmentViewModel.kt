package ru.devyumagulov.kinopoiskbyyumagulov.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.devyumagulov.kinopoiskbyyumagulov.App
import ru.devyumagulov.kinopoiskbyyumagulov.data.Entity.Film
import ru.devyumagulov.kinopoiskbyyumagulov.domain.Interactor
import javax.inject.Inject

class HomeFragmentViewModel : ViewModel() {

    val showProgressBar: MutableLiveData<Boolean> = MutableLiveData()

    //Инициализируем интерактор
    @Inject
    lateinit var interactor: Interactor
    val filmListLiveData: LiveData<List<Film>>

    init {
        App.instance.dagger.inject(this)
        filmListLiveData = interactor.getFilmsFromDb()
        getFilms()
    }

    fun getFilms() {
        showProgressBar.postValue(true)
        interactor.getFilmsFromApi(1, object : ApiCallback {
            override fun onSuccess() {
                showProgressBar.postValue(false)
            }

            override fun onFailure() {
                showProgressBar.postValue(false)
            }
        })
    }

    interface ApiCallback {
        fun onSuccess()
        fun onFailure()
    }
}