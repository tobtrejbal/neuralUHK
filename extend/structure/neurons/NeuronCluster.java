package uhk.lab.neural.extend.structure.neurons;

import uhk.lab.neural.core.structure.Neuron;
import uhk.lab.neural.core.structure.Synapse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tob on 18. 2. 2016.
 */
public class NeuronCluster extends Neuron {

    private List<Double> xsValues;
    private List<Double> eValues;

    private double E;

    private boolean frozen;

    private double F;

    private double classType;

    int sampleCount = 0;

    private NeuronCluster(double classType, double F, double E, double[] exampleParams) {
        super();
        this.classType = classType;
        this.xsValues = new ArrayList<Double>();
        this.eValues = new ArrayList<Double>();
        this.F = F;
        this.E = E;
        sampleCount++;
        for(int i = 0; i < exampleParams.length ;i++) {
            xsValues.add(exampleParams[i]);
        }
        for (int i = 0; i < exampleParams.length; i++) {
            eValues.add(E);
        }
    }

    public static NeuronCluster createNewCluster(List<Neuron> neurons, double E, double classType, int id, double F, double[] exampleParams) {

        NeuronCluster neuron = new NeuronCluster(classType, F, E, exampleParams);

        for(Neuron n : neurons) {
            neuron.createConnection(n);
        }

        return neuron;
    }

    public void updateCluster() {
        System.out.println(classType + "classtype");
        sampleCount++;

        for(int i = 0; i < getInputConnections().size(); i++) {

            Neuron neuron = getInputConnections().get(i).getSourceNeuron();

            double xS = xsValues.get(i);

            double newXs = (double) xS
                    - (double) ((double) 1 / (double) sampleCount)
                    * (double) ((double) xS - (double) neuron.getOutput());

            xsValues.set(i, newXs);

            // System.out.println("Old Xs value (parametr "+i+") was: " +
            // xS);
            // System.out.println("New Xs value (parametr "+i+") is: " +
            // newXs);

            double oldE = eValues.get(i);

            // double xS = (double) cluster.getXsValue().get(i);

            xS = newXs;
            double newE = (double) oldE
                    - (double) ((double) 1 / (double) sampleCount)
                    * (double) ((double) oldE - (double) (Math.pow(
                    (double) (xS - (double) neuron.getOutput()), 2)));

            eValues.set(i, newE);

            // System.out.println("Old E value was: " + oldE);
            // System.out.println("New E value is: " + newE);








/*

            double oldXs = xsValues.get(i);


            double newXs = oldXs - ((oldXs - neuron.getOutput())/sampleCount);

            //System.out.println("OldXS" + oldXs + "NewXS" + newXs);

            xsValues.set(i, newXs);

            double oldE = eValues.get(i);

            double newE = oldE - ((oldE - (Math.pow((oldXs - neuron.getOutput()),2)))/sampleCount);

            //System.out.println("OldE" + oldE + "NewE" + newE);

            eValues.set(i, newE);
*/
        }
        System.out.println(sampleCount);

    }

    @Override
    public void createConnection(Neuron neuron) {
        super.createConnection(neuron);
        xsValues.add(neuron.getOutput());
        eValues.add(E);
    }

    // vypocet dilci funkce (hodnota pro jednu dimenzy - jedno promennou)

    public double fx(double xs, double E, double F, double x) {

        double fx = (double) 1
                / (double) ((double) 1 + (double) (Math.pow((Math
                .abs((double) xs
                        - (double) x) / (double) E), (double) F)));
        return fx;


        //System.out.println("xs:"+xs+"E:"+E+"F:"+F+"x:"+x);
        //return (double) 1 /
        //        ((double) 1 + (double) Math.pow(Math.abs(xs-x)/E,F));
    }

    public double getClassType() {
        return classType;
    }

    public boolean isFrozen() {
        return frozen;
    }

    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }

    // vypocet celkoveho A (tj. "vzdalenosti" od bodu)

    @Override
    public double calculateOutput() {

        List<Double> fx = new ArrayList<Double>();

        // f pro kazdy parametr prvku
        for(int i = 0; i < getInputConnections().size(); i++) {
            Synapse con = getInputConnections().get(i);
            Neuron neuron = con.getSourceNeuron();
            fx.add(fx(xsValues.get(i), eValues.get(i), F, neuron.getOutput()));
        }

        // vypoctu a ze vsech fx podle funkce (v cyklu)

        double aTemp = 0;

        for(int i = 0; i < fx.size(); i++) {
            aTemp += Math.pow((1 / fx.get(i)) - 1, 2);
            //System.out.println("output: "+fx.get(i)+"A"+a);
        }

        // upravim hodnotu a podle funkce (1/1+odmocnina(a))
        double a = 1/(1+Math.sqrt(aTemp));
        //System.out.println(a+ " A HODNOTA");
        return a;

        //System.out.println();
        //System.out.println();
    }
}
