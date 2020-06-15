package game.creation;
import game.model.Obstacle;

public class ObstacleCreation {
private static float birdObstacleChance;
	
	private ObstacleCreation() {
		
	}
	
	
	public static void init(float birdChance, float x, float y) {
		birdObstacleChance = birdChance;
		BirdCreation.init(x, y);
		CactusCreation.init(x, y);
	}
	
	
	public static Obstacle getObstacle() {
		if (Math.random() < birdObstacleChance) {
			return BirdCreation.getBird();
		} 
		else {
			return CactusCreation.getCactus();
		}
	}
}
