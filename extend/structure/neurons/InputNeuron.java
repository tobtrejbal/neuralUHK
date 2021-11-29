package uhk.lab.neural.extend.structure.neurons;

import uhk.lab.neural.core.structure.Neuron;

/**
 * Created by Tob on 29. 5. 2016.
 */
public class InputNeuron extends Neuron{

    double input;

    @Override
    public double calculateOutput(){
        return input;
    }

    public void setInput(double input) {
        this.input = input;
    }

}
