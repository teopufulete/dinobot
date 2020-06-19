package neuroevolution.genetic;
import java.util.List;
import game.creation.DinoCreation;
import game.model.Dino;
import game.model.Obstacle;

public class GeneticAlgorithm {
  	public class ObstacleInfo {
	  	public float distance;
		public Obstacle closestObstacle;
		
		public ObstacleInfo(float distance, Obstacle closestObstacle) {
			this.distance = distance;
			this.closestObstacle =closestObstacle;
		}
	}
	
	public GeneticAlgorithm() {
	}
	
	public void updatePopulation(List<Obstacle> obstacles) {
	}
	
	public void evolvePopulation() {
	}
	
	public Dino getBestGenome() {
	}
	
	public int getBestScore() {
	}
	
	public boolean populationDead() {
	}
	
	private ObstacleInfo getClosestObstacle(List<Obstacle> obstacles) {
	}
}


