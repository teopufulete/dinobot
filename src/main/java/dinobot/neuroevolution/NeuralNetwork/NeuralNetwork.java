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
	
	
	public static NeuralNetwork expand(FlattenNet net) {
		NeuralNetwork nn = new NeuralNetwork();
		int prevInput = 0;
		int weightIndex = 0;
		
		for (int neuronCount: net.neurons) {
			Layer layer = new Layer(neuronCount, prevInput);
			
			for (int i = 0; i < layer.neurons.size(); i++) {
				
				for (int j = 0; j < layer.neurons.get(i).weights.size(); j++) {
					layer.neurons.get(i).weights.set(j, net.weights.get(weightIndex++));
				}
			}
			prevInput = neuronCount;
			nn.layers.add(layer);
		}
		return nn;
	}
	
	
	public float[] evaluate(float... inputs) {
		for (int i = 0; i < inputs.length; i++) {
			this.layers.get(0).neurons.get(i).value = inputs[i];	
		}
		
		Layer prevLayer = this.layers.get(0);
		for (int i = 1; i < this.layers.size(); i++) {
			this.layers.get(i).evaluate(prevLayer);
			prevLayer = this.layers.get(i);
		}
		// prev layer is now the last layer in the network
				return prevLayer.getOutput();
	}
}
