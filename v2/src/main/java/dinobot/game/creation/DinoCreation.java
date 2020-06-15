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
  
	
	public static Dino getDino() {
		return new Dino(spawnX, spawnY);
	}
	
	
	public static Dino getDino(FlattenNet cNet, FlattenNet bNet) {
		return new Dino(spawnX, spawnY, cNet, bNet);
	}
}
