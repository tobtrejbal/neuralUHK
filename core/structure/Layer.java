package uhk.lab.neural.core.structure;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tob on 18. 2. 2016.
 * Basic layer - represents signal at a time
 */
public class Layer {

    /**
     * List of neurons in layer
     */
    private List<Neuron> neurons;

    /**
     * previous layer
     */
    private Layer previousLayer;

    /**
     * next layer
     */
    private Layer nextLayer;

    /**
     * create layer and sets previous layer for this instance and next layer in previous layer
     * @param previousLayer
     */
    public Layer(Layer previousLayer) {
        this.neurons = new ArrayList<Neuron>();
        this.previousLayer = previousLayer;
        if(previousLayer != null) {
            previousLayer.setNextLayer(this);
        }
    }

    /**
     *
     * @return
     */
    public boolean isOutput() {
        return nextLayer == null;
    }

    /**
     *
     * @return
     */
    public List<Neuron> getNeurons() {
        return this.neurons;
    }

    /**
     *
     */
    public void activate() {
        // nejdriv spocitam vystupni hodnotu pro vsechny neurony
        for(int i = 0; i < neurons.size(); i++) {
            neurons.get(i).calculateInternal();
        }
        // a po vypoctu vsech ji teprve nastavim na vystup (aby to simulovalo paralelni vypocet - vsechny neurony se vypocitaji najednou)
        for(int i = 0; i < neurons.size(); i++) {
            neurons.get(i).setOutput();
        }
    }

    /**
     *
     * @return
     */
    public Layer getPreviousLayer() {
        return previousLayer;
    }

    /**
     *
     * @param previousLayer
     */
    public void setPreviousLayer(Layer previousLayer) {
        this.previousLayer = previousLayer;
    }

    /**
     *
     * @return
     */
    public Layer getNextLayer() {
        return nextLayer;
    }

    /**
     *
     * @param nextLayer
     */
    public void setNextLayer(Layer nextLayer) {
        this.nextLayer = nextLayer;
    }

    /**
     *
     * @return
     */
    public boolean isOutputLayer() {
        return nextLayer == null;
    }

    /**
     *
     * @return
     */
    public boolean isInputLayer() {
        return previousLayer == null;
    }

    /**
     *
     * @param neuron
     */
    public void addNeuron(Neuron neuron) {
        neurons.add(neuron);
    }

}
