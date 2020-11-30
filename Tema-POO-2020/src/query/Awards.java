package Query;

import actor.ActorsAwards;
import fileio.ActorInputData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


public class Awards {

    private final List<ActorInputData> actor;
    private final List<String> awards;
    private final String sortType;

    public Awards(final List<ActorInputData> actor, final List<String> awards,
                  final String sortType) {
        this.actor = actor;
        this.awards = awards;
        this.sortType = sortType;
    }

    /**
     * commm
     */
    public String solveAwards() {
        List<ActorInputData> actors = new ArrayList<>();
        for (ActorInputData actorInputData : actor) {
            boolean ok = true;
            for (String genre : awards) {
                if (!actorInputData.getAwards().containsKey(ActorsAwards.valueOf(genre))) {
                    ok = false;
                }
            }
            if (ok) {
                actors.add(actorInputData);
            }
        }

        List<Integer> noAwards = new LinkedList<>();
        for (int i = 0; i < actors.size(); i++) {
            noAwards.add(actors.get(i).getAwards().values().stream().reduce(0, Integer::sum));
        }

        for (int i = 0; i < actors.size() - 1; i++) {
            for (int j = i + 1; j < actors.size(); j++) {
                if ((noAwards.get(i) < noAwards.get(j))
                        || (noAwards.get(i).equals(noAwards.get(j))
                        && actors.get(i).getName().compareTo(actors.get(j).getName()) < 0)) {
                    int auxInt = noAwards.get(i);
                    noAwards.set(i, noAwards.get(j));
                    noAwards.set(j, auxInt);
                    ActorInputData auxStr = actors.get(i);
                    actors.set(i, actors.get(j));
                    actors.set(j, auxStr);
                }
            }
        }
        if (sortType.equals("asc")) {
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
