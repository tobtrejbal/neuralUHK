package uhk.lab.neural.extend.activators;

/**
 * Created by Tob on 8. 2. 2016.
 */
public class HyperbolicTangent implements NeuralActivator {

    /**
     *
     * @param d
     * @return
     */
    @Override
    public double g(double d) {
        return (Math.exp(d) - Math.exp(-1*d))/(Math.exp(d) + Math.exp(-1*d));
    }

    /**
     *
     * @param d
     * @return
     */
    @Override
    public double derivative(double d) {
        double temp = g(d);
        return 1- Math.pow((Math.exp(temp) - Math.exp(-1*temp))/(Math.exp(temp) + Math.exp(-1*temp)),2);
    }

}
