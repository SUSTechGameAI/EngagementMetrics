package viewer;

import basicMap.Settings;
import cmatest.MarioEvalFunction;
import engine.core.MarioGame;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

/**
 * This file allows you to generate a level and play it for any latent vector
 * or your choice. The vector must have a length of 32 numbers separated
 * by commas enclosed in square brackets [ ]. For example,
 * [0.9881835842209917, -0.9986077315374948, 0.9995512051242508, 0.9998643432807639, -0.9976165917284504, -0.9995247114230822, -0.9997001909358728, 0.9995694511739592, -0.9431036754879115, 0.9998155541290887, 0.9997863689962382, -0.8761392912669269, -0.999843833016589, 0.9993230720045649, 0.9995470247917402, -0.9998847606084427, -0.9998322053148382, 0.9997707200294411, -0.9998905141832997, -0.9999512510490688, -0.9533512808031753, 0.9997703088007039, -0.9992229823819915, 0.9953917828622341, 0.9973473366437476, 0.9943030781608361, 0.9995290290713732, -0.9994945079679955, 0.9997109900652238, -0.9988379572928884, 0.9995070647543864, 0.9994132207570211]
 * 
 * Additionally, if you send in a 2D array where each sub-array has length 32, this will be interpreted as several
 * latent vectors. Each latent vector will create a separate level segment, and the segments will be stitched together
 * into one level for you to play.
 * 
 */

public class MarioAIframeworktester {

	public static String getLevel(String filepath) {
		String content = "";
		try {
			content = new String(Files.readAllBytes(Paths.get(filepath)));
		} catch (IOException e) {
		}
		return content;
	}

	public static double[] randomGaussianDoubleArray(int dim) {
		Random rdm = new Random();
		double[] array = new double[dim];
		for (int i=0; i<dim; i++) {
			array[i] = rdm.nextGaussian();
		}
		return array;
	}

	public static void main(String[] args) throws IOException {
		Settings.setPythonProgram();
		// This is used because it contains code for communicating with the GAN
		MarioEvalFunction eval = new MarioEvalFunction();

		String level;
		level = eval.MarioAIlevelFromLatentVector(new double[] {0.9881835842209917, -0.9986077315374948, 0.9995512051242508, 0.9998643432807639, -0.9976165917284504, -0.9995247114230822, -0.9997001909358728, 0.9995694511739592, -0.9431036754879115, 0.9998155541290887, 0.9997863689962382, -0.8761392912669269, -0.999843833016589, 0.9993230720045649, 0.9995470247917402, -0.9998847606084427, -0.9998322053148382, 0.9997707200294411, -0.9998905141832997, -0.9999512510490688, -0.9533512808031753, 0.9997703088007039, -0.9992229823819915, 0.9953917828622341, 0.9973473366437476, 0.9943030781608361, 0.9995290290713732, -0.9994945079679955, 0.9997109900652238, -0.9988379572928884, 0.9995070647543864, 0.9994132207570211});

		MarioGame game = new MarioGame();
		game.runGame(new agents.collector.Agent(), level, 90, 0, true, false);

		eval.exit();

		System.exit(0);
	}
}
