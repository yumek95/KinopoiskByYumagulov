package ru.devyumagulov.kinopoiskbyyumagulov.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_favorites.*
import ru.devyumagulov.kinopoiskbyyumagulov.view.rv_adapters.FilmListRecyclerAdapter
import ru.devyumagulov.kinopoiskbyyumagulov.MainActivity
import ru.devyumagulov.kinopoiskbyyumagulov.R
import ru.devyumagulov.kinopoiskbyyumagulov.view.rv_adapters.TopSpacingItemDecoration
import ru.devyumagulov.kinopoiskbyyumagulov.domain.Film
import ru.devyumagulov.kinopoiskbyyumagulov.utils.AnimationHelper

class FavoritesFragment : Fragment() {

    private lateinit var filmsAdapter: FilmListRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Получаем список при транзакции фрагмента
        val favoritesList: List<Film> = emptyList()

        //Запускаем анимацию
        AnimationHelper.performFragmentCircularRevealAnimation(favorites_fragment_root, requireActivity(), 2)

        favorites_recycler
            .apply {
                filmsAdapter = FilmListRecyclerAdapter(object : FilmListRecyclerAdapter.OnItemClickListener {
                    override fun click(film: Film) {
                        (requireActivity() as MainActivity).launchDetailsFragment(film)
                    }
                })
                //Присваиваем адаптер
                adapter = filmsAdapter
                //Присваимаем layoutmanager
                layoutManager = LinearLayoutManager(requireContext())
                //Применяем декоратор для отступов
                val decorator = TopSpacingItemDecoration(8)
                addItemDecoration(decorator)
            }
        //Кладем нашу БД в RV
        filmsAdapter.addItems(favoritesList)

    }
}