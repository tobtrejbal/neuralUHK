package uhk.lab.neural.experimental.extended;

import uhk.lab.neural.experimental.NeuronExperimental;

/**
 * Created by Tob on 29. 5. 2016.
 */
public class InputNeuron extends NeuronExperimental {

    public void setInput(double input) {
        super.setTempOutput(input);
        super.fireSignal();
    }

}
