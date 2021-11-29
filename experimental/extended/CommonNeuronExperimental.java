package uhk.lab.neural.experimental.extended;

import uhk.lab.neural.experimental.NeuronExperimental;
import uhk.lab.neural.experimental.SynapseExperimental;
import uhk.lab.neural.extend.activators.NeuralActivator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tob on 29. 5. 2016.
 */
public class CommonNeuronExperimental extends NeuronExperimental {


    /**
     *
     */
    private double derivative;

    /**
     *
     */
    private double outputTemp;

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
    public CommonNeuronExperimental(NeuralActivator activator){
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
        System.out.println("calculating"+id);
        double s = 0;
        if(getInputConnections().size() == 3) {
            System.out.println(getInputs().get(2)+"vystup predchozi");
        }
        for(int i = 0; i < getInputConnections().size(); i++) {
            /*SynapseExperimental con = getInputConnections().get(i);
            NeuronExperimental leftNeuron = con.getSourceNeuron();*/
            double weight = weights.get(i);
            double a = getInputs().get(i);
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
    public void createConnection(NeuronExperimental neuron) {
        super.createConnection(neuron);
        weights.add((Math.random() * 1) - 0.5);
        previousDeltaWeights.add(0d);
    }

    /**
     *
     */
    @Override
    public void setOutput(){
        super.setOutput(outputTemp);
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
    public double getWeight(SynapseExperimental con) {
        return weights.get(getSynapseID(con));
    }

    /**
     *
     * @param con
     * @param weight
     */
    public void setWeight(SynapseExperimental con, double weight) {
        weights.set(getSynapseID(con), weight);
    }

    /**
     *
     * @param neuron
     * @return
     */
    public double getWeight(NeuronExperimental neuron) {
        return weights.get(getSynapseID(neuron));
    }

    /**
     *
     * @param neuron
     * @param weight
     */
    public void setWeight(NeuronExperimental neuron, double weight) {
        weights.set(getSynapseID(neuron), weight);
    }

    /**
     *
     * @param con
     * @param previousDeltaWeight
     */
    public void setPreviousDeltaWeight(SynapseExperimental con, double previousDeltaWeight) {
        previousDeltaWeights.set(getSynapseID(con), previousDeltaWeight);
    }

    /**
     *
     * @param con
     * @return
     */
    public double getPreviousDeltaWeight(SynapseExperimental con) {
        return previousDeltaWeights.get(getSynapseID(con));
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

