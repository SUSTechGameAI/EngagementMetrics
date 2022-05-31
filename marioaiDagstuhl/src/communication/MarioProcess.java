package communication;

import engine.core.MarioAgent;
import ch.idsia.ai.agents.AgentsPool;
import ch.idsia.ai.agents.human.HumanKeyboardAgent;
import ch.idsia.mario.engine.level.Level;
import ch.idsia.mario.engine.sprites.Mario;
import ch.idsia.mario.simulation.BasicSimulator;
import ch.idsia.mario.simulation.Simulation;
import ch.idsia.mario.simulation.SimulationOptions;
import ch.idsia.tools.CmdLineOptions;
import ch.idsia.tools.EvaluationInfo;
import ch.idsia.tools.EvaluationOptions;
import ch.idsia.tools.ToolsConfigurator;
import competition.cig.killer.AStarAgent;
import engine.core.MarioGame;
import engine.core.MarioResult;

public class MarioProcess extends Comm {
    private EvaluationOptions evaluationOptions;
    private Simulation simulator;
    private MarioGame game;
    private MarioAgent agent;

    public MarioProcess() {
        super();
        this.threadName = "MarioProcess";
    }

    public MarioProcess(MarioAgent agent) {
        super();
        this.agent = agent;
        this.threadName = "MarioProcess";
    }

    /**
     * Default mario launcher does not have any command line parameters
     */
    public void launchMario() {
    	String[] options = new String[] {""};
    	launchMario(options, false);
    }

 
    /**
     * This version of launching Mario allows for several parameters
     * @param options General command line options (currently not really used)
     * @param humanPlayer Whether a human is playing rather than a bot
     */
    public void launchMario(String[] options, boolean humanPlayer) {
        this.evaluationOptions = new CmdLineOptions(options);  // if none options mentioned, all defaults are used.
        // set agents
        createAgentsPool(humanPlayer);
        // Short time for evolution, but more for human
        if(!humanPlayer) evaluationOptions.setTimeLimit(100);
        // TODO: Make these configurable from commandline?
        evaluationOptions.setMaxFPS(!humanPlayer); // Slow for human players, fast otherwise
        evaluationOptions.setVisualization(true); // Set true to watch evaluations
        // Create Mario Component
        ToolsConfigurator.CreateMarioComponentFrame(evaluationOptions);
        evaluationOptions.setAgent(AgentsPool.getCurrentAgent());
        System.out.println(evaluationOptions.getAgent().getClass().getName());
        // set simulator
        this.simulator = new BasicSimulator(evaluationOptions.getSimulationOptionsCopy());
        this.game = new MarioGame();
    }

    /**
     * Set the agent that is evaluated in the evolved levels
     */
    public static void createAgentsPool(boolean humanPlayer)
    {
    	// Could still generalize this more
    	if(humanPlayer) {
    		AgentsPool.setCurrentAgent(new HumanKeyboardAgent());
        } else {
        	AgentsPool.setCurrentAgent(new AStarAgent());
        }
    }

    public void setLevel(Level level) {
        evaluationOptions.setLevel(level);
        this.simulator.setSimulationOptions(evaluationOptions);
    }

    /**
     * Simulate a given level
     * @return
     */
    public EvaluationInfo simulateOneLevel(Level level) {
        setLevel(level);
        EvaluationInfo info = this.simulator.simulateOneLevel();
        return info;
    }

    /**
     * Simulate a given level
     * @return
     */
    public MarioResult simulateOneLevel(String level) {

        MarioResult result = this.game.runGame(this.agent, level, 5, 0, true, true, 60);

        return result;
    }

    public MarioResult simulateOneLevel(String level, boolean visual) {

        MarioResult result = this.game.runGame(this.agent, level, 5, 0, visual, true, 60);

        return result;
    }

    public MarioResult simulateOneLevel(String level, boolean visual, int time) {

        MarioResult result = this.game.runGame(this.agent, level, 20, 0, visual, true, time);

        return result;
    }

    public EvaluationInfo simulateOneLevel() {
        evaluationOptions.setLevelFile("sample_1.json");
        EvaluationInfo info = this.simulator.simulateOneLevel();
        return info;
    }

    @Override
    public void start() {
        this.launchMario();
    }

    @Override
    public void initBuffers() {

    }
}
