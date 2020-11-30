package Command;

import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Rating {
    private final String username;
    private final String title;
    private final double rating;
    private final int season;
    private final List<MovieInputData> movies;
    private final List<SerialInputData> serials;
    private final List<UserInputData> users;

    public Rating(String username, String title, double rating, int season,
                  List<MovieInputData> movies, List<SerialInputData> serials,
                  List<UserInputData> users) {
        this.username = username;
        this.title = title;
        this.rating = rating;
        this.season = season;
        this.movies = movies;
        this.serials = serials;
        this.users = users;
    }

    public String solve_rating() {

        String message = "";

        for (UserInputData user : users) {
            if (user.getUsername().equals(username)) {
                if (user.getHistory().containsKey(title)) {
                    if (serials == null) {
                        if (!user.getUserMovieRatings().containsKey(title)) {
                            user.giveRating(title, rating);
                            for (MovieInputData movie : movies) {
                                if (movie.getTitle().equals(title)) {
                                    List<Double> ratings = movie.getRatings();
                                    ratings.add(rating);
                                    movie.setRatings(ratings);
                                    break;
                                }
                            }
                            message = "success -> " + title + " was rated with " + rating
                                    + " by " + user.getUsername();
                        } else {
                            message = "error -> " + title + " has been already rated";
                        }
                    } else {
                        for (SerialInputData serial : serials) {
                            if (serial.getTitle().equals(title)) {
                                if (!user.getUserSerialRatings().containsKey(title)) {
                                    List<Double> ratings
                                            = serial.getSeasons().get(season - 1).getRatings();
                                    ratings.add(rating);
                                    serial.getSeasons().get(season - 1).setRatings(ratings);
                                    ArrayList<Double> array
                                            = new ArrayList<>(serial.getNumberSeason() + 1);
                                    for (int k = 0; k < serial.getNumberSeason() + 1; k++) {
                                        array.add((double) 0);
                                    }
                                    array.set(season, rating);
                                    Map<String, ArrayList<Double>> map
                                            = user.getUserSerialRatings();
                                    map.put(title, array);
                                    user.setUserSerialRatings(map);
                                    message = "success -> " + title + " was rated with " + rating
                                            + " by " + user.getUsername();
                                } else {
                                    if (user.getUserSerialRatings().get(title).get(season)
                                            == (double) 0) {
                                        List<Double> ratings
                                                = serial.getSeasons().get(season - 1).getRatings();
                                        ratings.add(rating);
                                        serial.getSeasons().get(season - 1).setRatings(ratings);
                                        ArrayList<Double> array
                                                = user.getUserSerialRatings().get(title);
                                        Map<String, ArrayList<Double>> map
                                                = user.getUserSerialRatings();
                                        map.put(title, array);
                                        user.setUserSerialRatings(map);
                                        message = "success -> " + title + " was rated with "
                                                + rating + " by " + user.getUsername();
                                    } else {
                                        message = "error -> " + title + " has been already rated";
                                    }
                                }
                                break;
                            }
                        }
                    }
                } else {
                    message = "error -> " + title + " is not seen";
                }
                break;
            }
        }

        return message;
    }
}
