package neuroevolution.genetic;
import java.util.ArrayList;
import java.util.List;
import game.creation.DinoCreation;
import game.model.Dino;
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
	
	
	public Genotype(Genotype genome) {
		this.dino = DinoCreation.getDino(genome.dino.cactusNN.flatten(), genome.dino.birdNN.flatten());
		this.fitness = 0;
	}
	
	
	public static List<Genotype> breed(Genotype male, Genotype female, int childCount, float mutationRate, float mutationStdDev) {
		
		List<Genotype> children = new ArrayList<Genotype>();
		for (int ch = 0; ch < childCount; ch++) {
			FlattenNet childCactusNet = male.dino.cactusNN.flatten();
			FlattenNet childBirdNet = male.dino.birdNN.flatten();
			FlattenNet parentCactusNet = female.dino.cactusNN.flatten();
			FlattenNet parentBirdNet = female.dino.birdNN.flatten();
			
			childCactusNet = breedNeuralNetworks(childCactusNet, parentCactusNet, mutationRate, mutationStdDev);
			childBirdNet = breedNeuralNetworks(childBirdNet, parentBirdNet, mutationRate, mutationStdDev);
			children.add(new Genotype(childCactusNet, childBirdNet));
		}
		return children;
	}
}
