package ru.devyumagulov.kinopoiskbyyumagulov.view.rv_viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.film_item.view.*
import ru.devyumagulov.kinopoiskbyyumagulov.data.ApiConstants
import ru.devyumagulov.kinopoiskbyyumagulov.data.Entity.Film

//В конструктор класс передается layout, который мы создали(film_item.xml)
class FilmViewHolder(private val itemView: View) : RecyclerView.ViewHolder(itemView) {
    //Привязываем View из layout к переменным
    private val title = itemView.title
    private val poster = itemView.poster
    private val description = itemView.description
    //Вот здесь мы находим в верстке наш прогресс бар для рейтинга
    private val ratingDonut = itemView.rating_donut

    //В этом методе кладем данные из Film в наши View
    fun bind(film: Film) {
        //Устанавливаем заголовок
        title.text = film.title
        //Устанавливаем постер
        Glide.with(itemView)
            .load(ApiConstants.IMAGES_URL + "w780" + film.poster)
            .centerCrop()
            .into(poster)
        //Устанавливаем описание
        description.text = film.description
        //Устанавливаем рэйтинг
        ratingDonut.setProgress((film.rating * 10).toInt())
    }
}