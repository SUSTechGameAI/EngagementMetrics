import engine.core.MarioGame;
import engine.core.MarioResult;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PlayLevel {
    public static void printResults(MarioResult result) {
        System.out.println("****************************************************************");
        System.out.println("Game Status: " + result.getGameStatus().toString() +
                " Percentage Completion: " + result.getCompletionPercentage());
        System.out.println("Lives: " + result.getCurrentLives() + " Coins: " + result.getCurrentCoins() +
                " Remaining Time: " + (int) Math.ceil(result.getRemainingTime() / 1000f));
        System.out.println("Mario State: " + result.getMarioMode() +
                " (Mushrooms: " + result.getNumCollectedMushrooms() + " Fire Flowers: " + result.getNumCollectedFireflower() + ")");
        System.out.println("Total Kills: " + result.getKillsTotal() + " (Stomps: " + result.getKillsByStomp() +
                " Fireballs: " + result.getKillsByFire() + " Shells: " + result.getKillsByShell() +
                " Falls: " + result.getKillsByFall() + ")");
        System.out.println("Bricks: " + result.getNumDestroyedBricks() + " Jumps: " + result.getNumJumps() +
                " Max X Jump: " + result.getMaxXJump() + " Max Air Time: " + result.getMaxJumpAirTime());
        System.out.println("****************************************************************");
    }

    public static String getLevel(String filepath) {
        String content = "";
        try {
            content = new String(Files.readAllBytes(Paths.get(filepath)));
        } catch (IOException e) {
        }
        return content;
    }

    public static void repeatKiller(int times) {
       String part_filepath = "./Mario-AI-Framework/levels/original/lvl-";
       for(int j=1; j<=15; j++) {
           String full_filepath = part_filepath + j + ".txt";
           float completion = 0;
           float total_kill = 0;
           float fall_kill = 0;
           float coins = 0;
           for (int i = 0; i < times; i++) {
               MarioGame game = new MarioGame();
               // printResults(game.playGame(getLevel("../levels/original/lvl-1.txt"), 200, 0));
               MarioResult result = game.runGame(new agents.killer.Agent(), getLevel(full_filepath), 50, 0, false);
               completion += result.getCompletionPercentage();
               total_kill += result.getKillsTotal();
               fall_kill += result.getKillsByFall();
               coins += result.getCurrentCoins();
           }
           completion /= times;
           total_kill /= times;
           fall_kill /= times;
           coins /= times;
           System.out.println("========level " + j +"=======");
           System.out.println("completion : " + completion);
           System.out.println("total_kill : " + total_kill);
           System.out.println("kill : " + (total_kill - fall_kill));
           System.out.println("coins : " + coins);
       }
    }

    public static void repeatCollertor(int times) {
        String part_filepath = "./Mario-AI-Framework/levels/original/lvl-";
        for(int j=1; j<=15; j++) {
            String full_filepath = part_filepath + j + ".txt";
            float completion = 0;
            float total_kill = 0;
            float fall_kill = 0;
            float coins = 0;
            for (int i = 0; i < times; i++) {
                MarioGame game = new MarioGame();
                // printResults(game.playGame(getLevel("../levels/original/lvl-1.txt"), 200, 0));
                MarioResult result = game.runGame(new agents.collector.Agent(), getLevel(full_filepath), 50, 0, false);
                completion += result.getCompletionPercentage();
                total_kill += result.getKillsTotal();
                fall_kill += result.getKillsByFall();
                coins += result.getCurrentCoins();
            }
            completion /= times;
            total_kill /= times;
            fall_kill /= times;
            coins /= times;
            System.out.println("========level " + j +"=======");
            System.out.println("completion : " + completion);
            System.out.println("total_kill : " + total_kill);
            System.out.println("kill : " + (total_kill - fall_kill));
            System.out.println("coins : " + coins);
        }
    }

    public static void repeatRobin(int times) {
        String part_filepath = "./Mario-AI-Framework/levels/original/lvl-";
        for(int j=1; j<=15; j++) {
            String full_filepath = part_filepath + j + ".txt";
            float completion = 0;
            float total_kill = 0;
            float fall_kill = 0;
            float coins = 0;
            for (int i = 0; i < times; i++) {
                MarioGame game = new MarioGame();
                // printResults(game.playGame(getLevel("../levels/original/lvl-1.txt"), 200, 0));
                MarioResult result = game.runGame(new agents.runner.Agent(), getLevel(full_filepath), 50, 0, false);
                completion += result.getCompletionPercentage();
                total_kill += result.getKillsTotal();
                fall_kill += result.getKillsByFall();
                coins += result.getCurrentCoins();
            }
            completion /= times;
            total_kill /= times;
            fall_kill /= times;
            coins /= times;
            System.out.println("========level " + j +"=======");
            System.out.println("completion : " + completion);
            System.out.println("total_kill : " + total_kill);
            System.out.println("kill : " + (total_kill - fall_kill));
            System.out.println("coins : " + coins);
        }
    }

    public static void main(String[] args) {
        MarioGame game = new MarioGame();
        printResults(game.runGame(new agents.collectorWeek.Agent(), getLevel("./Mario-AI-Framework/levels/original/lvl-1.txt"), 50, 0, true));
//        repeatCollertor(5);
//        repeatKiller(5);
//        repeatRobin(5);
    }
}
