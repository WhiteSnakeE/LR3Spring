package com.knure.project.server.service;


import com.knure.project.entity.FilmCatalog;
import com.knure.project.handler.SecurityHeader;
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
	@WebResult(targetNamespace="http://service.server.project.knure.com/")
	public FilmCatalog.Movie getMovieById(@WebParam(name="id", header = true)int id) throws DAOException;

	@WebMethod()
	public FilmCatalog.Movie addMovie(@WebParam(name="movie",  targetNamespace="http://service.server.project.knure.com/")FilmCatalog.Movie book) throws DAOException;
	
	@WebMethod(operationName = "removeMovie")
	@WebResult(targetNamespace="http://service.server.project.knure.com/")
	public FilmCatalog.Movie removeMovie(@WebParam(name="id",header = true) int id,
										 @WebParam(name="clientToken", header = true) SecurityHeader clientToken,
										 @WebParam(name="serverToken", header = true, mode = WebParam.Mode.OUT)
											 Holder<SecurityHeader> serverToken) throws DAOException;

	@WebMethod()
	@WebResult(targetNamespace="http://service.server.project.knure.com/")
	public List<FilmCatalog.Movie> getAllMoviesByAuthor(@WebParam(name="author",header = true)FilmCatalog.Movie.Author author);
	@WebMethod()
	@WebResult(targetNamespace="http://service.server.project.knure.com/")
	public FilmCatalog.Movie changeMovieName(@WebParam(name="movieID", header = true)int movieId,@WebParam(name="newName") String newName) throws DAOException;

}