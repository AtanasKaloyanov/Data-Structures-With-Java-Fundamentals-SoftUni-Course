package org.softuni.exam.structures;

import org.softuni.exam.entities.Actor;
import org.softuni.exam.entities.Movie;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class MovieDatabaseImpl implements MovieDatabase {
    private Map<String, Actor> actorsById;

    private Map<String, Movie> moviesById;

    // key - actorsId, value List<Movie>
    private Map<String, List<Movie>> moviesByActorsId;

    // key - actorsId, value - Actor
    private Map<String, Actor> actorWithouthMovie;


    public MovieDatabaseImpl() {
        this.actorsById = new LinkedHashMap<>();
        this.moviesById = new LinkedHashMap<>();
        this.moviesByActorsId = new LinkedHashMap<>();
        this.actorWithouthMovie = new LinkedHashMap<>();
    }

    @Override
    public void addActor(Actor actor) {

        this.actorsById.put(actor.getId(), actor);
        this.moviesByActorsId.put(actor.getId(), new ArrayList<>());
        this.actorWithouthMovie.put(actor.getId(), actor);

    }

    @Override
    public void addMovie(Actor actor, Movie movie) throws IllegalArgumentException {
        // two actors can have the same movie

        if (!contains(actor)) {
            throw new IllegalArgumentException();
        }

        if (!contains(movie)) {
            this.moviesById.put(movie.getId(), movie);
        }

        this.actorWithouthMovie.remove(actor.getId());

        if (this.moviesByActorsId.get(actor.getId()).contains(movie)) {
            throw new IllegalArgumentException();
        }

        this.moviesByActorsId.get(actor.getId()).add(movie);
    }

    @Override
    public boolean contains(Actor actor) {
        return this.actorsById.containsKey(actor.getId());
    }

    @Override
    public boolean contains(Movie movie) {
        return this.moviesById.containsKey(movie.getId());
    }

    @Override
    public Iterable<Movie> getAllMovies() {
        return this.moviesById.values();
    }

    @Override
    public Iterable<Actor> getNewbieActors() {
        return this.actorWithouthMovie.values();
    }

    @Override
    public Iterable<Movie> getMoviesOrderedByBudgetThenByRating() {
        return this.moviesById.values()
                .stream()
                .sorted((movie1, movie2) -> {
                    int result = Double.compare(movie2.getBudget(), movie1.getBudget());

                    if (result == 0) {
                        result = Double.compare(movie2.getRating(), movie1.getRating());
                    }

                    return result;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<Actor> getActorsOrderedByMaxMovieBudgetThenByMoviesCount() {

        return this.actorsById.values()
                .stream()
                .sorted((actor1, actor2) -> {
                            double firstActorResult = this.moviesByActorsId
                                    .get(actor1.getId())
                                    .stream()
                                    .sorted((movie1, movie2) -> Double.compare(movie2.getBudget(), movie1.getBudget()))
                                    .collect(Collectors.toList())
                                    .get(0)
                                    .getBudget();


                            double secondActorResult = this.moviesByActorsId
                                    .get(actor2.getId())
                                    .stream()
                                    .sorted((movie1, movie2) -> Double.compare(movie2.getBudget(), movie1.getBudget()))
                                    .collect(Collectors.toList())
                                    .get(0)
                                    .getBudget();

                            int result = Double.compare(secondActorResult, firstActorResult);

                            if (result == 0) {
                                int firstActorMovies = this.moviesByActorsId.get(actor1.getId()).size();
                                int secondActorMovies = this.moviesByActorsId.get(actor2.getId()).size();

                                result = Integer.compare(secondActorMovies, firstActorMovies);
                            }

                            return result;
                        }
                ).collect(Collectors.toList());
    }


    @Override
    public Iterable<Movie> getMoviesInRangeOfBudget(double lower, double upper) {
        return this.moviesById.values()
                .stream()
                .filter(movie -> movie.getBudget() >= lower && movie.getBudget() <= upper)
                .sorted((movie1, movie2) -> Double.compare(movie2.getRating(), movie1.getRating()))
                .collect(Collectors.toList());
    }
}
