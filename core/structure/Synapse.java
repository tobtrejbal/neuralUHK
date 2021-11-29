package uhk.lab.neural.core.structure;

/**
 * Created by Tob on 18. 2. 2016.
 */
public class Synapse {

    /**
     *
     */
    private final Neuron sourceNeuron;

    /**
     *
     */
    private final Neuron targetNeuron;

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
    public Synapse(Neuron fromN, Neuron toN) {
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
    public Neuron getSourceNeuron() {
        return sourceNeuron;
    }

    /**
     *
     * @return
     */
    public Neuron getTargetNeuron() {
        return targetNeuron;
    }
}
