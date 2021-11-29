package uhk.lab.neural.extend.structure.neurons;

import uhk.lab.neural.extend.activators.NeuralActivator;
import uhk.lab.neural.core.structure.Neuron;
import uhk.lab.neural.core.structure.Synapse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tob on 18. 2. 2016.
 */
public class NeuronCommon extends Neuron {

    /**
     *
     */
    private double derivative;

    /**
     *
     */
    private double error;

    /**
     *
     */
    private NeuralActivator activator;

    /**
     *
     */
    List<Double> weights;

    /**
     *
     */
    List<Double> previousDeltaWeights;

    /**
     *
     * @param activator
     */
    public NeuronCommon(NeuralActivator activator){
        super();
        this.error = 0;
        this.activator = activator;
        this.weights = new ArrayList<Double>();
        this.previousDeltaWeights = new ArrayList<Double>();
    }

    /**
     * Compute Sj = Wij*Aij + w0j*bias
     * Calculate output of the neuron from all connections and their weights and add bias to them
     */
    @Override
    public double calculateOutput(){
        double s = 0;
        //System.out.println("pocitam"+id);
        for(int i = 0; i < getInputConnections().size(); i++) {
            Synapse con = getInputConnections().get(i);
            Neuron leftNeuron = con.getSourceNeuron();
            double weight = weights.get(i);
            //double a = leftNeuron.getOutput();
            double a = inputs.get(i);
            s += weight * a;
        }
        derivative =  derivative(s);
        return g(s);
    }

    /**
     *
     * @param neuron
     */
    @Override
    public void createConnection(Neuron neuron) {
        super.createConnection(neuron);
        weights.add((Math.random() * 1) - 0.5);
        previousDeltaWeights.add(0d);
    }

    /**
     *
     * @return
     */
    public double getDerivative() {
        return derivative;
    }

    /**
     *
     * @return
     */
    public double getError() {
        return error;
    }

    /**
     *
     * @param error
     */
    public void setError(double error) {
        this.error = error;
    }

    /**
     *
     * @return
     */
    @Override
    public int getId() {
        return id;
    }

    /**
     *
     * @param x
     * @return
     */
    private double g(double x) {
        return activator.g(x);
    }

    /**
     *
     * @param x
     * @return
     */
    private double derivative(double x) {
        return activator.derivative(x);
    }

    /**
     *
     * @param con
     * @return
     */
    public double getWeight(Synapse con) {
        return weights.get(getInputSynapseId(con));
    }

    /**
     *
     * @param con
     * @param weight
     */
    public void setWeight(Synapse con, double weight) {
        weights.set(getInputSynapseId(con), weight);
    }

    /**
     *
     * @param neuron
     * @return
     */
    public double getWeight(Neuron neuron) {
        return weights.get(getInputSynapseId(neuron));
    }

    /**
     *
     * @param neuron
     * @param weight
     */
    public void setWeight(Neuron neuron, double weight) {
        weights.set(getInputSynapseId(neuron), weight);
    }

    /**
     *
     * @param con
     * @param previousDeltaWeight
     */
    public void setPreviousDeltaWeight(Synapse con, double previousDeltaWeight) {
        previousDeltaWeights.set(getInputSynapseId(con), previousDeltaWeight);
    }

    /**
     *
     * @param con
     * @return
     */
    public double getPreviousDeltaWeight(Synapse con) {
        return previousDeltaWeights.get(getInputSynapseId(con));
    }

    /**
     *
     */
    public void randomizeWeights() {
        for(int i = 0; i < weights.size(); i++) {
            weights.set(i,(Math.random() * 1) - 0.5);
        }
    }

}
