package Query;

import fileio.MovieInputData;
import fileio.SerialInputData;

import java.util.LinkedList;
import java.util.List;

public class Names_getter {
    final List<String> years;
    final List<String> genres;
    final List<MovieInputData> movies;
    final List<SerialInputData> serials;

    public Names_getter(List<String> years, List<String> genres, List<MovieInputData> movies, List<SerialInputData> serials) {
        this.years = years;
        this.genres = genres;
        this.movies = movies;
        this.serials = serials;
    }

    public List<String> get_names() {
        List<String> names = new LinkedList<>();
        if (serials == null) {
            for (MovieInputData movie : movies) {
                int genres_size = genres.size();
                for (String genre : genres) {
                    if (movie.getGenres().contains(genre)) {
                        genres_size--;
                    }
                }

                if ((genres_size == 0 || genres.get(0) == null) && (years.contains(String.valueOf(movie.getYear())) || years.get(0) == null)) {
                    names.add(movie.getTitle());
                }

            }
        } else {
            for (SerialInputData serial : serials) {
                int genres_size = genres.size();
                for (String genre : genres) {
                    if (serial.getGenres().contains(genre)) {
                        genres_size--;
                    }
                }

                if ((genres_size == 0 || genres.get(0) == null) && (years.contains(String.valueOf(serial.getYear())) || years.get(0) == null)) {
                    names.add(serial.getTitle());
                }
            }
        }
        return names;
    }

    public void sort_names_desc (List<String> names, List<Integer> numbers) {
        for (int i = 0; i < names.size() - 1; i++) {
            for (int j = i + 1; j < names.size(); j++) {
                if ((numbers.get(i) < numbers.get(j)) || (numbers.get(i).equals(numbers.get(j)) && names.get(i).compareTo(names.get(j)) < 0)) {
                    int aux_int = numbers.get(i);
                    numbers.set(i, numbers.get(j));
                    numbers.set(j, aux_int);
                    String aux_str = names.get(i);
                    names.set(i, names.get(j));
                    names.set(j, aux_str);
                }
            }
        }
    }

    public String get_message (int number, List<String> names, List<Integer> appearences) {
        String message = "";
        boolean ok = false;
        int contor = number;
        for (int i = 0; i < names.size(); i++) {
            if (appearences.get(i) != 0) {
                if (contor != 0) {
                    if (ok) {
                        message += ", " + names.get(i);
                    } else {
                        message += names.get(i);
                        ok = true;
                    }
                    contor--;
                } else {
                    break;
                }
            }
        }
        return message;
    }
}
