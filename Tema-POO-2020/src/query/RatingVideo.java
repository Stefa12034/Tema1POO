package query;

import fileio.MovieInputData;
import fileio.SerialInputData;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Rating_video extends Names_getter{

    private final int number;
    private final String sort_type;

    public Rating_video(List<String> years, List<String> genres, List<MovieInputData> movies,
                        List<SerialInputData> serials, int number, String sort_type) {
        super(years, genres, movies, serials);
        this.number = number;
        this.sort_type = sort_type;
    }

    public String solve_rating_video() {
        List<String> names = get_names();
        List<Double> ratings = new LinkedList<>();
        for (int i = 0; i < names.size(); i++) {
            ratings.add((double) 0);
            if (serials == null) {
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
            } else {
                for (SerialInputData serial : serials) {
                    if (serial.getTitle().equals(names.get(i))) {
                        double seasons_sum = 0;
                        double season_number = serial.getNumberSeason();
                        for (int k = 0; k < serial.getNumberSeason(); k++) {
                            double sum = 0;
                            double size = serial.getSeasons().get(k).getRatings().size();
                            for (int l = 0; l < serial.getSeasons().get(k).getRatings().size(); l++) {
                                sum += serial.getSeasons().get(k).getRatings().get(l);
                            }
                            if (size != 0) {
                                seasons_sum += sum / size;
                            }
                        }
                        ratings.set(i, seasons_sum / season_number);
                        break;
                    }
                }
            }
        }

        for (int i = 0; i < names.size() - 1; i++) {
            for (int j = i + 1; j < names.size(); j++) {
                if (ratings.get(i) < ratings.get(j)) {
                    double aux_int = ratings.get(i);
                    ratings.set(i, ratings.get(j));
                    ratings.set(j, aux_int);
                    String aux_str = names.get(i);
                    names.set(i, names.get(j));
                    names.set(j, aux_str);
                }
            }
        }

        if (sort_type.equals("asc")) {
            Collections.reverse(names);
            Collections.reverse(ratings);
        }

        String message = "";
        boolean ok = false;
        int contor = number;
        for (int i = 0; i < names.size(); i++) {
            if (ratings.get(i) != 0) {
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
