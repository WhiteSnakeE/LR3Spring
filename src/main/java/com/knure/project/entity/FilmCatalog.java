

package com.knure.project.entity;


import javax.xml.bind.annotation.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "movie"
})
@XmlRootElement(name = "filmCatalog")
public class FilmCatalog {

    @XmlElement(required = true)
    protected List<Movie> movie;

    public List<Movie> getMovie() {
        if (movie == null) {
            movie = new ArrayList<>();
        }
        return this.movie;
    }

    @Override
    public String toString() {
        return "FilmCatalog{" +
                "movie=" + movie +
                '}';
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {

    })
    public static class Movie {

        @XmlElement(required = true)
        protected String title;
        protected Date releaseDate;
        @XmlElement(required = true)
        protected Genre genre;
        protected Author author;
        @XmlAttribute(name = "id", required = true)
        protected int id;

        @Override
        public String toString() {
            return "Movie{" +
                    "title='" + title + '\'' +
                    ", releaseDate=" + releaseDate +
                    ", genre='" + genre + '\'' +
                    ", author=" + author +
                    ", id=" + id +
                    '}';
        }

        public Movie( String title, Date releaseDate, Genre genre, Author author) {
            this.title = title;
            this.releaseDate = releaseDate;
            this.genre = genre;
            this.author = new Author(author.getName(), author.getSurname(), author.getBirthDate());

        }

        public Movie() {
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String value) {
            this.title = value;
        }

        public Date getReleaseDate() {
            return releaseDate;
        }

        public void setReleaseDate(Date value) {
            this.releaseDate = value;
        }

        public Genre getGenre() {
            return genre;
        }

        public void setGenre(Genre value) {
            this.genre = value;
        }

        public Author getAuthor() {
            return author;
        }

        public void setAuthor(Author value) {
            this.author = value;
        }

        public int getId() {
            return id;
        }

        public void setId(int value) {
            this.id = value;
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {

        })
        public static class Author {

            @XmlElement(required = true)
            protected String name;
            @XmlElement(required = true)
            protected String surname;
            protected Date birthDate;

            public Author(String name, String surname, Date birthDate) {
                this.name = name;
                this.surname = surname;
                this.birthDate = birthDate;
            }

            public Author() {

            }

            public String getName() {
                return name;
            }

            public void setName(String value) {
                this.name = value;
            }

            public String getSurname() {
                return surname;
            }

            public void setSurname(String value) {
                this.surname = value;
            }

            public Date getBirthDate() {
                return birthDate;
            }

            public void setBirthDate(Date value) {
                this.birthDate = value;
            }

            @Override
            public String toString() {
                return "Author{" +
                        "name='" + name + '\'' +
                        ", surname='" + surname + '\'' +
                        ", birthDate=" + birthDate +
                        '}';
            }
        }

    }

}
