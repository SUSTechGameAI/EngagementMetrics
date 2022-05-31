package engine.core;

import java.util.ArrayList;

/**
 * Interface for agents that want to play in the framework
 *
 * @author AhmedKhalifa
 */
public interface MarioAgent {

    /**
     * initialize and prepare the agent before the game starts
     *
     * @param model a forward model object so the agent can simulate or initialize some parameters based on it.
     * @param timer amount of time before the agent has to return
     */
    void initialize(MarioForwardModel model, MarioTimer timer);

    /**
     * get mario current actions
     *
     * @param model a forward model object so the agent can simulate the future.
     * @param timer amount of time before the agent has to return the actions.
     * @return an array of the state of the buttons on the controller
     */
    boolean[] getActions(MarioForwardModel model, MarioTimer timer, ArrayList<MarioEvent> gameEvents);

    /**
     * Return the name of the agent that will be displayed in debug purposes
     *
     * @return
     */
    String getAgentName();

    /**
     * Print some result after Game finish.
     *
     */
    void getAgentRecord();

    /**
     * get total state
     *
     */
    int getTotalState();

    /**
     * get lose state
     *
     */
    int getLoseState();
}
