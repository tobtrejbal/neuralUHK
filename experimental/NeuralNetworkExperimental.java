package uhk.lab.neural.experimental;

import uhk.lab.neural.experimental.extended.InputNeuron;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Tob on 29. 5. 2016.
 */
public class NeuralNetworkExperimental implements Serializable {

    private String name;
    private List<NeuronExperimental> input;
    private List<NeuronExperimental> output;
    private List<NeuronExperimental> neurons;

    public NeuralNetworkExperimental(List<NeuronExperimental> input, List<NeuronExperimental> output, List<NeuronExperimental> neurons, String name) {

        this.input = input;
        this.output = output;
        this.neurons = neurons;
        this.name = name;

    }

    public void setInput(double[] inputs) {
        if(input != null) {
            if(input.size() != inputs.length) {
                throw new IllegalArgumentException("The number of inputs must equal the number of neurons in the input layer");
            }
            else {
                for(int i = 0; i < input.size(); i++) {
                    InputNeuron inputNeuron = (InputNeuron) input.get(i);
                    inputNeuron.setInput(inputs[i]);
                }
            }
        }
    }

    public void resetNetwork() {
        for(int i = neurons.size()-1; i > 0;i--) {
            neurons.get(i).resetNeuron();
        }

        for(int i = neurons.size()-1; i>0;i--) {
            neurons.get(i).fireSignal();
        }
    }

    public String getName() {
        return name;
    }

    public double[] getOutput() {

        double[] outputs = new double[output.size()];

        int i = 0;
        for(NeuronExperimental neuron : output) {
            outputs[i] = neuron.getOutput();
            i++;
        }

        return outputs;
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
