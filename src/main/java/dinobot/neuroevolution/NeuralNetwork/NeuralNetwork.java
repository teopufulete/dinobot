package dinobot.neuroevolution.NeuralNetwork;
import java.util.ArrayList;
import java.util.List;

public class NeuralNetwork {
	
	public class FlattenNetwork {
		public List<Integer> neurons;
		public List<Float> weights;
		
		public FlattenNetwork() {
			this.neurons = new ArrayList<Integer>();
			this.weights = new ArrayList<Float>();
		}
	}
	
	public List<Layer> layers;
	
	private NeuralNetwork() {
		this.layers = new ArrayList<Layer>();
	}
}
