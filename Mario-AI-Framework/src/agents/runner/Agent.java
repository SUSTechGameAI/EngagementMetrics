package agents.runner;

import engine.core.MarioAgent;
import engine.core.MarioEvent;
import engine.core.MarioForwardModel;
import engine.core.MarioTimer;
import engine.helper.MarioActions;

import java.util.ArrayList;

/**
 * @author RobinBaumgarten
 */
public class Agent implements MarioAgent {
    private boolean[] action;
    private AStarTree tree;

    @Override
    public void initialize(MarioForwardModel model, MarioTimer timer) {
        this.action = new boolean[MarioActions.numberOfActions()];
        this.tree = new AStarTree();
    }

    @Override
    public boolean[] getActions(MarioForwardModel model, MarioTimer timer, ArrayList<MarioEvent> gameEvents) {
        action = this.tree.optimise(model, timer);
        return action;
    }

    @Override
    public String getAgentName() {
        return "RobinBaumgartenAgent";
    }

    @Override
    public void getAgentRecord() {
        System.out.println("============Robin Result===========");
        System.out.println("Searched Status: " + tree.SearchedStates);
        System.out.println("Lose Status: " + tree.SearchedLose);
        System.out.println("Fail rate: " + (int)100*(float)tree.SearchedLose/(float)tree.SearchedStates + "%");
    }

    @Override
    public int  getTotalState(){
        return tree.SearchedStates;
    }

    @Override
    public int getLoseState(){
        return tree.SearchedLose;
    };
}