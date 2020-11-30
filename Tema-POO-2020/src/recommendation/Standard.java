package recommendation;

import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;

import java.util.List;

public class Standard extends VideosNames {

    public Standard(final List<UserInputData> users, final List<MovieInputData> movies,
                    final List<SerialInputData> serials, final String username) {
        super(users, movies, serials, username);
    }

    /**
     * commm
     */
    public String solveStandard() {
        List<String> names = getVideosNames();
        if (!names.isEmpty()) {
            return names.get(0);
        }
        return null;
    }
}
