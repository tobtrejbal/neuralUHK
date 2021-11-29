package uhk.lab.neural.support.creating;

import uhk.lab.neural.core.structure.Layer;
import uhk.lab.neural.core.structure.Neuron;
import uhk.lab.neural.core.structure.NeuralNetwork;
import uhk.lab.neural.extend.activators.Linear;
import uhk.lab.neural.extend.activators.LogSigmoid;
import uhk.lab.neural.extend.activators.NeuralActivator;
import uhk.lab.neural.extend.structure.neurons.BiasNeuron;
import uhk.lab.neural.extend.structure.neurons.NeuronCommon;
import uhk.lab.neural.extend.structure.neurons.NeuronOutput;
import uhk.lab.neural.extend.structure.neurons.NeuronProduct;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tob on 15. 2. 2016.
 */
public class NeuralNetworkCreator {

    /**
     *
     */
    public static final int ACTIVATOR_SIGMOID = 0;

    /**
     *
     */
    public static final int ACTIVATOR_LINEAR = 1;

    /**
     *
     */
    public static int counter = 0;

    /**
     *
     * @param layerSizes
     * @param activatorHidden
     * @param activatorOutput
     * @param name
     * @return
     */
    public static NeuralNetwork createFeedForward(int[] layerSizes, int activatorHidden, int activatorOutput, String name) {

        counter = 0;

        List<Layer> layers = new ArrayList<Layer>();

        // create input layer

        Layer inputLayer = new Layer(null);
        for(int j = 0; j < layerSizes[0]; j++) {
            Neuron neuron = new Neuron();
            inputLayer.addNeuron(neuron);
        }
        layers.add(inputLayer);
        //System.out.println("vrstva1");
        // create hidden layers

        for(int i = 1; i < layerSizes.length-1; i++) {
            Layer hiddenLayer = new Layer(layers.get(i-1));
            for(int j = 0; j < layerSizes[i]; j++) {
                NeuronCommon neuron = new NeuronCommon(getActivator(activatorHidden));
                for(Neuron previousLayerNeuron : layers.get(i-1).getNeurons()) {
                    neuron.createConnection(previousLayerNeuron);
                }
                neuron.randomizeWeights();
                hiddenLayer.addNeuron(neuron);
            }
            layers.add(hiddenLayer);
        }
        layers.get(0).getNeurons().get(0).outputConnections.size();
        //System.out.println("vrstva2"+layers.get(0).getNeurons().get(1).outputConnections.size());
        // create output layer

        Layer outputLayer = new Layer(layers.get(layers.size()-1));
        for(int j = 0; j < layerSizes[layerSizes.length-1]; j++) {
            NeuronCommon neuron = new NeuronCommon(getActivator(activatorOutput));
            //neuron.createConnection(bias);
            for(Neuron previousLayerNeuron : layers.get(layers.size()-1).getNeurons()) {
                neuron.createConnection(previousLayerNeuron);
            }
            neuron.randomizeWeights();
            outputLayer.addNeuron(neuron);
        }
        //System.out.println("vrstva3"+layers.get(1).getNeurons().get(1).outputConnections.size());
        layers.add(outputLayer);

        for(Layer layer : layers) {
            //System.out.println(layer.getNeurons().size()+"sizo");
        }

        NeuralNetwork neuralNetwork = new NeuralNetwork(layers, name);
        neuralNetwork.randomizeWeights();

        /*System.out.println("bubak");
        for(Layer layer : layers) {
            for(Neuron neuron : layer.getNeurons()) {
                System.out.println(neuron.getId());
            }
        }*/

        return neuralNetwork;

    }

    /**
     *
     * @param inputLayerSize
     * @return
     */
    public static NeuralNetwork createLTSM(int inputLayerSize) {
        counter = 0;

        List<Layer> layers = new ArrayList<Layer>();

        // create input layer

        Layer inputLayer = new Layer(null);
        for(int j = 0; j < inputLayerSize; j++) {
            Neuron neuron = new NeuronCommon(null);
            inputLayer.addNeuron(neuron);
        }
        layers.add(inputLayer);

        for(Layer layer : createLTSMBlock(inputLayer)) {
            layers.add(layer);
        }

        return new NeuralNetwork(layers, "name");

    }

    /**
     *
     * @param layerSizes
     * @param activatorHidden
     * @param activatorOutput
     * @param name
     * @return
     */
    public static NeuralNetwork createFullRecurrent(int[] layerSizes, int activatorHidden, int activatorOutput, String name) {

        counter = 0;

        NeuronCommon bias = new NeuronCommon(null);
        bias.setOutput(1);
        List<Layer> layers = new ArrayList<Layer>();

        // create input layer

        Layer inputLayer = new Layer(null);
        for(int j = 0; j < layerSizes[0]; j++) {
            Neuron neuron = new NeuronCommon(null);
            inputLayer.addNeuron(neuron);
        }
        layers.add(inputLayer);

        // create hidden layers

        for(int i = 1; i < layerSizes.length-1; i++) {
            Layer hiddenLayer = new Layer(layers.get(i-1));
            for(int j = 0; j < layerSizes[i]; j++) {
                NeuronCommon neuron = new NeuronCommon(getActivator(activatorHidden));
                neuron.createConnection(bias);
                for(Neuron previousLayerNeuron : layers.get(i-1).getNeurons()) {
                    neuron.createConnection(previousLayerNeuron);
                }
                neuron.randomizeWeights();
                hiddenLayer.addNeuron(neuron);
            }
            // add recurrent neurons
            for(Neuron neuron : hiddenLayer.getNeurons()) {
                neuron.addSynapses(hiddenLayer.getNeurons());
            }
            layers.add(hiddenLayer);
            for(Neuron neuron : hiddenLayer.getNeurons()) {
                for(Neuron neuron1 : hiddenLayer.getNeurons()) {
                    neuron.createConnection(neuron1);
                }
            }
        }

        // create output layer

        Layer outputLayer = new Layer(layers.get(layers.size()-1));
        for(int j = 0; j < layerSizes[layerSizes.length-1]; j++) {
            NeuronCommon neuron = new NeuronCommon(getActivator(activatorOutput));
            neuron.createConnection(bias);
            for(Neuron previousLayerNeuron : layers.get(layers.size()-1).getNeurons()) {
                neuron.createConnection(previousLayerNeuron);
            }
            neuron.randomizeWeights();
            outputLayer.addNeuron(neuron);
        }
        layers.add(outputLayer);

        for(Layer layer : layers) {
            System.out.println(layer.getNeurons().size()+"sizo");
        }

        NeuralNetwork neuralNetwork = new NeuralNetwork(layers, name);
        neuralNetwork.randomizeWeights();

        return neuralNetwork;

    }

