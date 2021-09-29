package ru.devyumagulov.kinopoiskbyyumagulov.view.fragments

import android.content.Intent
import android.os.Bundle
import android.renderscript.ScriptGroup
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_details.*
import ru.devyumagulov.kinopoiskbyyumagulov.R
import ru.devyumagulov.kinopoiskbyyumagulov.data.ApiConstants
import ru.devyumagulov.kinopoiskbyyumagulov.databinding.FragmentDetailsBinding
import ru.devyumagulov.kinopoiskbyyumagulov.domain.Film

class DetailsFragment : Fragment() {
    private lateinit var film : Film
    private lateinit var binding :FragmentDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFilmsDetails()

        //Кнопка "Избранное"
        binding.detailsFabFavorites.setOnClickListener {
            //Инициализируем объект Film
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
        binding.detailsFab.setOnClickListener {
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
        Glide.with(this)
            .load(ApiConstants.IMAGES_URL + "w780" + film.poster)
            .centerCrop()
            .into(binding.detailsPoster)
        //Устанавливаем описание
        details_description.text = film.description

        //Установка нужной иконки при запуске фрагмента
        details_fab_favorites.setImageResource(
            if (film.isInFavorites) R.drawable.ic_round_favorites
            else R.drawable.ic_round_favorites_border
        )
    }
}