package uhk.lab.neural.extend.structure.neurons;

import uhk.lab.neural.core.structure.Neuron;

/**
 * Created by Tob on 29. 5. 2016.
 */
public class BiasNeuron extends Neuron {

    double value;

    @Override
    public double calculateOutput() {
        return value;
    }

    public BiasNeuron(double value) {
        this.value = 1.0;
    }

}
