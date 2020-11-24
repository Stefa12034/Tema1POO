package fileio;

import java.util.ArrayList;
import java.util.Map;

/**
 * Information about an user, retrieved from parsing the input test files
 * <p>
 * DO NOT MODIFY
 */
public final class UserInputData {
    /**
     * User's username
     */
    private final String username;
    /**
     * Subscription Type
     */
    private final String subscriptionType;
    /**
     * The history of the movies seen
     */
    private final Map<String, Integer> history;
    /**
     * Movies added to favorites
     */
    private final ArrayList<String> favoriteMovies;
    /**
     * The ratings of the movies
     */
    private final Map<String, Double> user_ratings;
    /**
     * The ratings of the seasons
     */
    private Map<String, ArrayList<Double>> user_season_ratings;

    public UserInputData(final String username, final String subscriptionType,
                         final Map<String, Integer> history,
                         final ArrayList<String> favoriteMovies,
                         final Map<String, Double> user_ratings,
                         final Map<String, ArrayList<Double>> user_season_ratings) {
        this.username = username;
        this.subscriptionType = subscriptionType;
        this.favoriteMovies = favoriteMovies;
        this.history = history;
        this.user_ratings = user_ratings;
        this.user_season_ratings = user_season_ratings;
    }

    public String getUsername() {
        return username;
    }

    public Map<String, Integer> getHistory() {
        return history;
    }

    public String getSubscriptionType() {
        return subscriptionType;
    }

    public ArrayList<String> getFavoriteMovies() {
        return favoriteMovies;
    }

    public void increment_views (String key, int oldvalue) {
        history.replace(key, oldvalue, oldvalue + 1);
    }

    public Map<String, Double> getUser_ratings() {
        return user_ratings;
    }

    public void give_rating (String key, Double given_rating) {
        user_ratings.put(key, given_rating);
    }

    public Map<String, ArrayList<Double>> getUser_season_ratings() {
        return user_season_ratings;
    }

    public void setUser_season_ratings(Map<String, ArrayList<Double>> user_season_ratings) {
        this.user_season_ratings = user_season_ratings;
    }

    @Override
    public String toString() {
        return "UserInputData{" + "username='"
                + username + '\'' + ", subscriptionType='"
                + subscriptionType + '\'' + ", history="
                + history + ", favoriteMovies="
                + favoriteMovies + '}';
    }
}
