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
}
