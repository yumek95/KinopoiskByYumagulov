package ru.devyumagulov.kinopoiskbyyumagulov

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toolbar
import androidx.appcompat.widget.AppCompatImageView
import com.google.android.material.appbar.CollapsingToolbarLayout
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        setFilmsDetails()
    }

    private fun setFilmsDetails() {
        //Получаем наш фильм из переданного бандла
        val film = intent.extras?.get("film") as Film

//        val detailsToolbar = findViewById<Toolbar>(R.id.details_toolbar)
//        val detailsPoster = findViewById<AppCompatImageView>(R.id.details_poster)
//        val detailsDescription = findViewById<TextView>(R.id.details_description)

        //Устанавливаем заголовок
        details_toolbar.title = film.title
        //Устанавливаем картинку
        details_poster.setImageResource(film.poster)
        //Устанавливаем описание
        details_description.text = film.description
    }
}