package com.knure.project.server.service;


import com.knure.project.entity.FilmCatalog;
import com.knure.project.server.dao.DAOException;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.Holder;
import java.util.List;

@WebService
public interface MovieService {

	@WebMethod()
	@WebResult(targetNamespace="http://filmcatalog.com/movies")
	public FilmCatalog.Movie getMovieById(@WebParam(name="id", header = true)int id) throws DAOException;

	@WebMethod()
	public int addMovie(@WebParam(name="movie",  targetNamespace="http://filmcatalog.com/movies")FilmCatalog.Movie book) throws DAOException;
	
	@WebMethod()
	@WebResult(targetNamespace="http://filmcatalog.com/movies")
	public FilmCatalog.Movie removeMovie(@WebParam(name="id",header = true) int id,
										 @WebParam(name="clientToken", header = true) String clientToken,
										 @WebParam(name="serverToken", header = true, mode = WebParam.Mode.OUT)
											 Holder<String> serverToken) throws DAOException;

	@WebMethod()
	@WebResult(targetNamespace="http://filmcatalog.com/movies")
	public List<FilmCatalog.Movie> getAllMoviesByAuthor(@WebParam(name="author",header = true)FilmCatalog.Movie.Author author);
	@WebMethod()
	@WebResult(targetNamespace="http://filmcatalog.com/movies")
	public FilmCatalog.Movie changeMovieName(@WebParam(name="movieID", header = true)int movieId,@WebParam(name="newName") String newName) throws DAOException;

}