package dinobot.neuroevolution.NeuralNetwork;
import java.util.ArrayList;
import java.util.List;

public class Layer {
	public List<Neuron> neurons;
	
	
	private Layer() {
		this.neurons = new ArrayList<Neuron>();
	}
	
	
	public Layer(int numberOfNeurons, int inputs) {
		this();
		for (int i = 0; i < numberOfNeurons; i++) {
			this.neurons.add(new Neuron(inputs));
		}
	}
  

	public void evaluate(Layer prevLayer) {
		for (Neuron neuron: this.neurons) {
			float weightedSum = 0.0f;
			for (int i = 0; i < prevLayer.neurons.size(); i++) {
				Neuron prevNeuron = prevLayer.neurons.get(i);
				weightedSum += prevNeuron.value * neuron.weights.get(i);
			}
			neuron.value = this.sigmoidActivation(weightedSum);
		}
	}
	
	
	private float sigmoidActivation(float weightedSum) {
		return (float) (1/ (1) + Math.exp(-weightedSum));
	}
}
  
