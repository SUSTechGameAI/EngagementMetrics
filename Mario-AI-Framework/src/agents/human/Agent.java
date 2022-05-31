package agents.human;

import engine.core.MarioAgent;
import engine.core.MarioEvent;
import engine.core.MarioForwardModel;
import engine.core.MarioTimer;
import engine.helper.EventType;
import engine.helper.MarioActions;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Agent extends KeyAdapter implements MarioAgent {
    private boolean[] actions = null;

    @Override
    public void initialize(MarioForwardModel model, MarioTimer timer) {
        actions = new boolean[MarioActions.numberOfActions()];
    }

    @Override
    public boolean[] getActions(MarioForwardModel model, MarioTimer timer, ArrayList<MarioEvent> gameEvents) {

        return actions;
    }

    @Override
    public String getAgentName() {
        return "HumanAgent";
    }

    @Override
    public void getAgentRecord() {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        toggleKey(e.getKeyCode(), true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        toggleKey(e.getKeyCode(), false);
    }

    private void toggleKey(int keyCode, boolean isPressed) {
        if (this.actions == null) {
            return;
        }
        switch (keyCode) {
            case KeyEvent.VK_LEFT:
                this.actions[MarioActions.LEFT.getValue()] = isPressed;
                break;
            case KeyEvent.VK_RIGHT:
                this.actions[MarioActions.RIGHT.getValue()] = isPressed;
                break;
            case KeyEvent.VK_DOWN:
                this.actions[MarioActions.DOWN.getValue()] = isPressed;
                break;
            case KeyEvent.VK_S:
                this.actions[MarioActions.JUMP.getValue()] = isPressed;
                break;
            case KeyEvent.VK_A:
                this.actions[MarioActions.SPEED.getValue()] = isPressed;
                break;
        }
    }

    @Override
    public int  getTotalState(){
        return 0;
    }

    @Override
    public int getLoseState(){
        return 0;
    };

}
