package uhk.lab.neural.extend.activators;

/**
 * Created by Tob on 8. 2. 2016.
 */
public class Linear implements NeuralActivator {

    /**
     *
     * @param d
     * @return
     */
    @Override
    public double g(double d) {
        return d;
    }

    /**
     *
     * @param d
     * @return
     */
    @Override
    public double derivative(double d) {
        return 1;
    }

}
