package uhk.lab.neural.core.structure;

import uhk.lab.neural.experimental.SynapseExperimental;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Tob on 18. 2. 2016.
 *
 * Override calculate output if you want something
 *
 */
public class Neuron {

    /**
     *
     */
    final public int id;

    /**
     *
     */
    public static int counter = 0;

    /**
     *
     */
    private double output;

    /**
     *
     */
    private double outputTemp;

    /**
     *
     */
    private int inputConnectionCount = 0;

    /**
     *
     */
    private int outputConnectionCount = 0;

    /**
     *
     */
    protected List<Double> inputs = new ArrayList<Double>();

    /**
     *
     */
    public List<Synapse> inputConnections = new ArrayList<Synapse>();

    /**
     *
     */
    HashMap<Synapse,Integer> inputSynapseIDLookoutSynapse = new HashMap<Synapse,Integer>();

    /**
     *
     */
    HashMap<Neuron,Integer> inputSynapseIDLookoutNeuron = new HashMap<Neuron,Integer>();

    /**
     *
     */
    public List<Synapse> outputConnections = new ArrayList<Synapse>();

    /**
     *
     */
    HashMap<Synapse,Integer> outputSynapseIDLookoutSynapse = new HashMap<Synapse,Integer>();

    /**
     *
     */
    HashMap<Neuron,Integer> outputSynapseIDLookoutNeuron = new HashMap<Neuron,Integer>();


    public Neuron(){
        id = counter;
        counter++;
    }

    /**
     * Calculate output of the neuron from all inputConnections
     */

    public void calculateInternal(){
        outputTemp = calculateOutput();
    }

    /**
     * Calculate output of the neuron from all inputConnections
     */

    public double calculateOutput(){
        return 0;
    }

    /**
     *
     * @param inNeurons
     */
    public void addSynapses(List<Neuron> inNeurons){
        for(Neuron n: inNeurons){
            createConnection(n);
        }
    }

    /**
     *
     * @param neuron
     * @return
     */
    public Synapse getInputSynapse(Neuron neuron){
        return getInputConnections().get(inputSynapseIDLookoutNeuron.get(neuron));
    }

    /**
     *
     * @param neuron
     * @return
     */
    public int getInputSynapseId(Neuron neuron){
        return inputSynapseIDLookoutNeuron.get(neuron);
    }

    /**
     *
     * @param synapse
     * @return
     */
    public int getInputSynapseId(Synapse synapse){
        return inputSynapseIDLookoutSynapse.get(synapse);
    }

    /**
     * Source
     * @param neuron
     */

    public void createConnection(Neuron neuron) {
        Synapse con = new Synapse(neuron, this);
        this.addInputConnection(con, neuron);
        neuron.addOutputConnection(con, this);
        inputs.add(0.0);
    }

    /**
     *
     * @param neuron
     */
    private void addInputConnection(Synapse con, Neuron neuron) {
        inputConnections.add(con);
        inputSynapseIDLookoutSynapse.put(con, inputConnectionCount);
        inputSynapseIDLookoutNeuron.put(neuron, inputConnectionCount);
        inputConnectionCount++;
    }

    /**
     *
     * @param neuron
     */

    private void addOutputConnection(Synapse con, Neuron neuron) {
        outputConnections.add(con);
        outputSynapseIDLookoutSynapse.put(con, outputConnectionCount);
        outputSynapseIDLookoutNeuron.put(neuron, outputConnectionCount);
        outputConnectionCount++;
    }


    /**
     *
     * @return
     */
    public List<Synapse> getInputConnections(){
        return inputConnections;
    }

    /**
     *
     * @return
     */
    public double getOutput() {
        return output;
    }

    /**
     *
     * @param o
     */
    public void setOutput(double o){
        output = o;
    }

    /**
     *
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     *
     */
    public void setOutput() {
        output = outputTemp;
        for(int i = 0; i < outputConnectionCount;i++) {
            outputConnections.get(i).getTargetNeuron().make();
        }
    }

    public void setOutputTemp(double outputTemp) {
        this.outputTemp = outputTemp;
    }

    public void make() {

    }
}

