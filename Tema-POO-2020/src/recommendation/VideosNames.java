package recommendation;

import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;

import java.util.LinkedList;
import java.util.List;

public class VideosNames {

    private final List<UserInputData> users;
    private final List<MovieInputData> movies;
    private final List<SerialInputData> serials;
    private final String username;

    public VideosNames(final List<UserInputData> users, final List<MovieInputData> movies,
                       final List<SerialInputData> serials, final String username) {
        this.users = users;
        this.movies = movies;
        this.serials = serials;
        this.username = username;
    }

    /**
     * commm
     */
    public List<MovieInputData> getMovies() {
        return movies;
    }

    /**
     * commm
     */
    public List<SerialInputData> getSerials() {
        return serials;
    }

    /**
     * commm
     */
    public List<UserInputData> getUsers() {
        return users;
    }

    /**
     * commm
     */
    public List<String> getVideosNames() {
        List<String> names = new LinkedList<>();
        for (UserInputData user : users) {
            if (user.getUsername().equals(username)) {
                for (MovieInputData movie : movies) {
                    if (!user.getHistory().containsKey(movie.getTitle())) {
                        names.add(movie.getTitle());
                    }
                }
                for (SerialInputData serial : serials) {
                    if (!user.getHistory().containsKey(serial.getTitle())) {
                        names.add(serial.getTitle());
                    }
                }
                break;
            }
        }
        return names;
    }

    /**
     * commm
     */
    public boolean hasPremium() {
        for (UserInputData user : users) {
            if (user.getUsername().equals(username)) {
                if (user.getSubscriptionType().equals("PREMIUM")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * commm
     */
    public List<Double> getRatings(final List<String> names) {
        List<Double> ratings = new LinkedList<>();
        for (int i = 0; i < names.size(); i++) {
            ratings.add((double) 0);
            for (MovieInputData movie : movies) {
                if (movie.getTitle().equals(names.get(i))) {
                    double sum = 0;
                    double size = movie.getRatings().size();
                    for (int k = 0; k < movie.getRatings().size(); k++) {
                        sum += movie.getRatings().get(k);
                    }
                    if (size != 0) {
                        ratings.set(i, sum / size);
                    }
                    break;
                }
            }
            for (SerialInputData serial : serials) {
                if (serial.getTitle().equals(names.get(i))) {
                    double seasonsSum = 0;
                    double seasonNumber = serial.getNumberSeason();
                    for (int k = 0; k < serial.getNumberSeason(); k++) {
                        double sum = 0;
                        double size = serial.getSeasons().get(k).getRatings().size();
                        for (int l = 0; l < serial.getSeasons().get(k).getRatings().size(); l++) {
                            sum += serial.getSeasons().get(k).getRatings().get(l);
                        }
                        if (size != 0) {
                            seasonsSum += sum / size;
                        }
                    }
                    ratings.set(i, seasonsSum / seasonNumber);
                    break;
                }
            }
        }

        return ratings;
    }
}
