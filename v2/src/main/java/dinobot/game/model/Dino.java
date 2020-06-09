package dinobotv2.game.model;
import dinobotv2.neuroevolution.neuralnetwork.NeuralNetwork;
import dinobotv2.neuroevolution.neuralnetwork.NeuralNetwork.FlattenNet;

public class Dino {
	public float x;
	public float y;
	public float width = 60;
	public float height = 120;
	public float velocity = 0.6f;
	public float gravity = 0f;
	public float jumpForce = -16f;
	public boolean isDead = false;
	public float groundLevel;
	public int score = 0;
	public boolean isDucking = false;
	public NeuralNetwork cactusNN;
	public NeuralNetwork birdNN;
	public int state = 0;
	
	private int JUMP = 0;
	private int DUCK = 1;
	private int DO_NOTHING = 2;
	private int counter = 3;
	
	
	public Dino(float x, float y) {
		this.x = x;
		this.y = y;
		this.groundLevel = y;
		this.cactusNN = new NeuralNetwork(5, 8, 2);
		this.birdNN = new NeuralNetwork(6, 10, 3);
	}
	
	
	public Dino(float x, float y, FlattenNet cNet, FlattenNet bNet) {
		this.x = x;
		this.y = y;
		this.groundLevel = y;
		this.cactusNN = NeuralNetwork.expand(cNet);
		this.birdNN = NeuralNetwork.expand(bNet);
	}
	
	
	public void jump() {
		if (isDucking) {
			this.standUp();
		}
		if ((this.y >= this.groundLevel)) {
			this.gravity = this.jumpForce;	
		}
	}
	
	
	public void standUp() {
		if (this.isDucking) {
			float temp = this.width;
			this.width = this.height;
			this.height = temp;
			this.isDucking = false;	
		}
	}
	
	
	public void duck() {
		if (this.y >= this.groundLevel && !this.isDucking) {
			float temp = this.width;
			this.width = this.height;
			this.height = temp;
			this.isDucking = true;
		}
	}
	
	
	public void update() {
		this.score++;
		this.counter--;
		if (this.counter <= 0) {
			this.state ^= 1;
			this.counter = 3;
		}
		this.gravity += this.velocity;
		this.y = Math.min(this.y + this.gravity, this.groundLevel);
	}
}
