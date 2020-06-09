package dinobot.neuroevolution.NeuralNetwork;
import java.util.ArrayList;
import java.util.List;

public class Neuron {
	float value;
	List<Float> weights;
	
	public Neuron(int numberOfWeights) {
		this.value = 0;
		this.weights = new ArrayList<Float>();
		for (int i = 0; i < numberOfWeights; i++) {
			this.weights.add((float) (Math.random() * 2 - 1));
		}
	}
}
