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
		VELOCITY = Math.max(VELOCITY - 1, MAX_VELOCITY);
	}
	
	
	public void update() {
		this.x += VELOCITY;
	}
	
	
	public boolean checkCollisionwDino(Dino dino) {
		return (this.x < dino.x + dino.width && this.x + this.width > dino.x &&
				this.y - this.height < dino.y && this.y > dino.y - dino.height);
	}
	
	
	public boolean isInvisible() {

	}
}
