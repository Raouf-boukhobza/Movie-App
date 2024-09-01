package com.raouf.movieapp.data.repository

import coil.network.HttpException
import com.raouf.movieapp.data.local.MovieDao
import com.raouf.movieapp.data.mappers.toMovie
import com.raouf.movieapp.data.mappers.toMovieEntity
import com.raouf.movieapp.data.remote.MovieApi
import com.raouf.movieapp.domain.MovieRepository
import com.raouf.movieapp.domain.Resource
import com.raouf.movieapp.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi,
    private val movieDao: MovieDao
) : MovieRepository {

    override suspend fun getMoviesData(
        forceFetchFromApi: Boolean,
        category: String,
        page: Int
    ): Flow<Resource<List<Movie>>> {
        return flow {
            emit(Resource.IsLoading(true))
            val listMovie = movieDao.getMovieByCategory(category = category)
            val shouldLoadLocalMovie: Boolean = listMovie.isNotEmpty() && !forceFetchFromApi
            if (shouldLoadLocalMovie) {
                emit(
                    Resource.Success(
                        data = listMovie.map { movieEntity ->
                            movieEntity.toMovie(category)
                        }
                    )
                )
                emit(Resource.IsLoading(false))
                return@flow
            }

            val movieFromApi = try {
                movieApi.getMovieData(category, page)
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(message = "Error loading movies"))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error(message = "Error loading movies"))
                return@flow
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error(message = "Error loading movies"))
                return@flow
            }

            val moviesEntity = movieFromApi.results.let {
                it.map { movieDto ->
                    movieDto.toMovieEntity(category = category)
                }
            }

            movieDao.UpsertMovieList(moviesEntity)
            emit(
                Resource.Success(
                    data = moviesEntity.map {
                        it.toMovie(category)
                    }
                )
            )
            emit(Resource.IsLoading(false))
        }
    }

    override suspend fun getMovie(id: Int): Flow<Resource<Movie>> {
        return flow {
            emit(Resource.IsLoading(true))
            val movieById = movieDao.getMovieById(id)
            if (movieById != null){
                emit(
                    Resource.Success(
                        data = movieById.toMovie(category = movieById.category)
                    )
                )
                emit(Resource.IsLoading(false))
                return@flow
            }
            emit(Resource.Error(
                message = "Error ,No such movie "
            ))
            emit(Resource.IsLoading(false))
        }
    }
}