package uhk.lab.neural.experimental;

import uhk.lab.neural.core.structure.Neuron;

/**
 * Created by Tob on 18. 2. 2016.
 */
public class SynapseExperimental {

    /**
     *
     */
    private final NeuronExperimental sourceNeuron;

    /**
     *
     */
    private final NeuronExperimental targetNeuron;

    /**
     *
     */
    static int counter = 0;

    /**
     *
     */
    final public int id;

    /**
     *
     * @param fromN
     * @param toN
     */
    public SynapseExperimental(NeuronExperimental fromN, NeuronExperimental toN) {
        super();
        sourceNeuron = fromN;
        targetNeuron = toN;
        id = counter;
        counter++;
    }

    /**
     *
     * @return
     */
    public NeuronExperimental getSourceNeuron() {
        return sourceNeuron;
    }

    /**
     *
     * @return
     */
    public NeuronExperimental getTargetNeuron() {
        return targetNeuron;
    }
}
