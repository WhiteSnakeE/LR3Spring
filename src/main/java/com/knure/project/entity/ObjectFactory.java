//
// This file was generated by the Eclipse Implementation of JAXB, v3.0.0 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.11.12 at 03:00:18 PM MSK 
//


package com.knure.project.entity;


import javax.xml.bind.annotation.XmlRegistry;

/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.vlad.movies package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.vlad.movies
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link FilmCatalog }
     * 
     */
    public FilmCatalog createFilmCatalog() {
        return new FilmCatalog();
    }

    /**
     * Create an instance of {@link FilmCatalog.Movie }
     * 
     */
    public FilmCatalog.Movie createFilmCatalogMovie() {
        return new FilmCatalog.Movie();
    }

    /**
     * Create an instance of {@link FilmCatalog.Movie.Author }
     * 
     */
    public FilmCatalog.Movie.Author createFilmCatalogMovieAuthor() {
        return new FilmCatalog.Movie.Author();
    }

}
