package uhk.lab.neural.support.trainer;

/**
 * Created by Tob on 9. 2. 2016.
 */
public interface Trainer {

    public double train(double[][] inputs, double[][] expectedOutputs, int currentEpoch);

}
