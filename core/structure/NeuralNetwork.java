package uhk.lab.neural.core.structure;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Tob on 18. 2. 2016.
 */
public class NeuralNetwork implements Serializable {

    private String name;
    private List<Layer> layers;
    private Layer input;
    private Layer output;

    public NeuralNetwork(List<Layer> layers, String name) {

        this.layers = layers;
        this.name = name;
        this.input = layers.get(0);
        this.output = layers.get(layers.size()-1);
        // randomizeWeights();
    }

    public void setInput(double[] inputs) {
        if(input != null) {
            if(input.getNeurons().size() != inputs.length) {
                throw new IllegalArgumentException("The number of inputs must equal the number of neurons in the input layer");
            }
            else {
                List<Neuron> neurons = input.getNeurons();
                for(int i = 0; i < neurons.size(); i++) {
                    neurons.get(i).setOutputTemp(inputs[i]);
                    neurons.get(i).setOutput();
                }
                /*for(int i = 0; i < neurons.size(); i++) {
                   System.out.println(neurons.get(i).getOutput()+"lali");
                }
                System.out.println(layers.get(1).getNeurons().get(0).inputs.get(0));
                System.out.println(layers.get(1).getNeurons().get(0).inputs.get(1));*/
            }
        }
    }

    public String getName() {
        return name;
    }

    public double[] getOutput() {

        double[] outputs = new double[output.getNeurons().size()];
        for(int i = 1; i < layers.size(); i++) {
            Layer layer = layers.get(i);
            layer.activate();
        }

        int i = 0;
        for(Neuron neuron : output.getNeurons()) {
            outputs[i] = neuron.getOutput();
            i++;
        }

        return outputs;
    }

    public List<Layer> getLayers() {
        return layers;
    }

    public void reset() {
        randomizeWeights();
    }

    public double[] getWeights() {

        List<Double> weights = new ArrayList<Double>();

        for(Layer layer : layers) {

            for(Neuron neuron : layer.getNeurons()) {

                for(Synapse synapse: neuron.getInputConnections()) {
                    //weights.add(synapse.getWeight());
                }
            }
        }

        double[] allWeights = new double[weights.size()];

        int i = 0;
        for(Double weight : weights) {
            allWeights[i] = weight;
            i++;
        }

        return allWeights;
    }

    public void copyWeightsFrom(NeuralNetwork sourceNeuralNetwork) {
        if(layers.size() != sourceNeuralNetwork.layers.size()) {
            throw new IllegalArgumentException("Cannot copy weights. Number of layers do not match (" + sourceNeuralNetwork.layers.size() + " in source versus " + layers.size() + " in destination)");
        }

        int i = 0;
        for(Layer sourceLayer : sourceNeuralNetwork.layers) {
            Layer destinationLayer = layers.get(i);

            if(destinationLayer.getNeurons().size() != sourceLayer.getNeurons().size()) {
                throw new IllegalArgumentException("Number of neurons do not match in layer " + (i + 1) + "(" + sourceLayer.getNeurons().size() + " in source versus " + destinationLayer.getNeurons().size() + " in destination)");
            }

            int j = 0;
            for(Neuron sourceNeuron : sourceLayer.getNeurons()) {
                Neuron destinationNeuron = destinationLayer.getNeurons().get(j);

                if(destinationNeuron.getInputConnections().size() != sourceNeuron.getInputConnections().size()) {
                    throw new IllegalArgumentException("Number of inputs to neuron " + (j + 1) + " in layer " + (i + 1) + " do not match (" + sourceNeuron.getInputConnections().size() + " in source versus " + destinationNeuron.getInputConnections().size() + " in destination)");
                }

                int k = 0;
                for(Synapse sourceSynapse : sourceNeuron.getInputConnections()) {
                    Synapse destinationSynapse = destinationNeuron.getInputConnections().get(k);

                    //destinationSynapse.setWeight(sourceSynapse.getWeight());
                    k++;
                }

                j++;
            }

            i++;
        }
    }

    public void persist() {
        String fileName = name.replaceAll(" ", "") + "-" + new Date().getTime() +  ".net";
        System.out.println("Writing trained neural network to file " + fileName);

        ObjectOutputStream objectOutputStream = null;

        try {
            objectOutputStream = new ObjectOutputStream(new FileOutputStream(fileName));
            objectOutputStream.writeObject(this);
        }

        catch(IOException e) {
            System.out.println("Could not write to file: " + fileName);
            e.printStackTrace();
        }

        finally {
            try {
                if(objectOutputStream != null) {
                    objectOutputStream.flush();
                    objectOutputStream.close();
                }
            }

            catch(IOException e) {
                System.out.println("Could not write to file: " + fileName);
                e.printStackTrace();
            }
        }
    }

    /**
     * Randomize weights
     */

    public void randomizeWeights() {

        for(int i = 1; i < layers.size(); i++) {
            for (Neuron neuron : layers.get(i).getNeurons()) {
                for (Synapse conn : neuron.getInputConnections()) {
                    double newWeight = (Math.random() * 1) - 0.5;

                }
            }
        }
    }

    public double[][] getOutputs(double[][] input) {

        double[][] output = new double[input.length][];

        for(int i = 0; i < input.length; i++) {
            setInput(input[i]);
            output[i] = getOutput();
        }

        for(int j = 0; j < output.length; j++) {
            //System.out.println("id : " + j + " result : " + output[j][0]);
        }

        return output;

    }


}
