package Query;

import fileio.MovieInputData;
import fileio.SerialInputData;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Longest  extends Names_getter{

    private final Integer number;
    private final String sort;

    public Longest(List<String> years, List<String> genres, List<MovieInputData> movies,
                   List<SerialInputData> serials, Integer number, String sort) {
        super(years, genres, movies, serials);
        this.number = number;
        this.sort = sort;
    }

    public String solve_longest () {
        List<String> names = get_names();
        List<Integer> durations = new LinkedList<>();
        for (int i = 0; i < names.size(); i++) {
            durations.add(i);
        }

        if (serials == null) {
            for (int i = 0; i < names.size(); i++) {
                for (MovieInputData movie : movies) {
                    if (names.get(i).equals(movie.getTitle())) {
                        durations.set(i, movie.getDuration());
                    }
                }
            }
        } else {
            for (int i = 0; i < names.size(); i++) {
                for (SerialInputData serial : serials) {
                    if (names.get(i).equals(serial.getTitle())) {
                        for (int k = 0; k < serial.getSeasons().size(); k++) {
                            durations.set(i, durations.get(i)
                                    + serial.getSeasons().get(k).getDuration());
                        }
                    }
                }
            }
        }

        sort_names_desc(names, durations);

        if (sort.equals("asc")) {
            Collections.reverse(names);
            Collections.reverse(durations);
        }

        return get_message(number, names, durations);
    }
}
