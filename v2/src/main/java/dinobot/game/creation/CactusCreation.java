package game.creation;
import game.model.CactusObstacle;
import game.model.Obstacle;
import game.model.CactusObstacle.CactusType;

public class CactusCreation {
  	private static float spawnX;
	private static float spawnY;
	private static CactusType[] types = CactusType.values();
	
	private CactusCreation() {
	}
	
	public static void init(float x, float y) {
		spawnX = x;
		spawnY = y;
	}
	
	public static Obstacle getCactus() {
		return new CactusObstacle(spawnX, spawnY, types[(int) Math.round(Math.random()*(types.length-1))]);
	}
}
