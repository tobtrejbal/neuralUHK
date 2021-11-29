package uhk.lab.neural.extend.activators;

/**
 * Created by Tob on 8. 2. 2016.
 */
public class LogSigmoid implements NeuralActivator {

    /**
     *
     * @param d
     * @return
     */
    @Override
    public double g(double d) {
        return 1.0 / (1 + Math.exp(-1.0 * d));
    }

    /**
     *
     * @param d
     * @return
     */
    @Override
    public double derivative(double d) {
        double temp = g(d);
        return temp * (1.0 - temp);
    }
}
