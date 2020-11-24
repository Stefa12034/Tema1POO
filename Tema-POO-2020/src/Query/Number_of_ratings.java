package Query;

import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Number_of_ratings extends Names_getter{

    private final List<UserInputData> users;
    private final int number;
    private final String sort_type;

    public Number_of_ratings(List<String> years, List<String> genres, List<MovieInputData> movies, List<SerialInputData> serials, List<UserInputData> users, int number, String sort_type) {
        super(years, genres, movies, serials);
        this.users = users;
        this.number = number;
        this.sort_type = sort_type;
    }

    public String solve_number_of_ratings() {
        List<String> names = new LinkedList<>();
        List<Integer> num_ratings = new LinkedList<>();
        for (UserInputData user : users) {
            names.add(user.getUsername());
            num_ratings.add(user.getUser_ratings().size() + user.getUser_season_ratings().size());
        }

        sort_names_desc(names, num_ratings);

        if (sort_type.equals("asc")) {
            Collections.reverse(names);
            Collections.reverse(num_ratings);
        }

        return get_message(number, names, num_ratings);
    }
}
