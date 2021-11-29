package uhk.lab.neural.extend.structure.neurons;

import uhk.lab.neural.core.structure.Neuron;
import uhk.lab.neural.core.structure.Synapse;

/**
 * Created by Tob on 18. 2. 2016.
 */
public class NeuronOutput extends Neuron {

    /**
     *
     */
    private double output;

    /**
     *
     */
    @Override
    public double calculateOutput() {

        double bestA = Double.MIN_VALUE;
        NeuronCluster bestCluster = null;

        for (Synapse connection : getInputConnections()) {
            NeuronCluster cluster = (NeuronCluster) connection.getSourceNeuron();

            if (cluster.getOutput() > bestA) {
                bestCluster = cluster;
                bestA = cluster.getOutput();            }
        }

        if (bestCluster != null) {
            output = bestCluster.getClassType();
            System.out.println(output);
        }

        return output;

    }

}