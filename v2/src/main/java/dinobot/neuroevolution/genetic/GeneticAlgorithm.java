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
	
	public Population population;
	public int alive;
	public int generation;
	
	public int populationSize = 100;
	public float elitism = 0.2f;
	public float mutationRate = 0.1f;
	public float mutationStdDev = 0.5f;
	public float randomness = 0.2f;
	public int childCount = 1;
	
	private Dino bestGenome;
	
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


