package uhk.lab.neural.experimental.extended;

import uhk.lab.neural.experimental.NeuralNetworkExperimental;
import uhk.lab.neural.experimental.NeuronExperimental;
import uhk.lab.neural.experimental.SynapseExperimental;
import uhk.lab.neural.experimental.Synchronisation;
import uhk.lab.neural.extend.activators.LogSigmoid;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tob on 29. 5. 2016.
 */
public class Creator {

    public static List<NeuronExperimental> add(List<NeuronExperimental> original,List<NeuronExperimental> adding) {
        for(int i = 0; i<adding.size();i++) {
            original.add(adding.get(i));
        }
        return original;
    }

    public static NeuralNetworkExperimental createFeedForward(int[] layerSizes) {

        List<NeuronExperimental> neurons = new ArrayList<NeuronExperimental>();
        List<NeuronExperimental> input = new ArrayList<NeuronExperimental>();
        List<NeuronExperimental> output = new ArrayList<NeuronExperimental>();

        // create input layer

        for(int j = 0; j < layerSizes[0]; j++) {
            InputNeuron neuron = new InputNeuron();
            input.add(neuron);
        }

        add(neurons,input);

        List<Synchronisation> synchronisations = new ArrayList<Synchronisation>();

        List<NeuronExperimental> hiddenLayer = new ArrayList<NeuronExperimental>();

        for(int i = 1; i < layerSizes.length-1; i++) {
            Synchronisation synchronisation = new Synchronisation();
            synchronisations.add(synchronisation);
            for(int j = 0; j < layerSizes[i]; j++) {
                CommonNeuronExperimental neuron = new CommonNeuronExperimental(new LogSigmoid());
                if(i == 1) {
                    for(NeuronExperimental previousLayerNeuron : input) {
                        neuron.createConnection(previousLayerNeuron);
                    }
                } else {
                    for(NeuronExperimental previousLayerNeuron : synchronisations.get(i-2).getNeurons()) {
                        neuron.createConnection(previousLayerNeuron);
                    }
                }
                neuron.setSynchronisation(synchronisation);
                neuron.resetNeuron();
                hiddenLayer.add(neuron);
            }
            synchronisation.setNeurons(hiddenLayer);
            add(neurons,hiddenLayer);
        }

        // create output layer

        for(int j = 0; j < layerSizes[layerSizes.length-1]; j++) {
            CommonNeuronExperimental neuron = new CommonNeuronExperimental(new LogSigmoid());
            for(NeuronExperimental previousLayerNeuron : hiddenLayer) {
                neuron.createConnection(previousLayerNeuron);
            }
            neuron.resetNeuron();
            output.add(neuron);
        }

        add(neurons,output);


        for(int j = 0; j < layerSizes[1]; j++) {
            NeuronExperimental neuron = hiddenLayer.get(j);
            for(NeuronExperimental neuronExperimental : output) {
                neuron.createConnection(neuronExperimental);
            }
            //neuron.resetNeuron();
        }

        List<SynapseExperimental> synapses = hiddenLayer.get(1).getInputConnections();

        for(int i = 0; i<synapses.size();i++) {
            System.out.println(synapses.get(i).getSourceNeuron().id);
        }

        System.out.println("test");
        System.out.println("test");
        System.out.println("test");

        NeuralNetworkExperimental neuralNetworkExperimental = new NeuralNetworkExperimental(input,output,neurons,"bubak");

        neuralNetworkExperimental.resetNetwork();

        return neuralNetworkExperimental;

    }

}
