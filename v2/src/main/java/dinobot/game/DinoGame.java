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
				} 
				else if (dino.y < this.groundLevel) {
					image(dinoJumpImage, dino.x, dino.y-dino.height, dino.width, dino.height);
				} 
				else if (dino.state == 0) {
					image(dinoRun1Image, dino.x, dino.y-dino.height, dino.width, dino.height);
				} 
				else {
					image(dinoRun2Image, dino.x, dino.y-dino.height, dino.width, dino.height);
				}
			}
		}
	}
	
	
	private void renderObstacle(Obstacle obstacle) {
		if (obstacle.type == ObstacleType.BIRD) {
			this.renderObstacle((BirdObstacle) obstacle);
		} 
		else {
			this.renderObstacle((CactusObstacle) obstacle);
		}
	}
	
	
	private void renderObstacle(BirdObstacle obstacle) {
		if (obstacle.state == 0) {
			image(birdImage, obstacle.x, obstacle.y - obstacle.height, obstacle.width, obstacle.height);
		} 
		else {
			image(birdSecondImage, obstacle.x, obstacle.y - obstacle.height, obstacle.width, obstacle.height);
		}
	}
	
	
	private void renderObstacle(CactusObstacle obstacle) {
		if (obstacle.cactusType == CactusType.LARGE) {
			image(cactusLargeImage, obstacle.x, obstacle.y - obstacle.height, obstacle.width, obstacle.height);
		} 
		else if (obstacle.cactusType == CactusType.MEDIUM) {
			image(cactusMediumImage, obstacle.x, obstacle.y - obstacle.height, obstacle.width, obstacle.height);
		} 
		else {
			image(cactusSmallImage, obstacle.x, obstacle.y - obstacle.height, obstacle.width, obstacle.height);
		}
	}
	
	
	private void drawGenerationInfo() {
		score = this.agent.getBestScore();
		highscore = Math.max(score, highscore);
		textSize(22);
		fill(0);
		text("Score: " + score, 20, 50);
		text("Generation: " + agent.generation, 20, 80);
		text("Alive: " + agent.alive + " / " + agent.populationSize, 20, 110);
		text("Highscore: " + highscore, 20, 140);
		text("Speed: " + speed + "x", 20, 170);
	}
	
	
	private void renderNeuralNetwork(NeuralNetwork net, float beginx) {
		int beginy = 0;
		int yspan = 300;
		int layerSpace = 80;
		int neuronSpace = 10;
		int layerWidth = 15;
		ellipseMode(CENTER);
		
		for (int i = 1; i < net.layers.size(); i++) {
			Layer prevLayer = net.layers.get(i - 1);
			Layer layer = net.layers.get(i);
			int totalLayerHeight = layer.neurons.size() * layerWidth + (layer.neurons.size() - 1) * neuronSpace;
			int layerBegin = beginy + (yspan - totalLayerHeight) / 2;
			int totalPrevLayerHeight = prevLayer.neurons.size() * layerWidth + (prevLayer.neurons.size() - 1) * neuronSpace;
			int prevLayerBegin = beginy + (yspan - totalPrevLayerHeight) / 2;
		
			// Draw current layer
			for (int j = 0; j < layer.neurons.size(); j++) {
				
				// Draw weights for each neuron
				Neuron neuron = layer.neurons.get(j);
				for (int k = 0; k < neuron.getWeights().size(); k++) {
					float weight = neuron.getWeights().get(k);
					strokeWeight(2 * Math.abs(weight));
					if (weight >= 0) {
						stroke(0, 0, 255);
					} 
					else {
						stroke(255, 0, 0);
					}
					line(beginx + (i-1) * (layerWidth+layerSpace), prevLayerBegin + k * (layerWidth + neuronSpace), beginx + i * (layerWidth+layerSpace), layerBegin + j * (neuronSpace + layerWidth));
				}
				fill(255);
				strokeWeight(1);
				stroke(0);
				ellipse(beginx + i * (layerWidth+layerSpace), layerBegin + j * (neuronSpace + layerWidth), layerWidth, layerWidth);
			}
			
			//Draw previous layer
			for (int j = 0; j < prevLayer.neurons.size(); j++) {
				fill(255);
				strokeWeight(1);
				stroke(0);
				ellipse(beginx + (i-1) * (layerWidth+layerSpace), prevLayerBegin + j * (neuronSpace + layerWidth), layerWidth, layerWidth);
			}
		}
	}
	
	
	private void renderGround() {
		image(groundImage, 0, this.groundLevel-10, width, 20);
	}
	
	
	private void clearScreen() {
		background(255);
	}	
}
