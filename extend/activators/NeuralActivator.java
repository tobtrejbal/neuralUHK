package uhk.lab.neural.extend.activators;

/**
 * Created by Tob on 8. 2. 2016.
 */
public interface NeuralActivator {

    /**
     *
     * @param d
     * @return
     */
    double g(double d);

    /**
     *
     * @param d
     * @return
     */
    double derivative(double d);

}
