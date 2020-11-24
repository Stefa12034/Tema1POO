package main;

import Command.Rating;
import Query.*;
import actor.ActorsAwards;
import checker.Checkstyle;
import checker.Checker;
import common.Constants;
import fileio.*;
import org.json.simple.JSONArray;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The entry point to this homework. It runs the checker that tests your implentation.
 */
public final class Main {
    /**
     * for coding style
     */
    private Main() {
    }

    /**
     * Call the main checker and the coding style checker
     * @param args from command line
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException {
        File directory = new File(Constants.TESTS_PATH);
        Path path = Paths.get(Constants.RESULT_PATH);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        File outputDirectory = new File(Constants.RESULT_PATH);

        Checker checker = new Checker();
        checker.deleteFiles(outputDirectory.listFiles());

        for (File file : Objects.requireNonNull(directory.listFiles())) {

            String filepath = Constants.OUT_PATH + file.getName();
            File out = new File(filepath);
            boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getAbsolutePath(), filepath);
            }
        }

        checker.iterateFiles(Constants.RESULT_PATH, Constants.REF_PATH, Constants.TESTS_PATH);
        Checkstyle test = new Checkstyle();
        test.testCheckstyle();
    }

    /**
     * @param filePath1 for input file
     * @param filePath2 for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void action(final String filePath1,
                              final String filePath2) throws IOException {
        InputLoader inputLoader = new InputLoader(filePath1);
        Input input = inputLoader.readData();

        Writer fileWriter = new Writer(filePath2);
        JSONArray arrayResult = new JSONArray();

        //TODO add here the entry point to your implementation

        for (int i = 0; i < input.getCommands().size(); i++) {
            ActionInputData current_action = input.getCommands().get(i);
            if (current_action.getActionType().equals("command")) {

                //Favorite

                if (current_action.getType().equals("favorite")) {
                    for (int j = 0; j < input.getUsers().size(); j++) {
                        UserInputData current_user = input.getUsers().get(j);
                        if (current_user.getUsername().equals(current_action.getUsername())) {
                                if (current_user.getFavoriteMovies().contains(current_action.getTitle())) {
                                    arrayResult.add(fileWriter.writeFile(current_action.getActionId(), "Ratata", "error -> " + current_action.getTitle() + " is already in favourite list"));
                                    // break;
                                } else {
                                if (current_user.getHistory().containsKey(current_action.getTitle())) {
                                    current_user.getFavoriteMovies().add(current_action.getTitle());
                                    arrayResult.add(fileWriter.writeFile(current_action.getActionId(), "Ratata", "success -> " + current_action.getTitle() + " was added as favourite"));
                                } else {
                                    arrayResult.add(fileWriter.writeFile(current_action.getActionId(), "Ratata", "error -> " + current_action.getTitle() + " is not seen"));
                                }
                            }
                            break;
                        }
                    }
                }

                //View

                if (current_action.getType().equals("view")) {
                    for (int j = 0; j < input.getUsers().size(); j++) {
                        UserInputData current_user = input.getUsers().get(j);
                        if (current_user.getUsername().equals(current_action.getUsername())) {
                            if (current_user.getHistory().containsKey(current_action.getTitle())) {
                                current_user.increment_views(current_action.getTitle(), current_user.getHistory().get(current_action.getTitle()));
                            } else {
                                current_user.getHistory().put(current_action.getTitle(), 1);
                            }
                            arrayResult.add(fileWriter.writeFile(current_action.getActionId(), "Ratata", "success -> " + current_action.getTitle() + " was viewed with total views of " + current_user.getHistory().get(current_action.getTitle())));
                        }
                    }
                }

                //Rating

                if (current_action.getType().equals("rating")) {

                    if (current_action.getSeasonNumber() == 0) {
                        Rating rating = new Rating(current_action.getUsername(), current_action.getTitle(), current_action.getGrade(),
                                0, input.getMovies(), null, input.getUsers());
                        String message = rating.solve_rating();
                        arrayResult.add(fileWriter.writeFile(current_action.getActionId(), "Ratata", message));
                    } else {
                        Rating rating = new Rating(current_action.getUsername(), current_action.getTitle(), current_action.getGrade(),
                                current_action.getSeasonNumber(), null, input.getSerials(), input.getUsers());
                        String message = rating.solve_rating();
                        arrayResult.add(fileWriter.writeFile(current_action.getActionId(), "Ratata", message));
                    }
                }
            }

            if (current_action.getActionType().equals("query")) {

                // Actor

                if (current_action.getObjectType().equals("actors")) {

                    // Average

                    if (current_action.getCriteria().equals("average")) {
                        Average average = new Average(input.getActors(), input.getMovies(), input.getSerials(), current_action.getNumber(), current_action.getSortType());
                        String message = average.solve_average();
                        arrayResult.add(fileWriter.writeFile(current_action.getActionId(), "Ratata", "Query result: [" + message + "]"));
                    }

                    // Awards

                    if (current_action.getCriteria().equals("awards")) {
                        Awards awards = new Awards(input.getActors(), current_action.getFilters().get(3), current_action.getSortType());
                        String message = awards.solve_awards();
                        arrayResult.add(fileWriter.writeFile(current_action.getActionId(), "Ratata", "Query result: [" + message + "]"));
                    }

                    // Filter description

                    if (current_action.getCriteria().equals("filter_description")) {
                        Filter_description filter_description = new Filter_description(input.getActors(), current_action.getFilters().get(2));
                        List<String> name = filter_description.actors_name();
                        List<String> description  = filter_description.actors_description();
                        String message = filter_description.solve_filter_description(name, description);
                        arrayResult.add(fileWriter.writeFile(current_action.getActionId(), "Ratata", "Query result: [" + message + "]"));
                    }
                }

                // Favorite

                if (current_action.getCriteria().equals("favorite")) {
                    if (current_action.getObjectType().equals("movies")) {
                        Favorite_movie_serial favorite_movie = new Favorite_movie_serial(current_action.getFilters().get(0), current_action.getFilters().get(1), input.getMovies(), input.getUsers(), null, current_action.getNumber());
                        String message = favorite_movie.solve_favorite_movie_serial();
                        arrayResult.add(fileWriter.writeFile(current_action.getActionId(), "Ratata", "Query result: [" + message + "]"));
                    } else {
                        Favorite_movie_serial favorite_serial = new Favorite_movie_serial(current_action.getFilters().get(0), current_action.getFilters().get(1), null, input.getUsers(), input.getSerials(), current_action.getNumber());
                        String message = favorite_serial.solve_favorite_movie_serial();
                        arrayResult.add(fileWriter.writeFile(current_action.getActionId(), "Ratata", "Query result: [" + message + "]"));
                    }
                }

                // Longest

                if (current_action.getCriteria().equals("longest")) {
                    if (current_action.getObjectType().equals("movies")) {
                        Longest longest = new Longest(current_action.getFilters().get(0), current_action.getFilters().get(1), input.getMovies(), null, current_action.getNumber(), current_action.getSortType());
                        String message = longest.solve_longest();
                        arrayResult.add(fileWriter.writeFile(current_action.getActionId(), "Ratata", "Query result: [" + message + "]"));
                    } else {
                        Longest longest = new Longest(current_action.getFilters().get(0), current_action.getFilters().get(1), null, input.getSerials(), current_action.getNumber(), current_action.getSortType());
                        String message = longest.solve_longest();
                        arrayResult.add(fileWriter.writeFile(current_action.getActionId(), "Ratata", "Query result: [" + message + "]"));
                    }
                }

                // Most viewed

                if (current_action.getCriteria().equals("most_viewed")) {
                    if (current_action.getObjectType().equals("movies")) {
                        Most_viewed most_viewed = new Most_viewed(current_action.getFilters().get(0), current_action.getFilters().get(1), input.getMovies(), null, input.getUsers(), current_action.getNumber());
                        String message = most_viewed.solve_most_viewed();
                        arrayResult.add(fileWriter.writeFile(current_action.getActionId(), "Ratata", "Query result: [" + message + "]"));
                    } else {
                        Most_viewed most_viewed = new Most_viewed(current_action.getFilters().get(0), current_action.getFilters().get(1), null, input.getSerials(), input.getUsers(), current_action.getNumber());
                        String message = most_viewed.solve_most_viewed();
                        arrayResult.add(fileWriter.writeFile(current_action.getActionId(), "Ratata", "Query result: [" + message + "]"));
                    }
                }

                // Rating

                if (current_action.getCriteria().equals("ratings")) {
                    if (current_action.getObjectType().equals("movies")) {
                        Rating_video rating_video = new Rating_video(current_action.getFilters().get(0), current_action.getFilters().get(1), input.getMovies(), null, current_action.getNumber(), current_action.getSortType());
                        String message = rating_video.solve_rating_video();
                        arrayResult.add(fileWriter.writeFile(current_action.getActionId(), "Ratata", "Query result: [" + message + "]"));
                    } else {
                        Rating_video rating_video = new Rating_video(current_action.getFilters().get(0), current_action.getFilters().get(1), null, input.getSerials(), current_action.getNumber(), current_action.getSortType());
                        String message = rating_video.solve_rating_video();
                        arrayResult.add(fileWriter.writeFile(current_action.getActionId(), "Ratata", "Query result: [" + message + "]"));
                    }
                }

                // Number of ratings

                if (current_action.getCriteria().equals("num_ratings")) {
                    Number_of_ratings number_of_ratings = new Number_of_ratings(null, null, null, null, input.getUsers(), current_action.getNumber(), current_action.getSortType());
                    String message = number_of_ratings.solve_number_of_ratings();
                    arrayResult.add(fileWriter.writeFile(current_action.getActionId(), "Ratata", "Query result: [" + message + "]"));
                }
            }

        }




        // Aici se termina ce am scris

        fileWriter.closeJSON(arrayResult);
    }
}
