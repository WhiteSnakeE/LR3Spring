package com.knure.project.server.dao;

import com.knure.project.dbtable.DBTable;
import com.knure.project.dbtable.DBTableFactory;
import com.knure.project.entity.FilmCatalog;
import com.knure.project.entity.Genre;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class MovieDAOInMemoryImpl implements MovieDAO {


    DBTable<FilmCatalog.Movie> movieDBTable = DBTableFactory.instance();

    private static MovieDAOInMemoryImpl instance;

    private MovieDAOInMemoryImpl() {
        initMovies();
    }

    public static synchronized MovieDAOInMemoryImpl instance() {
        if (instance == null) {
            instance = new MovieDAOInMemoryImpl();
        }
        return instance;
    }
    @Override
    public List<FilmCatalog.Movie> getAllMoviesByAuthor(FilmCatalog.Movie.Author author) {
        List<FilmCatalog.Movie> movies = new ArrayList<>();
        for (FilmCatalog.Movie movie : movieDBTable.selectAll()) {
            if (movie.getAuthor().getName().equals(author.getName()) &&
                    movie.getAuthor().getSurname().equals(author.getSurname()) &&
                    movie.getAuthor().getBirthDate().equals(author.getBirthDate())){
                movies.add(movie);
            }

        }
        return movies;
    }

    @Override
    public FilmCatalog.Movie getMovieById(int id) throws DAOException {
        try {
            return movieDBTable.get(id);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public int addMovie(FilmCatalog.Movie movie) {
        int id = movieDBTable.insert(movie);
        movie.setId(id);
        try {
            movieDBTable.update(id, movie);
        } catch (SQLException e) {
            // do nothing, always exist
        }
        return id;
    }

    @Override
    public FilmCatalog.Movie changeMovieName(int movieI, String newName) throws DAOException {
        FilmCatalog.Movie movie;
        try {
            movie = movieDBTable.get(movieI);
        } catch (SQLException e) {
            throw new DAOException("This movie doesn't exist");
        }
        if (newName.isEmpty())
            throw new DAOException("Name can't be empty");
        movie.setTitle(newName);
        try {
            return movieDBTable.update(movieI, movie);
        } catch (SQLException e) {
            throw new DAOException("Can't be updated");
        }
    }

    @Override
    public FilmCatalog.Movie removeMovie(int movieId) throws DAOException {
        try {
            return movieDBTable.delete(movieId);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private FilmCatalog.Movie newMovie(String title, Date releaseDate, Genre genre, FilmCatalog.Movie.Author author) {
        FilmCatalog.Movie movie = new FilmCatalog.Movie();
        movie.setTitle(title);
        movie.setReleaseDate(releaseDate);
        movie.setGenre(genre);
        movie.setAuthor(author);
        return movie;
    }
    private void initMovies() {
        List<FilmCatalog.Movie> movies = List.of(
                newMovie( "Avengers", new Date(2012 - 1900, Calendar.MAY, 10),
                        Genre.ACTION,
                        new FilmCatalog.Movie.Author("Kevin", "Feige", new Date(1973 - 1900, Calendar.JULY, 2))),
                newMovie("Avengers 2", new Date(2015 - 1900, Calendar.AUGUST, 8),
                        Genre.ACTION,
                        new FilmCatalog.Movie.Author("Kevin", "Feige", new Date(1973 - 1900, Calendar.JULY, 2))),
                newMovie( "It", new Date(1985 - 1900, Calendar.MAY, 12),
                        Genre.ACTION,
                        new FilmCatalog.Movie.Author("Stephen", "King", new Date(1947 - 1900, Calendar.OCTOBER, 21)))
        );

        movies.forEach(this::addMovie);
    }
}
