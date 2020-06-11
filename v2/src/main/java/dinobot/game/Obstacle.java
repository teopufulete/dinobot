package dinobotv2.game.model;

public class Obstacle {
	
	public enum ObstacleType {
		CACTUS, BIRD;
	}
	
	public float x;
	public float y;
	public float width;
	public float height;
	public ObstacleType type;
	
	public static float VELOCITY = -10f;
	public static float MAX_VELOCITY = -50f;
	
	
	public Obstacle(float x, float y) {
		this.x =x;
		this.y = y;
	}
	
	
	public static void speedUp() {
		
	}
	
	
	public void update() {

	}
	
	
	public boolean checkCollisionwDino(Dino dino) {
	
	}
	
	
	public boolean isInvisible() {

	}
}
