package com.knure.project.server.dao;


import com.knure.project.entity.FilmCatalog;

import java.util.List;


public interface MovieDAO {

	public List<FilmCatalog.Movie> getAllMoviesByAuthor(FilmCatalog.Movie.Author author);

	public FilmCatalog.Movie getMovieById(int item) throws DAOException;

	public int addMovie(FilmCatalog.Movie movie);

	public FilmCatalog.Movie changeMovieName(int movieI, String newName) throws DAOException;

	public FilmCatalog.Movie removeMovie(int movieId) throws DAOException;

}
