package uhk.lab.neural.support.trainer;

import uhk.lab.dataprecompute.DataPrecompute;
import uhk.lab.encapsulation.dataset.DataSet;
import uhk.lab.neural.core.structure.NeuralNetwork;
import uhk.lab.neural.main.datagenerator.TrainingData;
import uhk.lab.neural.main.datagenerator.TrainingDataGenerator;
import uhk.lab.neural.core.structure.Layer;
import uhk.lab.neural.core.structure.Neuron;
import uhk.lab.neural.extend.structure.neurons.NeuronCluster;

/**
 * Created by Tob on 17. 2. 2016.
 */
public class MFArtMapTrainer implements Trainer {

    NeuralNetwork neuralNetwork;
    Layer recognitionLayer;
    Layer inputLayer;
    Layer outputLayer;
    double threshold;
    double E;
    double F;

    public MFArtMapTrainer(NeuralNetwork neuralNetwork, double threshold, double E, double F) {
        this.neuralNetwork = neuralNetwork;
        inputLayer = neuralNetwork.getLayers().get(0);
        recognitionLayer = neuralNetwork.getLayers().get(1);
        outputLayer = neuralNetwork.getLayers().get(2);
        this.threshold = threshold;
        this.E = E;
        this.F = F;
    }

    public double trainBack(TrainingDataGenerator generator, int maxRuns) {

        double currentEpoch = 0;
        double error;
        double sum = 0.0;
        double average = 25;
        int epoch = 1;
        int samples = 25;
        double[] errors = new double[samples];

        do {
            TrainingData trainingData = generator.getTrainingData();

            train(trainingData.getInputs(), DataPrecompute.getColumn(trainingData.getOutputs(),0));

            // System.out.println("Error for epoch " + epoch + ": " + error + ". Average: " + average + (characteristicTime > 0 ? " Learning rate: " + learningRate / (1 + (currentEpoch / characteristicTime)): ""));
            epoch++;
            currentEpoch = epoch;
        } while(currentEpoch < maxRuns);//while(average > errorThreshold && currentEpoch < maxRuns);
        return 0;
    }

    // sufflovat

    public void train(DataSet dataSet) {
        train(dataSet.getInput().getData(), DataPrecompute.getColumn(dataSet.getOutput().getData(),0));
    }

    public void train(double[][] inputs, double[] outputs) {
        for(int i = 0; i < inputs.length; i++) {

            //System.out.println("Training:" + inputs.length);

            double classType = outputs[i];
            unfreezeClusters();
            neuralNetwork.setInput(inputs[i]);

            recognitionLayer.activate();

            NeuronCluster bestCluster = null;

            // zmrazime vsechny clustery, ktere maji jinou tridu nebo je jejich a mensi

            freezeClusters(classType);

            // najdeme nejlepsi vysledek

            bestCluster = findBestCluster();

            // pokud zadny neexistuje, vytvorime novy cluster, pokud ano, upravime ten nejlepsi

            //System.out.println("aaa");

            if(bestCluster != null) {
                bestCluster.updateCluster();
                System.out.println("Updating cluster");
            } else {
                recognitionLayer.addNeuron(createNewCluster(classType, inputs[i]));
                System.out.println("Creating cluster");
            }
        }
    }

    public void unfreezeClusters() {
        for(int i = 0; i < recognitionLayer.getNeurons().size(); i++) {
            NeuronCluster cluster = (NeuronCluster) recognitionLayer.getNeurons().get(i);
            cluster.setFrozen(false);
        }
    }

    public void freezeClusters(double classType) {
        for(int i = 0; i < recognitionLayer.getNeurons().size(); i++) {
            NeuronCluster cluster = (NeuronCluster) recognitionLayer.getNeurons().get(i);
            if(cluster.getOutput() < threshold) {
                cluster.setFrozen(true);
            }
            if(cluster.getClassType() != classType) {
                cluster.setFrozen(true);
            }
        }
    }

    public NeuronCluster findBestCluster() {

        double bestA = Double.MIN_VALUE;
        NeuronCluster bestCluster = null;

        for(int i = 0; i < recognitionLayer.getNeurons().size(); i++) {
            NeuronCluster cluster = (NeuronCluster) recognitionLayer.getNeurons().get(i);
            if(!cluster.isFrozen()) {
                if(cluster.getOutput() > bestA) {
                    bestCluster = cluster;
                    bestA = cluster.getOutput();
                }
            }
        }

        return bestCluster;
    }


    public NeuronCluster createNewCluster(double classType, double[] exampleParams) {
        NeuronCluster clusterNeuron = NeuronCluster.createNewCluster(inputLayer.getNeurons(), E, classType, 5, F, exampleParams);
        for(Neuron neuron : outputLayer.getNeurons()) {
            neuron.createConnection(clusterNeuron);
        }

        return clusterNeuron;
    }


    @Override
    public double train(double[][] inputs, double[][] expectedOutputs, int currentEpoch) {
        return 0;
    }
}
