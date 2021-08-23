package ru.devyumagulov.kinopoiskbyyumagulov.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Film(
    val title: String,
    val poster: Int,
    val description: String,
    var isInFavorites: Boolean = false,
    val rating: Float = 0f
    ) : Parcelable
