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
  
  //TO DO: eval, activation, output funtions
}
  
