package query;

import fileio.ActorInputData;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Filter_description {
    private final List<ActorInputData> actors;
    private final List<String> keywords;
    private final String sort_type;

    public Filter_description(List<ActorInputData> actors, List<String> keywords,
                              String sort_type) {
        this.actors = actors;
        this.keywords = keywords;
        this.sort_type = sort_type;
    }

    public List<String> actors_name () {
        List<String> name = new LinkedList<>();
        for (ActorInputData actor : actors) {
            name.add(actor.getName());
        }
        return name;
    }

    public List<String> actors_description() {
        List<String> description = new LinkedList<>();
        for (ActorInputData actor : actors) {
            description.add(actor.getCareerDescription());
        }
        return description;
    }

    public String solve_filter_description(List<String> name, List<String> description) {
        List<String> searched_actors = new LinkedList<>();
        for (int i = 0; i < name.size(); i++) {
            description.set(i, description.get(i).replace(",", " "));
            description.set(i, description.get(i).replace("-", " "));
            description.set(i, description.get(i).replace(".", " "));
            description.set(i, description.get(i).toLowerCase());
            boolean ok = true;
            for (String keyword : keywords) {
                if (!Arrays.asList(description.get(i).split(" ")).contains(keyword)) {
                    ok = false;
                    break;
                }
            }
            if (ok) {
                searched_actors.add(name.get(i));
            }
        }
        Collections.sort(searched_actors);
        if (sort_type.equals("desc")) {
            Collections.reverse(searched_actors);
        }
        StringBuilder message = new StringBuilder();
        boolean ok = false;
        for (String searched_actor : searched_actors) {
            if (ok) {
                message.append(", ").append(searched_actor);
            } else {
                message.append(searched_actor);
                ok = true;
            }
        }
        return message.toString();
    }
}
