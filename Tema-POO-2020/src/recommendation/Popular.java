package recommendation;

import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;

import java.util.LinkedList;
import java.util.List;

public class Popular extends VideosNames {

    public Popular(final List<UserInputData> users, final List<MovieInputData> movies,
                   final List<SerialInputData> serials, final String username) {
        super(users, movies, serials, username);
    }

    /**
     * commm
     */
    public String solvePopular() {
        if (hasPremium()) {
            List<String> names = getVideosNames();
            List<String> genres = new LinkedList<>();
            List<Integer> noViews = new LinkedList<>();
            List<String> first = new LinkedList<>();
            for (String name : names) {
                for (int j = 0; j < getMovies().size(); j++) {
                    if (name.equals(getMovies().get(j).getTitle())) {
                        for (int k = 0; k < getMovies().get(j).getGenres().size(); k++) {
                            if (!genres.contains(getMovies().get(j).getGenres().get(k))) {
                                genres.add(getMovies().get(j).getGenres().get(k));
                                first.add(getMovies().get(j).getTitle());
                                noViews.add(0);
                                for (MovieInputData movie : getMovies()) {
                                    if (movie.getGenres().contains(
                                            getMovies().get(j).getGenres().get(k))) {
                                        for (UserInputData user : getUsers()) {
                                            if (user.getHistory().containsKey(movie.getTitle())) {
                                                noViews.set(noViews.size() - 1,
                                                        noViews.get(noViews.size() - 1)
                                                        + user.getHistory().get(movie.getTitle()));
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                for (int j = 0; j < getSerials().size(); j++) {
                    if (name.equals(getSerials().get(j).getTitle())) {
                        for (int k = 0; k < getSerials().get(j).getGenres().size(); k++) {
                            if (!genres.contains(getSerials().get(j).getGenres().get(k))) {
                                genres.add(getSerials().get(j).getGenres().get(k));
                                first.add(getSerials().get(j).getTitle());
                                noViews.add(0);
                                for (SerialInputData serial : getSerials()) {
                                    if (serial.getGenres().contains(
                                            getSerials().get(j).getGenres().get(k))) {
                                        for (UserInputData user : getUsers()) {
                                            if (user.getHistory().containsKey(serial.getTitle())) {
                                                noViews.set(noViews.size() - 1,
                                                        noViews.get(noViews.size() - 1)
                                                                + user.getHistory().get(
                                                                        serial.getTitle()));
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (!first.isEmpty()) {
                int max = noViews.get(0);
                String message = first.get(0);
                for (int i = 1; i < first.size(); i++) {
                    if (max < noViews.get(i)) {
                        max = noViews.get(i);
                        message = first.get(i);
                    }
                }
                return message;
            }
            return null;
        }
        return "standard";
    }
}
