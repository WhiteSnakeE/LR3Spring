package com.knure.project.server;

import com.knure.project.server.service.MovieServiceImpl;

import javax.xml.ws.Endpoint;
public class MovieServer {

    public static void main(String[] args) {
        String url = "http://localhost:8080/movie";
        Endpoint.publish(url,new MovieServiceImpl());
        System.out.println("MovieService is published at " + url);
    }
}
