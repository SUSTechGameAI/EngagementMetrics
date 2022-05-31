package agents.replay;

import agents.collector.AStarTree;
import engine.core.*;
import engine.helper.MarioActions;

import java.util.ArrayList;

/**
 * @author RobinBaumgarten
 */
public class Agent implements MarioAgent {
    private boolean[] action;
    private ArrayList<MarioAgentEvent> agentEvents;
    private int count;


    public Agent(ArrayList<MarioAgentEvent> agentEvents) {
        this.agentEvents = agentEvents;
        this.count = 0;
    }

    @Override
    public void initialize(MarioForwardModel model, MarioTimer timer) {
        this.action = new boolean[MarioActions.numberOfActions()];
    }

    @Override
    public boolean[] getActions(MarioForwardModel model, MarioTimer timer, ArrayList<MarioEvent> gameEvents) {
        action = this.agentEvents.get(count).getActions();
        count++;
        return action;
    }

    @Override
    public String getAgentName() {
        return "replay";
    }

    @Override
    public void getAgentRecord() {

    }

    @Override
    public int  getTotalState(){
        return -1;
    }

    @Override
    public int getLoseState(){
        return -1;
    };

}
