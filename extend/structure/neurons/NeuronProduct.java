package uhk.lab.neural.extend.structure.neurons;

import uhk.lab.neural.core.structure.Neuron;
import uhk.lab.neural.core.structure.Synapse;

/**
 * Created by Tob on 21. 2. 2016.
 */
public class NeuronProduct extends Neuron {

    /**
     *
     */
    public NeuronProduct(){
        super();
    }

    /**
     * Compute Sj = Wij*Aij + w0j*bias
     * Calculate output of the neuron from all connections and their weights and add bias to them
     */

    @Override
    public double calculateOutput(){
        double s = 1;
        for(int i = 0; i < getInputConnections().size(); i++) {
            Synapse con = getInputConnections().get(i);
            Neuron leftNeuron = con.getSourceNeuron();
            double a = leftNeuron.getOutput();
            s *= a;
        }
        return s;
    }

    /**
     *
     * @param neuron
     */
    @Override
    public void createConnection(Neuron neuron) {
        super.createConnection(neuron);
    }

    /**
     *
     * @return
     */
    @Override
    public int getId() {
        return id;
    }

}
