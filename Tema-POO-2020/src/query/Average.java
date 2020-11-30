package Query;

import fileio.ActorInputData;
import fileio.MovieInputData;
import fileio.SerialInputData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Average {

    private final List<ActorInputData> actor;
    private final List<MovieInputData> movies;
    private final List<SerialInputData> serials;
    private final int number;
    private final String sort_type;

    public Average(List<ActorInputData> actor, List<MovieInputData> movies,
                   List<SerialInputData> serials, int number, String sort_type) {
        this.actor = actor;
        this.movies = movies;
        this.serials = serials;
        this.number = number;
        this.sort_type = sort_type;
    }

    public String solve_average() {
        ArrayList<String> actors = new ArrayList<>();
        ArrayList<Double> avarage_ratings = new ArrayList<>();
        for (ActorInputData actorInputData : actor) {
            actors.add(actorInputData.getName());
            double rating_sum = 0;
            double rating_increment = 0;
            for (int k = 0; k < actorInputData.getFilmography().size(); k++) {
                boolean ok = true;
                for (MovieInputData movie : movies) {
                    if (movie.getTitle().equals(actorInputData.getFilmography().get(k))) {
                        ok = false;
                        for (int p = 0; p < movie.getRatings().size(); p++) {
                            rating_sum += movie.getRatings().get(p);
                            rating_increment++;
                        }
                        break;
                    }
                }
                if (ok) {
                    for (SerialInputData serial : serials) {
                        if (serial.getTitle().equals(actorInputData.getFilmography().get(k))) {
                            double serial_rating = 0;
                            double serial_increment = serial.getNumberSeason();
                            for (int p = 0; p < serial.getSeasons().size(); p++) {
                                double rating_season = 0;
                                int season_rating_increment = 0;
                                for (int h = 0; h < serial.getSeasons().get(p).getRatings().size();
                                     h++) {
                                    rating_season += serial.getSeasons().get(p).getRatings().get(h);
                                    season_rating_increment++;
                                }
                                if (season_rating_increment != 0) {
                                    serial_rating += rating_season / season_rating_increment;
                                }
                            }
                            if (serial_increment != 0) {
                                rating_sum += serial_rating / serial_increment;
                                rating_increment++;
                            }
                            break;
                        }
                    }
                }
            }
            if (rating_increment != 0) {
                avarage_ratings.add(rating_sum / rating_increment);
            } else {
                avarage_ratings.add((double) 0);
            }
        }
        int j, k;
        for (j = 0; j < actors.size() - 1; j++) {
            for (k = j + 1; k < actors.size(); k++) {
                if ((avarage_ratings.get(j) > avarage_ratings.get(k)) ||
                        (avarage_ratings.get(j).equals(avarage_ratings.get(k))
                                && actors.get(j).compareTo(actors.get(k)) > 0)) {
                    double aux = avarage_ratings.get(j);
                    avarage_ratings.set(j, avarage_ratings.get(k));
                    avarage_ratings.set(k, aux);
                    String aux2 = actors.get(j);
                    actors.set(j, actors.get(k));
                    actors.set(k, aux2);
                }


            }
        }

        if (sort_type.equals("desc")) {
            Collections.reverse(actors);
            Collections.reverse(avarage_ratings);
        }

        StringBuilder message = new StringBuilder();
        boolean ok = false;
        int counter = number;
        for (j = 0; j < actors.size(); j++) {
            if (avarage_ratings.get(j) != 0 && counter != 0) {
                if (ok) {
                    message.append(", ").append(actors.get(j));
                    counter--;
                } else {
                    message.append(actors.get(j));
                    counter--;
                    ok = true;
                }
            }
        }
        return message.toString();
    }
}
