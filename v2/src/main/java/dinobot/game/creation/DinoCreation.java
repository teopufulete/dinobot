package game.creation;
import game.model.Dino;
import neuroevolution.neuralnetwork.NeuralNetwork.FlattenNet;

public class DinoCreation {
	private static float spawnX;
	private static float spawnY;
	
	
	private DinoCreation() {
	}
	
	
	public static void init(float x, float y) {
		spawnX = x;
		spawnY = y;
	}
  
}
