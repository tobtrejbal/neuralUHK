package uhk.lab.neural.experimental;

import java.util.List;

/**
 * Created by Tob on 29. 5. 2016.
 */
public class Synchronisation {

    private List<NeuronExperimental> neurons;

    private int counter;

    public Synchronisation() {
        counter = 0;
    }

    public List<NeuronExperimental> getNeurons() {
        return neurons;
    }

    public void setNeurons(List<NeuronExperimental> neurons) {
        this.neurons = neurons;
    }

    public void synchronizedFire() {
        counter++;
        if(counter >= neurons.size()) {
            for(int i = 0; i < neurons.size();i++) {
                neurons.get(i).fireSignal();
            }
            counter = 0;
        }
    }
}
