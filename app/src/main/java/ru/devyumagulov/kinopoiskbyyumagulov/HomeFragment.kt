package ru.devyumagulov.kinopoiskbyyumagulov

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_favorites.*
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*

class HomeFragment : Fragment() {

    //Поле для нашего адаптера
    private lateinit var filmsAdapter: FilmListRecyclerAdapter

    //создаем "БД" фильмов
    val filmsDataBase = listOf(
        Film(
            "El Camino", R.drawable.el_camino, "Джесси Пинкман сбежал от " +
                    "неонацистов. Не зная, куда ему податься, он скрывается от полиции, похитителей " +
                    "и прошлого. Теперь он должен понять, как ему жить дальше.", false, 6.2f
        ),
        Film(
            "Годзилла против Конга", R.drawable.godzilla_vs_kingkong, "Конг и " +
                    "группа ученых отправляются в опасное путешествие в поисках родного дома " +
                    "гиганта. Среди них девочка Джия, единственная, кто умеет общаться с Конгом. " +
                    "Неожиданно они сталкиваются с разъяренным Годзиллой, разрушающим все на своем " +
                    "пути. Битва двух титанов, спровоцированная неведомыми силами — лишь малая часть " +
                    "тайны, спрятанной в недрах Земли.", false, 5.8f
        ),
        Film(
            "Достать ножи", R.drawable.knives_out, "На следующее утро после " +
                    "празднования 85-летия известного автора криминальных романов Харлана Тромби " +
                    "виновника торжества находят мёртвым. Налицо - явное самоубийство, но полиция " +
                    "по протоколу опрашивает всех присутствующих в особняке членов семьи, хотя, в" +
                    " этом деле больше заинтересован частный детектив Бенуа Блан. Тем же утром он " +
                    "получил конверт с наличными от неизвестного и заказ на расследование смерти " +
                    "Харлана. Не нужно быть опытным следователем, чтобы понять, что все " +
                    "приукрашивают свои отношения с почившим главой семейства, но Блану достаётся" +
                    " настоящий подарок - медсестра покойного, которая физически не выносит ложь.", true, 8.4f
        ),
        Film(
            "Любовь. Смерть. Роботы", R.drawable.love_death_robots,
            "Короткометражные фильмы на фантастические темы.", true, 8.6f
        ),
        Film(
            "Майор Гром", R.drawable.mayor_grom, "Игорь Гром — опытный " +
                    "следователь из Санкт-Петербурга, известный своим пробивным характером и " +
                    "непримиримой позицией по отношению к преступникам всех мастей. Неимоверная" +
                    " сила, аналитический склад ума и неподкупность — всё это делает майора Грома " +
                    "идеальным полицейским. Работая не покладая рук, он всегда доводит начатое до " +
                    "конца и никогда не пасует перед стоящими на пути трудностями.", false, 4.7f
        ),
        Film("Mortal Kombat", R.drawable.mk, "Фильм по культовой игре", false, 2.8f),
        Film(
            "Рик и Морти", R.drawable.rick_and_morty, "В центре сюжета - " +
                    "школьник по имени Морти и его дедушка Рик. Морти - самый обычный мальчик, " +
                    "который ничем не отличается от своих сверстников. А вот его дедуля занимается " +
                    "необычными научными исследованиями и зачастую полностью неадекватен. Он" +
                    " может в любое время дня и ночи схватить внука и отправиться вместе с ним" +
                    " в безумные приключения с помощью построенной из разного хлама летающей " +
                    "тарелки, которая способна перемещаться сквозь межпространственный тоннель." +
                    " Каждый раз эта парочка оказывается в самых неожиданных местах и " +
                    "самых нелепых ситуациях.", true, 9.5f
        )
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Запускаем анимацию
        AnimationHelper.performFragmentCircularRevealAnimation(home_fragment_root, requireActivity(), 1)

        //находим наш RV
        main_recycler.apply {
            //Инициализируем наш адаптер в конструктор передаем анонимно инициализированный интерфейс,
            filmsAdapter =
                FilmListRecyclerAdapter(object : FilmListRecyclerAdapter.OnItemClickListener {
                    override fun click(film: Film) {
                        (requireActivity() as MainActivity).launchDetailsFragment(film)

                    }
                })

            //Присваиваем адаптер
            adapter = filmsAdapter
            //Присвоим layoutManager
            layoutManager = LinearLayoutManager(requireContext())
            //Применяем декоратор для отступов
            val decorator = TopSpacingItemDecoration(8)
            addItemDecoration(decorator)
        }
        //Кладем нашу БД в RV
        filmsAdapter.addItems(filmsDataBase)

        //Теперь все поле search_view кликабельно
        search_view.setOnClickListener {
            search_view.isIconified = false
        }

        //Подключаем слушателя изменений введенного текста в поиска
        search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {

            //Этот метод отрабатывает при нажатии кнопки "поиск" на софт клавиатуре
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            //Этот метод отрабатывает на каждое изменения текста
            override fun onQueryTextChange(newText: String): Boolean {
                //Если ввод пуст то вставляем в адаптер всю БД
                if (newText.isEmpty()) {
                    filmsAdapter.addItems(filmsDataBase)
                    return true
                }
                //Фильтруем список на поиск подходящих сочетаний
                val result = filmsDataBase.filter {
                    //Чтобы все работало правильно, нужно и запрос, и имя фильма приводить к нижнему регистру
                    it.title.toLowerCase(Locale.getDefault()).contains(newText.toLowerCase(Locale.getDefault()))
                }
                //Добавляем в адаптер
                filmsAdapter.addItems(result)
                return true
            }
        })
    }
}