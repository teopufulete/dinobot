package game;
import java.util.LinkedList;
import java.util.List;
import game.creation.DinoCreation;
import game.creation.ObstacleCreation;
import game.model.BirdObstacle;
import game.model.CactusObstacle;
import game.model.CactusObstacle.CactusType;
import game.model.Dino;
import game.model.Obstacle;
import game.model.Obstacle.ObstacleType;
import neuroevolution.genetic.GeneticAlgorithm;
import neuroevolution.genetic.Genotype;
import neuroevolution.neuralnetwork.Layer;
import neuroevolution.neuralnetwork.NeuralNetwork;
import neuroevolution.neuralnetwork.Neuron;
import processing.core.PApplet;
import processing.core.PImage;
import util.Screen;

public class DinoGame extends PApplet{
	List<Obstacle> obstacles;
	GeneticAlgorithm agent;
	
	int groundLevel;
	int tickCount = 0;
	int spawnRate = 140;
	int minSpawnRate = 80;
	int speedupRate = 1000;
	
	int score = 0;
	int highscore = 0;
	int speed = 1;
	int maxSpeed = 10;
	
	PImage dinoRun1Image;
	PImage dinoRun2Image;
	PImage dinoDuckImage;
	PImage dinoJumpImage;
	PImage cactusLargeImage;
	PImage birdImage;
	PImage birdSecondImage;
	PImage cactusMediumImage;
	PImage cactusSmallImage;
	PImage groundImage;
	
	
	public void settings() {
		size(1366, 768);
		Screen.setDimensions(width, height);
		this.groundLevel = height - 100;
		this.obstacles = new LinkedList<Obstacle>();
		DinoCreation.init(100, this.groundLevel);
		ObstacleCreation.init(0.3f, width, this.groundLevel);
		this.agent = new GeneticAlgorithm();
	}
	
	
	public void setup() {
		clearScreen();
		surface.setTitle("Neuroevolution Chrome Dino");
		cactusLargeImage = loadImage("sprites/cactusLarge.png");
		cactusMediumImage = loadImage("sprites/cactusMedium.png");
		cactusSmallImage = loadImage("sprites/cactusSmall.png");
		birdImage = loadImage("sprites/enemy1.png");
		groundImage = loadImage("sprites/ground.png");
		birdSecondImage = loadImage("sprites/enemy2.png");
		dinoRun1Image = loadImage("sprites/dinoRun1.png");
		dinoRun2Image = loadImage("sprites/dinoRun2.png");
		dinoJumpImage = loadImage("sprites/dinoJump.png");
		dinoDuckImage = loadImage("sprites/dinoDuck.png");
	}

	
	public void draw() {
		for (int i = 0; i < speed; i++) {
			this.tickCount++;
			this.clearScreen();
			this.drawGenerationInfo();
			this.renderGround();
			this.obstacles.forEach(obstacle -> this.renderObstacle(obstacle));
			this.agent.population.genomes.forEach(genome -> this.renderDino(genome.dino));
			this.renderNeuralNetwork(this.agent.getBestGenome().cactusNN, 850);
			this.renderNeuralNetwork(this.agent.getBestGenome().birdNN, 1100);
			this.obstacles.removeIf(obstacle -> obstacle.isInvisible());
			for (Genotype genome: this.agent.population.genomes) {
				for (Obstacle obstacle: this.obstacles) {
					this.checkCollision(obstacle, genome.dino);
				}
			}
			if (tickCount % spawnRate == 0) {
				this.spawnObstacle();
			}
			
			if (tickCount % speedupRate == 0) {
				Obstacle.speedUp();
			}
			this.obstacles.forEach(obstacle -> obstacle.update());
			this.agent.updatePopulation(obstacles);
			if (this.agent.populationDead()) {
				this.reset();
			}
		}
	}

	
	public void keyPressed() {
		if (key == CODED) {
			if (keyCode == UP) {
				speed = min(maxSpeed, ++speed);
			} 
			else if (keyCode == DOWN) {
				speed = max(1, --speed);
			}
		}
	}
	
	
	private void reset() {
		obstacles.clear();
		tickCount = 0;
		agent.evolvePopulation();
		score = 0;
		this.spawnRate = 140;
	}

	
	private void checkCollision(Obstacle obstacle, Dino dino) {
		if (!dino.isDead) {
			if (obstacle.checkCollisionwDino(dino)) {
				dino.isDead = true;
				this.agent.alive--;
			}
		}
	}
	
	
	private void spawnObstacle() {
		this.obstacles.add(ObstacleCreation.getObstacle());
	}
	
	
	private void renderDino(Dino dino) {
		if (!dino.isDead) {
			if (agent.alive > 1) {
				stroke(0);
				strokeWeight(1);
				fill(125, 125, 125, 50);
				rect(dino.x, dino.y-dino.height, dino.width, dino.height);
			}
			else {
				if (dino.isDucking) {
					image(dinoDuckImage, dino.x, dino.y-dino.height, dino.width, dino.height);
				} else if (dino.y < this.groundLevel) {
					image(dinoJumpImage, dino.x, dino.y-dino.height, dino.width, dino.height);
				} else if (dino.state == 0) {
					image(dinoRun1Image, dino.x, dino.y-dino.height, dino.width, dino.height);
				} else {
					image(dinoRun2Image, dino.x, dino.y-dino.height, dino.width, dino.height);
				}
			}
		}
	}
	
	
	private void renderObstacle(Obstacle obstacle) {
		if (obstacle.type == ObstacleType.BIRD) {
			this.renderObstacle((BirdObstacle) obstacle);
		} else {
			this.renderObstacle((CactusObstacle) obstacle);
		}
	}
	
	
	private void renderObstacle(BirdObstacle obstacle) {
		if (obstacle.state == 0) {
			image(birdImage, obstacle.x, obstacle.y - obstacle.height, obstacle.width, obstacle.height);
		} else {
			image(birdSecondImage, obstacle.x, obstacle.y - obstacle.height, obstacle.width, obstacle.height);
		}
	}
	
	
	private void renderObstacle(CactusObstacle obstacle) {
		if (obstacle.cactusType == CactusType.LARGE) {
			image(cactusLargeImage, obstacle.x, obstacle.y - obstacle.height, obstacle.width, obstacle.height);
		} else if (obstacle.cactusType == CactusType.MEDIUM) {
			image(cactusMediumImage, obstacle.x, obstacle.y - obstacle.height, obstacle.width, obstacle.height);
		} else {
			image(cactusSmallImage, obstacle.x, obstacle.y - obstacle.height, obstacle.width, obstacle.height);
		}
	}
}
