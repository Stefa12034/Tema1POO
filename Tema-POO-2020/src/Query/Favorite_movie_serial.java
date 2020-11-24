package Query;

import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;

import java.util.LinkedList;
import java.util.List;

public class Favorite_movie_serial extends Names_getter{

    private final List<UserInputData> users;
    private final int number;

    public Favorite_movie_serial(List<String> years, List<String> genres, List<MovieInputData> movies, List<UserInputData> users, List<SerialInputData> serials, int number) {
        super(years, genres, movies, serials);
        this.users = users;
        this.number = number;
    }

    public String solve_favorite_movie_serial() {
        List<String> names = get_names();
        List<Integer> appearences = new LinkedList<>();
        for (int i = 0; i < names.size(); i++) {
            appearences.add(0);
        }

        for (UserInputData user : users) {
            for (int j = 0; j < user.getFavoriteMovies().size(); j++) {
                for (int k = 0; k < names.size(); k++) {
                    if (names.get(k).equals(user.getFavoriteMovies().get(j))) {
                        appearences.set(k, appearences.get(k) + 1);
                    }
                }
            }
        }

        sort_names_desc(names, appearences);

        return get_message(number, names, appearences);

    }
}
