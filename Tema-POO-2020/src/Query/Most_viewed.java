package Query;

import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;

import java.util.LinkedList;
import java.util.List;

public class Most_viewed extends Names_getter{

    private final List<UserInputData> users;
    private final int number;

    public Most_viewed(List<String> years, List<String> genres, List<MovieInputData> movies, List<SerialInputData> serials, List<UserInputData> users, int number) {
        super(years, genres, movies, serials);
        this.users = users;
        this.number = number;
    }

    public String solve_most_viewed() {
        List<String> names = get_names();
        List<Integer> views = new LinkedList<>();
        for (int i = 0; i < names.size(); i++) {
            views.add(0);
        }

        for (UserInputData user : users) {
            for (int j = 0; j < names.size(); j++) {
                if (user.getHistory().containsKey(names.get(j))) {
                    views.set(j, views.get(j) + user.getHistory().get(names.get(j)));
                }
            }
        }

        sort_names_desc(names, views);

        return get_message(number, names, views);
    }
}