    /**
     *
     * @param inputLayerSize
     * @param outputLayerSize
     * @param name
     * @return
     */
    public static NeuralNetwork createMFArtMap(int inputLayerSize, int outputLayerSize, String name) {

        counter = 0;

        List<Layer> layers = new ArrayList<Layer>();

        // create input layer

        Layer inputLayer = new Layer(null);
        for(int j = 0; j < inputLayerSize; j++) {
            NeuronCommon neuron = new NeuronCommon(null);
            inputLayer.addNeuron(neuron);
        }
        layers.add(inputLayer);

        // create recognition layer

        Layer recognitionLayer = new Layer(inputLayer);

        layers.add(recognitionLayer);

        // create output layer

        Layer outputLayer = new Layer(recognitionLayer);
        for(int j = 0; j < outputLayerSize; j++) {
            NeuronOutput neuron = new NeuronOutput();
            outputLayer.addNeuron(neuron);
        }
        layers.add(outputLayer);

        return new NeuralNetwork(layers, name);

    }

    /**
     *
     * @param id
     * @return
     */
    private static NeuralActivator getActivator(int id) {
        switch (id) {
            case ACTIVATOR_LINEAR:
                return new Linear();
            case ACTIVATOR_SIGMOID:
                return new LogSigmoid();
            default:
                return null;
        }
    }

    /**
     *
     * @param previousLayer
     * @return
     */
    private static List<Layer> createLTSMBlock(Layer previousLayer) {
        List<Layer> layers = new ArrayList<Layer>();

        // first layer (input and gates)

        Layer firstLayer = new Layer(previousLayer);

        // input and memory layer

        Layer secondLayer = new Layer(firstLayer);

        // calculate output

        Layer thirdLayer = new Layer(secondLayer);

        // set output

        Layer fourthLayer = new Layer(thirdLayer);

        layers.add(firstLayer);
        layers.add(secondLayer);
        layers.add(thirdLayer);
        layers.add(fourthLayer);

        // fill layers
        for(int i = 0; i < 4; i++) {
            NeuronCommon neuronCommon = new NeuronCommon(new LogSigmoid());
            firstLayer.addNeuron(neuronCommon);
        }
        for(int i = 0; i < 2; i++) {
            NeuronProduct neuronCommon = new NeuronProduct();
            secondLayer.addNeuron(neuronCommon);
        }
        for(int i = 0; i < 1; i++) {
            NeuronCommon neuronCommon = new NeuronCommon(new Linear());
            thirdLayer.addNeuron(neuronCommon);
        }
        for(int i = 0; i < 1; i++) {
            NeuronProduct neuronCommon = new NeuronProduct();
            fourthLayer.addNeuron(neuronCommon);
        }

        // connect layers
        // first - from previous layer
        for(Neuron neuron : firstLayer.getNeurons()) {
            for(Neuron neuronPrevious : previousLayer.getNeurons()) {
                neuron.createConnection(neuronPrevious);
            }
        }
        // second -
        // first neuron from first and second from first layer,
        // second from first neuron from third layer and third neuron from first layer
        Neuron neuronFirst = secondLayer.getNeurons().get(0);
        neuronFirst.createConnection(firstLayer.getNeurons().get(0));
        neuronFirst.createConnection(firstLayer.getNeurons().get(1));
        Neuron neuronSecond = secondLayer.getNeurons().get(1);
        neuronSecond.createConnection(firstLayer.getNeurons().get(2));
        neuronSecond.createConnection(thirdLayer.getNeurons().get(0));

        // third - all from second layer
        for(Neuron neuron : thirdLayer.getNeurons()) {
            for(Neuron neuronPrevious : secondLayer.getNeurons()) {
                neuron.createConnection(neuronPrevious);
            }
        }

        // fourth - third layer and fourth from first layer
        Neuron neuronThird = fourthLayer.getNeurons().get(0);
        neuronThird.createConnection(thirdLayer.getNeurons().get(0));
        neuronThird.createConnection(firstLayer.getNeurons().get(3));

        /*for(int i = 1; i < layers.size(); i++) {
            Layer layer = layers.get(i);
            System.out.println("I:"+(i+1));
            for(int j = 0; j < layer.getNeurons().size(); j++) {
                Neuron neuron = layer.getNeurons().get(j);
                for(Synapse synapse : neuron.getInputConnections()) {
                    System.out.println(synapse.getSourceNeuron().getOutput()+"output"+"j:"+j);
                }

            }
        }*/

        return layers;

    }
}
