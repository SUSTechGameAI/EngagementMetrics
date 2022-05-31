package ch.idsia.ai.agents;
import ch.idsia.mario.environments.Environment;

import java.util.ArrayList;

public class Behavior {
    private ArrayList<boolean[]> action = new ArrayList<>();
    private int totalStates;
    private int deathStates;

    public Behavior() {
        this.totalStates = 0;
        this.deathStates = 0;
    }

    public Behavior(boolean[] action) {
        this.action.set(0, action);
        this.totalStates = 0;
        this.deathStates = 0;
    }

    public int getTotalStates() {
        return totalStates;
    }

    public int getDeathStates() {
        return deathStates;
    }

    public boolean[] getAction() {
        return action.get(0);
    }

    public void setTotalStates(int totalStates) {
        this.totalStates = totalStates;
    }

    public void setDeathStates(int deathStates) {
        this.deathStates = deathStates;
    }

    public void addTotalStates(int totalStates) {
        this.totalStates +=totalStates;
    }

    public void addDeathStates(int deathStates) {
        this.deathStates += deathStates;
    }

    public void addAction(boolean[] action) {
        this.action.add(action);
    }

    public void clear() {
        this.action.clear();
        totalStates = 0;
        deathStates = 0;
    }
}
