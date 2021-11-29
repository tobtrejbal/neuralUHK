package uhk.lab.neural.experimental;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Tob on 18. 2. 2016.
 *
 * Override calculate output if you want something
 *
 */
public class NeuronExperimental {

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

    private double tempOutput;

    /**
     *
     */
    private double output;

    /**
     *
     */
    private int inputConnectionCount = 0;

    /**
     *
     */
    private int outputConnectionCount = 0;

    private int inputCounter;

    Synchronisation synchronisation;

    /**
     *
     */
    List<Double> inputs = new ArrayList<Double>();

    /**
     *
     */
    List<SynapseExperimental> inputConnections = new ArrayList<SynapseExperimental>();

    /**
     *
     */
    List<SynapseExperimental> outputConnections = new ArrayList<SynapseExperimental>();

    /**
     *
     */
    HashMap<SynapseExperimental,Integer> inputSynapseIDLookoutSynapse = new HashMap<SynapseExperimental,Integer>();

    /**
     *
     */
    HashMap<NeuronExperimental,Integer> inputSynapseIDLookoutNeuron = new HashMap<NeuronExperimental,Integer>();

    /**
     *
     */
    HashMap<SynapseExperimental,Integer> outputSynapseIDLookoutSynapse = new HashMap<SynapseExperimental,Integer>();

    /**
     *
     */
    HashMap<NeuronExperimental,Integer> outputSynapseIDLookoutNeuron = new HashMap<NeuronExperimental,Integer>();

    public NeuronExperimental(){
        id = counter;
        System.out.println("ID neuronu " + id);
        counter++;
        inputCounter = 0;
    }

    /**
     * Calculate output of the neuron from all connections
     */

    public double calculateOutput(){
        return 0;
    }

    /**
     *
     * @param neuron
     * @return
     */
    public SynapseExperimental getSynapse(NeuronExperimental neuron){
        return getInputConnections().get(inputSynapseIDLookoutNeuron.get(neuron));
    }

    /**
     *
     * @param neuron
     * @return
     */
    public int getSynapseID(NeuronExperimental neuron){
        return inputSynapseIDLookoutNeuron.get(neuron);
    }

    /**
     *
     * @param synapse
     * @return
     */
    public int getSynapseID(SynapseExperimental synapse){
        return inputSynapseIDLookoutNeuron.get(synapse);
    }

    /**
     * Source
     * @param neuron
     */

    public void createConnection(NeuronExperimental neuron) {
        SynapseExperimental con = new SynapseExperimental(neuron, this);
        this.addInputConnection(con, neuron);
        neuron.addOutputConnection(con, this);
    }

    /**
     *
     * @param neuron
     */
    private void addInputConnection(SynapseExperimental con, NeuronExperimental neuron) {
        inputConnections.add(con);
        inputSynapseIDLookoutSynapse.put(con, inputConnectionCount);
        inputSynapseIDLookoutNeuron.put(neuron, inputConnectionCount);
        inputConnectionCount++;
    }

    /**
     *
     * @param neuron
     */

    private void addOutputConnection(SynapseExperimental con, NeuronExperimental neuron) {
        outputConnections.add(con);
        outputSynapseIDLookoutSynapse.put(con, outputConnectionCount);
        outputSynapseIDLookoutNeuron.put(neuron, outputConnectionCount);
        outputConnectionCount++;
    }

    /**
     *
     * @return
     */
    public List<SynapseExperimental> getInputConnections(){
        return inputConnections;
    }

    /**
     *
     * @return
     */
    public List<SynapseExperimental> getOutputConnections(){
        return outputConnections;
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
    }

    public void addInput(double input, NeuronExperimental neuronExperimental) {
        inputs.set(getSynapseID(neuronExperimental),input);
        inputCounter++;
        if(inputCounter >= inputConnectionCount) {
            inputCounter = 0;
            tempOutput = calculateOutput();
            if (synchronisation == null) {
                fireSignal();
            } else {
                synchronisation.synchronizedFire();
            }
        }
    }

    protected List<Double> getInputs() {
        return inputs;
    }


    protected double getTempOutput() {
        return tempOutput;
    }

    protected void setTempOutput(double tempOutput) {
        this.tempOutput = tempOutput;
    }

    public void resetNeuron() {
        for(int i = 0; i < inputConnections.size();i++) {
            inputs.add(0.0);
        }
    }

    public void fireSignal() {
        setOutput(tempOutput);
        System.out.println("signal vysila"+id);
        for(int i = 0; i < outputConnections.size();i++) {
            outputConnections.get(i).getTargetNeuron().addInput(getOutput(),this);
        }
        //readyToFire = false;
    }

    public void setSynchronisation(Synchronisation synchronisation) {
        this.synchronisation = synchronisation;
    }

    /*
    public void countInput() {
        inputCounter++;
        if(inputCounter >= inputConnectionCount) {
            inputCounter = 0;
            tempOutput = calculateOutput();
            if (synchronisation == null) {
                fireSignal();
            } else {
                synchronisation.synchronizedFire();
                // synchronizeSignalFire();
            }
        }
    }
*/
    /*

    tohle dava rizeni neuronum ale oproti synchronizacni tride to je dost spatne optimalizovany

    public void synchronizeSignalFire() {
        readyToFire = true;
        int readyCounter = 0;
        for(int i = 0; i < synchronisation.getNeurons().size();i++) {
            if(synchronisation.getNeurons().get(i).isReadyToFire()) {
                readyCounter++;
            }
        }
        if(readyCounter>=synchronisation.getNeurons().size()) {
            for(int i = 0; i < synchronisation.getNeurons().size();i++) {
                synchronisation.getNeurons().get(i).setOutput();
                synchronisation.getNeurons().get(i).fireSignal();
            }
        }
    }
*/


}

