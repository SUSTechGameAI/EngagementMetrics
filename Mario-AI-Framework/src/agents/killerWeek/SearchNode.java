package agents.killerWeek;

import engine.core.MarioForwardModel;
import engine.helper.GameStatus;

import java.util.ArrayList;

public class SearchNode {
    public int timeElapsed = 0;
    public float remainingTimeEstimated = 0;
    public float remainingTime = 0;

    public SearchNode parentPos = null;
    public MarioForwardModel sceneSnapshot = null;
    public int distanceFromOrigin = 0;
    public boolean hasBeenHurt = false;
    public boolean isInVisitedList = false;
    public boolean check = false;

    boolean[] action;
    int repetitions = 1;

    public float calcRemainingTime(float marioX, float marioXA) {
        return (100000 - (maxForwardMovement(marioXA, 1000) + marioX)) / Helper.maxMarioSpeed - 1000;
    }

    public float getRemainingTime() {
        if (remainingTime > 0)
            return remainingTime;
        else
            return remainingTimeEstimated;
    }

    public float estimateRemainingTimeChild(boolean[] action, int repetitions) {
        float[] childbehaviorDistanceAndSpeed = Helper.estimateMaximumForwardMovement(
                this.sceneSnapshot.getMarioFloatVelocity()[0], action, repetitions);
        return calcRemainingTime(this.sceneSnapshot.getMarioFloatPos()[0] + childbehaviorDistanceAndSpeed[0],
                childbehaviorDistanceAndSpeed[1]);
    }

    public SearchNode(boolean[] action, int repetitions, SearchNode parent) {
        this.parentPos = parent;
        if (parent != null) {
            this.remainingTimeEstimated = parent.estimateRemainingTimeChild(action, repetitions);
            this.distanceFromOrigin = parent.distanceFromOrigin + 1;
            this.sceneSnapshot = parentPos.sceneSnapshot.clone();
            for (int i = 0; i < repetitions; i++) {
                this.sceneSnapshot.advance(action);
            }
        }
        this.action = action;
        this.repetitions = repetitions;
        if (parent != null)
            timeElapsed = parent.timeElapsed + repetitions;
        else
            timeElapsed = 0;
    }

    public void initializeRoot(MarioForwardModel model) {
        if (this.parentPos == null) {
            this.sceneSnapshot = model.clone();
            this.remainingTimeEstimated = calcRemainingTime(model.getMarioFloatPos()[0], 0);
        }
    }

    public float simulatePos() {
//        this.sceneSnapshot = parentPos.sceneSnapshot.clone();
//        for (int i = 0; i < repetitions; i++) {
//            this.sceneSnapshot.advance(action);
//        }
        int marioDamage = Helper.getMarioDamage(this.sceneSnapshot, this.parentPos.sceneSnapshot);
        remainingTime =
                calcRemainingTime(this.sceneSnapshot.getMarioFloatPos()[0], this.sceneSnapshot.getMarioFloatVelocity()[0]) +
                        marioDamage * (1000000 - 100 * distanceFromOrigin);
        if (isInVisitedList)
            remainingTime += Helper.visitedListPenalty;
        hasBeenHurt = marioDamage != 0;

        return remainingTime;
    }

    public ArrayList<SearchNode> generateChildren() {
        ArrayList<SearchNode> list = new ArrayList<SearchNode>();
        ArrayList<boolean[]> possibleActions = Helper.createPossibleActions(this);
//        if (this.isLeafNode()) {
//            possibleActions.clear();
//        }
        for (boolean[] action : possibleActions) {
            list.add(new SearchNode(action, repetitions, this));
        }
        return list;
    }

    public boolean isLeafNode() {
        if (this.sceneSnapshot == null) {
            return false;
        }
        return this.sceneSnapshot.getGameStatus() != GameStatus.RUNNING;
    }

    public int getkilled() {
        if (this.sceneSnapshot == null) {
            return 0;
        }
        return this.sceneSnapshot.getKillsTotal();
    }

    public float getkillrate() {
        if (this.sceneSnapshot == null) {
            return 0;
        }
        if(this.sceneSnapshot.getTotalEnemies() == 0){
            return 0;
        }
        return (float) this.sceneSnapshot.getKillsTotal()/ (float) this.sceneSnapshot.getTotalEnemies();
    }

    public int getTotalEnemies() {
        if (this.sceneSnapshot == null) {
            return 0;
        }
        return this.sceneSnapshot.getTotalEnemies();
    }

    public float[] getEnemiesFloatPos() {
        if (this.sceneSnapshot == null) {
            return null;
        }

        return this.sceneSnapshot.getEnemiesFloatPos();
    }

    public float getMarioX() {
        if (this.sceneSnapshot == null) {
            return 0;
        }
        return this.sceneSnapshot.getMarioFloatPos()[0];
    }

    public float getMarioY() {
        if (this.sceneSnapshot == null) {
            return 0;
        }
        return this.sceneSnapshot.getMarioFloatPos()[1];
    }

    public float getCollectRate() {
        if (this.sceneSnapshot == null) {
            return 0;
        }
        if(this.sceneSnapshot.getTotalCoins() == 0){
            return 0;
        }
        return (float)this.sceneSnapshot.getNumCollectedCoins()/(float) this.sceneSnapshot.getTotalCoins();
    }

    public int ifWin() {
        if (this.sceneSnapshot == null) {
            return 0;
        }
        if(this.sceneSnapshot.getGameStatus() == GameStatus.WIN){
            return 1;
        }
        return 0;
    }

    public int ifLose() {
        if (this.sceneSnapshot == null) {
            return 0;
        }
        if(this.sceneSnapshot.getGameStatus() == GameStatus.LOSE){
            return 1;
        }
        return 0;
    }

    private float maxForwardMovement(float initialSpeed, int ticks) {
        float y = ticks;
        float s0 = initialSpeed;
        return (float) (99.17355373 * Math.pow(0.89, y + 1) - 9.090909091 * s0 * Math.pow(0.89, y + 1) + 10.90909091 * y
                - 88.26446282 + 9.090909091 * s0);
    }

}
