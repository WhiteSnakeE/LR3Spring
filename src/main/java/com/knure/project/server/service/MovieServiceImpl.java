package com.knure.project.server.service;


import com.knure.project.entity.FilmCatalog;
import com.knure.project.handler.SecurityHeader;
import com.knure.project.server.dao.DAOException;
import com.knure.project.server.dao.MovieDAO;
import com.knure.project.server.dao.MovieDAOInMemoryImpl;

import javax.jws.HandlerChain;
import javax.jws.WebService;
import javax.xml.ws.Holder;
import java.util.List;

//@HandlerChain(file = "security_handler.xml")
@WebService(endpointInterface="com.knure.project.server.service.MovieService")

public class MovieServiceImpl implements MovieService {
	private static MovieDAO movieDAO = MovieDAOInMemoryImpl.instance();
	

	@Override
	public FilmCatalog.Movie getMovieById(int id) throws DAOException {
		return movieDAO.getMovieById(id);
	}
	

	@Override
	public FilmCatalog.Movie addMovie(FilmCatalog.Movie movie) throws DAOException {
		return movieDAO.addMovie(movie);
	}

	@Override
	public FilmCatalog.Movie removeMovie(int id, SecurityHeader clientToken, Holder<SecurityHeader> serverToken)
			throws DAOException {
		return movieDAO.removeMovie(id);
	}

	@Override
	public List<FilmCatalog.Movie> getAllMoviesByAuthor(FilmCatalog.Movie.Author author) {
		return movieDAO.getAllMoviesByAuthor(author);
	}

	@Override
	public FilmCatalog.Movie changeMovieName(int movieId, String newName) throws DAOException {
		return movieDAO.changeMovieName(movieId, newName);
	}

}
