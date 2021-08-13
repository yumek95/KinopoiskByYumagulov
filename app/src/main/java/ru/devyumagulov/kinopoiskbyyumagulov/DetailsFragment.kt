package ru.devyumagulov.kinopoiskbyyumagulov

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_details.*

class DetailsFragment : Fragment() {
    private lateinit var film : Film

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setFilmsDetails()

        //Кнопка "Избранное"
        details_fab_favorites.setOnClickListener {
            //Инициализируем объект Film
            //Непонятно, почему не работает lateinit
            val film = arguments?.get("film") as Film
            if (!film.isInFavorites) {
                details_fab_favorites.setImageResource(R.drawable.ic_round_favorites)
                film.isInFavorites = true
            } else {
                details_fab_favorites.setImageResource(R.drawable.ic_round_favorites_border)
                film.isInFavorites = false
            }
        }

        //Кнопка "Поделиться"
        details_fab.setOnClickListener {
            //Создаем интент
            val intent = Intent()
            //Указываем action с которым он запускается
            intent.action = Intent.ACTION_SEND
            //Инициализируем объект Film
            //Непонятно, почему не работает lateinit
            val film = arguments?.get("film") as Film
            //Кладем данные о нашем фильме
            intent.putExtra(
                Intent.EXTRA_TEXT,
                "Зацени этот фильм: ${film.title} \n\n ${film.description}"
            )

            //Указываем MIME тип, чтобы система знала, какое приложения предложить
            intent.type = "text/plain"
            //Запускаем наше активити
            startActivity(Intent.createChooser(intent, "Share To:"))
        }

    }

    private fun setFilmsDetails() {
        //Получаем наш фильм из переданного бандла
        val film = arguments?.get("film") as Film

        //Устанавливаем заголовок
        details_toolbar.title = film.title
        //Устанавливаем картинку
        details_poster.setImageResource(film.poster)
        //Устанавливаем описание
        details_description.text = film.description

        //Установка нужной иконки при запуске фрагмента
        details_fab_favorites.setImageResource(
            if (film.isInFavorites) R.drawable.ic_round_favorites
            else R.drawable.ic_round_favorites_border
        )
    }
}