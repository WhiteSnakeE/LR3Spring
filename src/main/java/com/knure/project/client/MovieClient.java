package com.knure.project.client;

import com.knure.project.entity.FilmCatalog;
import com.knure.project.entity.Genre;
import com.knure.project.server.service.MovieService;

import javax.xml.namespace.QName;
import javax.xml.ws.Holder;
import javax.xml.ws.Service;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MovieClient {
    public static void main(String[] args) {
        try {
            URL url = new URL("http://localhost:8080/movie?wsdl");
            QName qname = new QName("http://service.server.project.knure.com/", "MovieServiceImplService");

            Service service = Service.create(url, qname);
            MovieService movieService = service.getPort(MovieService.class);


            FilmCatalog.Movie idMovie = movieService.getMovieById(1);
            System.out.println("Movie: " + idMovie);

            FilmCatalog.Movie.Author author = new FilmCatalog.Movie.Author("Kevin", "Feige", new Date(1973 - 1900, Calendar.JULY, 2));
            FilmCatalog.Movie newMovie = new FilmCatalog.Movie( "Avangers 3", new Date(2019 - 1900, Calendar.MAY, 21)
                    ,Genre.ACTION, author);

            movieService.addMovie(newMovie);
            System.out.println("New Movie added: " + newMovie);

            List<FilmCatalog.Movie> movies = movieService.getAllMoviesByAuthor(author);
            System.out.println("_______________________Movies after adding_______________________");
            for (FilmCatalog.Movie movie:movies) {
                System.out.println(movie);
            }
            String clientToken = "MovieServiceImplService";
            Holder<String> serverToken = new Holder<>();
            FilmCatalog.Movie removedMovie = movieService.removeMovie(4,clientToken,serverToken);
            System.out.println("Removed movie: " + removedMovie);

            List<FilmCatalog.Movie> moviesAfterRemoving = movieService.getAllMoviesByAuthor(author);
            System.out.println("_______________________Movies after removing_______________________");
            for (FilmCatalog.Movie movie:moviesAfterRemoving) {
                System.out.println(movie);
            }

            FilmCatalog.Movie changedMovie = movieService.changeMovieName(1,"Iron man");
            System.out.println("Changed movie " + changedMovie);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
