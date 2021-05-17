package com.setiaki.moviecatalogue

import com.setiaki.moviecatalogue.data.local.entity.*
import com.setiaki.moviecatalogue.data.remote.response.*

object DummyData {

    fun getTopRatedMoviesEntity(): List<MovieEntity> {
        return listOf(
            MovieEntity(
                122,
                "Spirited Away",
                "/39wmItIWsg5sZMyRUHLkWBcuVCM.jpg",
                "overview",
                "2003-12-01",
                8.5.toFloat(),
                false
            ),
            MovieEntity(
                238,
                "The Godfather",
                "/3bhkrj58Vtu7enYsRolD1fZdja1.jpg",
                "overview",
                "1972-03-14",
                8.7.toFloat(),
                false
            )
        )
    }

    fun getTopRatedTvShowsEntity(): List<TvShowEntity> {
        return listOf(
            TvShowEntity(
                100,
                "I Am Not an Animal",
                "/qG59J1Q7rpBc1dvku4azbzcqo8h.jpg",
                "overview",
                "2004-05-10",
                9.4.toFloat(),
                false
            ),
            TvShowEntity(
                83880,
                "Our Planet",
                "/wRSnArnQBmeUYb5GWDU595bGsBr.jpg",
                "overview",
                "2019-04-05",
                8.9.toFloat(),
                false
            )
        )
    }

    fun getMovieResponse(): MovieResponse {
        return MovieResponse(
            122,
            "Spirited Away",
            "/39wmItIWsg5sZMyRUHLkWBcuVCM.jpg",
            "overview",
            listOf<GenreResponse>(
                GenreResponse(16, ""),
                GenreResponse(10751, ""),
                GenreResponse(14, "")
            ),
            "2003-12-01",
            8.5
        )
    }

    fun getMovieWithGenre(): MovieWithGenre {
        return MovieWithGenre(
            movie = MovieEntity(
                122,
                "Spirited Away",
                "/39wmItIWsg5sZMyRUHLkWBcuVCM.jpg",
                "overview",
                "2003-12-01",
                8.5.toFloat(),
                false
            ),
            genres = listOf<GenreEntity>(
                GenreEntity(16, "Animation"),
                GenreEntity(10751, "Fantasy"),
                GenreEntity(14, "Family"),
            )
        )
    }

    fun getTvShowWithGenre(): TvShowWithGenre {
        return TvShowWithGenre(
            tvShow = TvShowEntity(
                100,
                "I Am Not an Animal",
                "/qG59J1Q7rpBc1dvku4azbzcqo8h.jpg",
                "overview",
                "2004-05-10",
                9.4.toFloat(),
                false
            ),
            genres = listOf<GenreEntity>(
                GenreEntity(16, "Animation"),
                GenreEntity(35, "Comedy")
            )
        )
    }

    fun getMovieEntity(): MovieEntity {
        return MovieEntity(
            122,
            "Spirited Away",
            "/39wmItIWsg5sZMyRUHLkWBcuVCM.jpg",
            "overview",
            "2003-12-01",
            8.5.toFloat(),
            false
        )
    }

    fun getTvShowEntity(): TvShowEntity {
        return TvShowEntity(
            100,
            "I Am Not an Animal",
            "/qG59J1Q7rpBc1dvku4azbzcqo8h.jpg",
            "overview",
            "2004-05-10",
            9.4.toFloat(),
            false
        )
    }


}