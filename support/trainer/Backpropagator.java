package uhk.lab.neural.support.trainer;

import uhk.lab.neural.core.structure.Layer;
import uhk.lab.neural.core.structure.Neuron;
import uhk.lab.neural.core.structure.Synapse;
import uhk.lab.neural.extend.structure.neurons.NeuronCommon;
import uhk.lab.neural.core.structure.NeuralNetwork;
import uhk.lab.neural.main.datagenerator.TrainingData;
import uhk.lab.neural.main.datagenerator.TrainingDataGenerator;

import java.util.List;

/**
 * Created by Tob on 18. 2. 2016.
 */
public class Backpropagator implements Trainer {

    /**
     *
     */
    private NeuralNetwork neuralNetwork;

    /**
     *
     */
    private double learningRate;

    /**
     *
     */
    private double momentum;

    /**
     *
     */
    private double characteristicTime;

    /**
     *
     */
    private double currentEpoch;

    public Backpropagator(NeuralNetwork neuralNetwork, double learningRate, double momentum, double characteristicTime) {
        this.neuralNetwork = neuralNetwork;
        this.learningRate = learningRate;
        this.momentum = momentum;
        this.characteristicTime = characteristicTime;
    }

    public void train(TrainingDataGenerator generator, double errorThreshold, int maxRuns) {

        double error;
        double sum = 0.0;
        double average = 25;
        int epoch = 1;
        int samples = 25;
        double[] errors = new double[samples];

        do {
            TrainingData trainingData = generator.getTrainingData();
            error = backpropagate(trainingData.getInputs(), trainingData.getOutputs(), (int) currentEpoch);

            sum -= errors[epoch % samples];
            errors[epoch % samples] = error;
            sum += errors[epoch % samples];

            if(epoch > samples) {
                average = sum / samples;
            }

            // System.out.println("Error for epoch " + epoch + ": " + error + ". Average: " + average + (characteristicTime > 0 ? " Learning rate: " + learningRate / (1 + (currentEpoch / characteristicTime)): ""));
            epoch++;
            currentEpoch = epoch;
        } while(currentEpoch < maxRuns);//while(average > errorThreshold && currentEpoch < maxRuns);
    }

    public double backpropagate(double[][] inputs, double[][] expectedOutputs, int currentEpoch) {

        double error = 0;

        if(currentEpoch % 10 == 0) {
            double[][] output2 = new double[inputs.length][];

            for(int i = 0; i < inputs.length; i++) {
                neuralNetwork.setInput(inputs[i]);
                output2[i] = neuralNetwork.getOutput();
            }

            /*for(int j = 0; j < output2.length; j++) {
                for (int k = 0; k < output2[0].length; k++) {
                    System.out.println("id : " + j + " id : " + k + " result : " + output2[j][k]);
                }
            }*/
        }

        for (int i = 0; i < inputs.length; i++) {

            double[] input = inputs[i];
            double[] expectedOutput = expectedOutputs[i];

            List<Layer> layers = neuralNetwork.getLayers();

            neuralNetwork.setInput(input);
            double[] output = neuralNetwork.getOutput();
            if(currentEpoch % 100 == 0) {
                for(int j = 0; j < output.length; j++) {
                    System.out.println("id : " + i + " "+ j+" Expected : " + expectedOutput[j] + " Real val. : " + output[j]);
                }
            }
            //First step of the backpropagation algorithm. Backpropagate errors from the output layer all the way up
            //to the first hidden layer
            for (int j = layers.size() - 1; j > 0; j--) {
                Layer layer = layers.get(j);

                for (int k = 0; k < layer.getNeurons().size(); k++) {
                    NeuronCommon neuron = (NeuronCommon) layer.getNeurons().get(k);
                    double neuronError = 0;

                    if (layer.isOutputLayer()) {
                        //the order of output and expected determines the sign of the delta. if we have output - expected, we subtract the delta
                        //if we have expected - output we add the delta.
                        neuronError = neuron.getDerivative() * (output[k] - expectedOutput[k]);
                    } else {
                        neuronError = neuron.getDerivative();
                        double sum = 0;

                        List<Neuron> downstreamNeurons = layer.getNextLayer().getNeurons();
                        for (int l = 0; l < downstreamNeurons.size();l++) {

                            NeuronCommon downstreamNeuron = (NeuronCommon) downstreamNeurons.get(l);
                            sum += (downstreamNeuron.getWeight(neuron) * downstreamNeuron.getError());


                        }
                        neuronError *= sum;
                    }

                    neuron.setError(neuronError);
                }
            }

            //Second step of the backpropagation algorithm. Using the errors calculated above, update the weights of the
            //network
            for(int j = layers.size() - 1; j > 0; j--) {
                Layer layer = layers.get(j);

                for(int k = 0; k < layer.getNeurons().size(); k++) {
                    NeuronCommon neuron = (NeuronCommon) layer.getNeurons().get(k);
                    for(Synapse synapse : neuron.getInputConnections()) {

                        double newLearningRate = characteristicTime > 0 ? learningRate / (1 + (currentEpoch / characteristicTime)) : learningRate;
                        double delta = newLearningRate * neuron.getError() * synapse.getSourceNeuron().getOutput();
                        double previousDelta = neuron.getPreviousDeltaWeight(synapse);
                        delta += momentum * previousDelta;

                        neuron.setPreviousDeltaWeight(synapse, delta);
                        neuron.setWeight(synapse, neuron.getWeight(synapse) - delta);
                    }
                }
            }

            output = neuralNetwork.getOutput();
            error += error(output, expectedOutput);
        }

        return error;
    }

    public double error(double[] actual, double[] expected) {

        if (actual.length != expected.length) {
            throw new IllegalArgumentException("The lengths of the actual and expected value arrays must be equal");
        }

        double sum = 0;

        for (int i = 0; i < expected.length; i++) {
            sum += Math.pow(expected[i] - actual[i], 2);
        }

        return sum / 2;
    }

    /**
     *
     * @param inputs
     * @param expectedOutputs
     * @param currentEpoch
     * @return
     */
    @Override
    public double train(double[][] inputs, double[][] expectedOutputs, int currentEpoch) {

        return backpropagate(inputs, expectedOutputs, currentEpoch);

    }

}
