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
	
	
	public NeuralNetwork(int... dimensions) {
		this();
		int prevInputs = 0;
		for (int i = 1; i < dimensions.length; i++) {
			this.layers.add(new Layer(dimensions[i], prevInputs));
			prevInputs = dimensions[i];
		}
	}
	
	
	public FlattenNet flatten() {
		FlattenNet net = new FlattenNet();
		
		for (Layer layer: this.layers) {
			net.neurons.add(layer.neurons.size());
			
			for (Neuron neuron: layer.neurons) {
				
				for (float weight: neuron.weights) {
					net.weights.add(weight);
				}
			}
		}
		return net;
	}
}
