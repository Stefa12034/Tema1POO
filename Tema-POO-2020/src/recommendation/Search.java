package recommendation;

import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;

import java.util.LinkedList;
import java.util.List;

public class Search extends VideosNames {

    private final String genre;

    public Search(final List<UserInputData> users, final List<MovieInputData> movies,
                  final List<SerialInputData> serials, final String username, final String genre) {
        super(users, movies, serials, username);
        this.genre = genre;
    }

    /**
     * commm
     */
    public String solveSearch() {
        if (hasPremium()) {
            List<String> allNames = getVideosNames();
            List<String> names = new LinkedList<>();
            for (String s : allNames) {
                for (MovieInputData movie : getMovies()) {
                    if (s.equals(movie.getTitle())) {
                        if (movie.getGenres().contains(genre)) {
                            names.add(s);
                        }
                    }
                }
                for (SerialInputData serial : getSerials()) {
                    if (s.equals(serial.getTitle())) {
                        if (serial.getGenres().contains(genre)) {
                            names.add(s);
                        }
                    }
                }
            }

            List<Double> ratings = getRatings(allNames);

            for (int i = 0; i < names.size() - 1; i++) {
                for (int j = i + 1; j < names.size(); j++) {
                    if ((ratings.get(i) < ratings.get(j))
                            || (ratings.get(i).equals(ratings.get(j))
                            && names.get(i).compareTo(names.get(j)) > 0)) {
                        double auxInt = ratings.get(i);
                        ratings.set(i, ratings.get(j));
                        ratings.set(j, auxInt);
                        String auxStr = names.get(i);
                        names.set(i, names.get(j));
                        names.set(j, auxStr);
                    }
                }
            }

            if (!names.isEmpty()) {
                StringBuilder message = new StringBuilder();
                boolean ok = false;
                for (String name : names) {
                    if (ok) {
                        message.append(", ").append(name);
                    } else {
                        message.append(name);
                        ok = true;
                    }
                }
                return message.toString();
            }
            return null;
        }
        return "standard";
    }
}
