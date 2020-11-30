package recommendation;

import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;

import java.util.List;

public class BestUnseen extends VideosNames {

    public BestUnseen(final List<UserInputData> users, final List<MovieInputData> movies,
                      final List<SerialInputData> serials, final String username) {
        super(users, movies, serials, username);
    }

    /**
     * commm
     */
    public String solveBestUnseen() {
        List<String> names = getVideosNames();
        List<Double> ratings = getRatings(names);


        for (int i = 0; i < names.size() - 1; i++) {
            for (int j = i + 1; j < names.size(); j++) {
                if (ratings.get(i) < ratings.get(j)) {
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
            return names.get(0);
        }
        return null;
    }
}
