package Query;

import actor.ActorsAwards;
import fileio.ActorInputData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Awards {

    private final List<ActorInputData> actor;
    private final List<String> genres;
    private final String sort_type;

    public Awards(List<ActorInputData> actor, List<String> genres, String sort_type) {
        this.actor = actor;
        this.genres = genres;
        this.sort_type = sort_type;
    }

    public String solve_awards() {
        List<ActorInputData> actors = new ArrayList<>();
        for (ActorInputData actorInputData : actor) {
            boolean ok = true;
            for (String genre : genres) {
                if (!actorInputData.getAwards().containsKey(ActorsAwards.valueOf(genre))) {
                    ok = false;
                }
            }
            if (ok) {
                actors.add(actorInputData);
            }
        }
        for (int j = 0; j < genres.size(); j++) {
            for (int k = 0; k < actors.size() - 1; k++) {
                for (int l = k + 1; l < actors.size(); l++) {
                    if (j == 0) {
                        if (actors.get(k).getAwards().get(ActorsAwards.valueOf(genres.get(j))) < actors.get(l).getAwards().get(ActorsAwards.valueOf(genres.get(j)))) {
                            ActorInputData aux = actors.get(k);
                            actors.set(k, actors.get(l));
                            actors.set(l, aux);
                        }
                    } else {
                        if (actors.get(k).getAwards().get(ActorsAwards.valueOf(genres.get(j - 1))).equals(actors.get(l).getAwards().get(ActorsAwards.valueOf(genres.get(j - 1))))
                                && actors.get(k).getAwards().get(ActorsAwards.valueOf(genres.get(j))) < actors.get(l).getAwards().get(ActorsAwards.valueOf(genres.get(j)))) {
                            ActorInputData aux = actors.get(k);
                            actors.set(k, actors.get(l));
                            actors.set(l, aux);
                        }
                    }
                }
            }
        }

        if (sort_type.equals("asc")) {
            Collections.reverse(actors);
        }
        StringBuilder message = new StringBuilder();
        boolean ok = false;
        for (ActorInputData actorInputData : actors) {
            if (ok) {
                message.append(", ").append(actorInputData.getName());
            } else {
                message.append(actorInputData.getName());
                ok = true;
            }
        }
        return message.toString();
    }
}
