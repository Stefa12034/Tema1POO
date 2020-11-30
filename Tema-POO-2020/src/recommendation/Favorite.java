package recommendation;

import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;

import java.util.LinkedList;
import java.util.List;

public class Favorite extends VideosNames {

    public Favorite(final List<UserInputData> users, final List<MovieInputData> movies,
                    final List<SerialInputData> serials, final String username) {
        super(users, movies, serials, username);
    }

    /**
     * commm
     */
    public String solveFavorite() {
        if (hasPremium()) {
            List<String> names = getVideosNames();
            List<Integer> noApp = new LinkedList<>();
            for (int i = 0; i < names.size(); i++) {
                noApp.add(0);
            }
            for (int i = 0; i < names.size(); i++) {
                for (UserInputData user : getUsers()) {
                    if (user.getFavoriteMovies().contains(names.get(i))) {
                        noApp.set(i, noApp.get(i) + 1);
                    }
                }
            }

            if (!names.isEmpty()) {
                int max = noApp.get(0);
                String message = names.get(0);
                for (int i = 1; i < names.size(); i++) {
                    if (max < noApp.get(i)) {
                        max = noApp.get(i);
                        message = names.get(i);
                    }
                }
                return message;
            }
            return null;
        }
        return "standard";
    }

}
