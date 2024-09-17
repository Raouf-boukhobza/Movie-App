package com.raouf.movieapp.data.repository

import coil.network.HttpException
import com.raouf.movieapp.data.local.MovieDao
import com.raouf.movieapp.data.mappers.toMovie
import com.raouf.movieapp.data.mappers.toMovieEntity
import com.raouf.movieapp.data.remote.MovieApi
import com.raouf.movieapp.data.remote.MovieListDto
import com.raouf.movieapp.domain.MovieRepository
import com.raouf.movieapp.domain.model.Movie
import com.raouf.movieapp.domain.util.Category
import com.raouf.movieapp.domain.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okio.IOException
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi,
    private val movieDao: MovieDao
) : MovieRepository {


    override suspend fun getMoviesList(
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
                            movieEntity.toMovie()
                        }
                    )
                )
                emit(Resource.IsLoading(false))
                return@flow
            }

            val movieFromApi = try {
                movieApi.getMovieList(category, page)
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(message = e.message ?: ""))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error(message = e.message ?: ""))
                return@flow
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error(message = e.message ?: ""))
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
                        it.toMovie()
                    }
                )
            )
            emit(Resource.IsLoading(false))
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getMovie(id: Int): Flow<Resource<Movie>> {
        return flow<Resource<Movie>> {
            emit(Resource.IsLoading(true))
            var movieById = try {
                movieApi.getMovieByIdFromRemote(movieId = id)
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(message = e.message ?: ""))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error(message = e.message ?: ""))
                return@flow
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error(message = e.message ?: ""))
                return@flow
            }


            movieById = movieById.copy(
                videoUrl = try {
                    movieApi.getVideo(id)
                        .results.filter {movie ->
                            movie.type == "Trailer"
                        }.map {movie ->
                            movie.key
                        }[0]
                } catch (e: IOException) {
                    e.printStackTrace()
                    emit(Resource.Error(message = e.message ?: ""))
                    ""
                } catch (e: HttpException) {
                    e.printStackTrace()
                    emit(Resource.Error(message = e.message ?: ""))
                   ""
                } catch (e: Exception) {
                    e.printStackTrace()
                    emit(Resource.Error(message = e.message ?: ""))
                   ""
                }
            )
            emit(
                Resource.Success(
                    data = movieById.toMovieEntity(
                        category = null
                    ).toMovie()
                )
            )
            emit(Resource.IsLoading(false))
        }.flowOn(Dispatchers.IO)

    }


    override suspend fun getTrendingMovie(
        category: String
    ): Flow<Resource<List<Movie>>> {
        return flow {
            emit(Resource.IsLoading(isLoading = true))
            val trendingMoviesCatch = movieDao.getMovieByCategory(category)
            if (trendingMoviesCatch.isNotEmpty()) {
                emit(
                    Resource.Success(
                        movieDao.getMovieByCategory(category).let {
                            it.map { movieEntity ->
                                movieEntity.toMovie()
                            }
                        }
                    ))
            }
            val trendingMovies = try {
                movieApi.getTrendingMovies()
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(message = e.message ?: ""))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error(message = e.message ?: ""))
                return@flow
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error(message = e.message ?: ""))
                return@flow
            }

            if (trendingMovies.results.isNotEmpty()) {
                movieDao.deleteMovies(category = Category.trenidng.name)
                extracted(trendingMovies, category)
                emit(
                    Resource.Success(
                        movieDao.getMovieByCategory(category).let {
                            it.map { movieEntity ->
                                movieEntity.toMovie()
                            }
                        }
                    )
                )
            }
            emit(Resource.IsLoading(false))
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getTopRatedMovies(
        category: String,
        page: Int
    ): Flow<Resource<List<Movie>>> {
        return flow {

            emit(Resource.IsLoading(true))

            var topRatedMovies = movieDao.getMovieByCategory(category = category)

            if (topRatedMovies.isNotEmpty()) {
                emit(
                    Resource.Success(
                        data = topRatedMovies.let {
                            it.map { movieEntity ->
                                movieEntity.toMovie()
                            }
                        }
                    )
                )
            } else {
                val topRatedMoviesFromApi = try {
                    movieApi.getTopRatedMovies(page = page)
                } catch (e: IOException) {
                    e.printStackTrace()
                    emit(Resource.Error(message = e.message ?: ""))
                    return@flow
                } catch (e: HttpException) {
                    e.printStackTrace()
                    emit(Resource.Error(message = e.message ?: ""))
                    return@flow
                } catch (e: Exception) {
                    e.printStackTrace()
                    emit(Resource.Error(message = e.message ?: ""))
                    return@flow
                }

                movieDao.UpsertMovieList(
                    movies = topRatedMoviesFromApi.results.let {
                        it.map { movieDto ->
                            movieDto.toMovieEntity(category)
                        }
                    }
                )
                topRatedMovies = movieDao.getMovieByCategory(category = category)
                emit(
                    Resource.Success(
                        data = topRatedMovies.let {
                            it.map { movieEntity ->
                                movieEntity.toMovie()
                            }
                        }
                    )
                )
            }
            emit(Resource.IsLoading(false))
        }.flowOn(Dispatchers.IO)

    }

    override suspend fun getSearchMovie(
        query: String,
        page: Int
    ): Flow<Resource<List<Movie>>> {
        return flow<Resource<List<Movie>>> {
            emit(Resource.IsLoading(true))
            val searchMovieList = try {
                movieApi.getSearchMovies(
                    query = query,
                    page = page
                )
            }catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(message = e.message ?: ""))

                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error(message = e.message ?: ""))

                return@flow
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error(message = e.message ?: ""))

                return@flow
            }
            emit(
                Resource.Success(
                    data = searchMovieList.results.let {
                        it.map { movieDto ->
                            movieDto.toMovieEntity(null).toMovie()
                        }
                    }
                )
            )
            emit(Resource.IsLoading(false))

        }.flowOn(Dispatchers.IO)
    }

    private suspend fun extracted(
        trendingMovies: MovieListDto,
        category: String
    ) {
        movieDao.UpsertMovieList(
            movies = trendingMovies.results.let {
                it.map { movieDto ->
                    movieDto.toMovieEntity(category)
                }
            }
        )
    }
}
