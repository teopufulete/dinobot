package neuroevolution.genetic;
import java.util.ArrayList;
import java.util.List;
import game.creation.DinoCreation;
import game.model.Dino;
import neuroevolution.neuralnetwork.NeuralNetwork;
import neuroevolution.neuralnetwork.NeuralNetwork.FlattenNet;

public class Genotype {
	public Dino dino;
	public float fitness;
	
	public Genotype() {
		this.dino = DinoCreation.getDino();
		this.fitness = 0;
	}
	
	public Genotype(FlattenNet cNet, FlattenNet bNet) {
		this.dino = DinoCreation.getDino(cNet, bNet);
		this.fitness = 0;
	}
}
