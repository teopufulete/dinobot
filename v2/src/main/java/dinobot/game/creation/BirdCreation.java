package game.creation;
import game.model.BirdObstacle;
import game.model.Obstacle;
import game.model.BirdObstacle.BirdHeight;

public class BirdCreation {
	private static float spawnX;
	private static float spawnY;
	private static BirdHeight[] types = BirdHeight.values();
	
	private BirdCreation() {	
	}
	
	public static void init(float x, float y) {
		spawnX = x;
		spawnY = y;
	}
	
	public static Obstacle getBird() {
		return new BirdObstacle(spawnX, spawnY, types[(int) Math.round(Math.random()*(types.length-1))]);
	}
}
