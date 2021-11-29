package uhk.lab.neural.main.datagenerator;

import java.util.Random;

/**
 * Created by Tob on 18. 2. 2016.
 */
public class AndTrainingDataGenerator implements TrainingDataGenerator {

    double[][] inputs = {{0, 0}, {0, 1}, {1, 0}, {1, 1}};
    double[][] outputs = {{-1}, {-1}, {-1}, {1}};
    int[] inputIndices = {0, 1, 2, 3};


    public TrainingData getTrainingData() {
        double[][] randomizedInputs = new double[4][2];
        double[][] randomizedOutputs = new double[4][1];

        inputIndices = shuffle(inputIndices);

        for (int i = 0; i < inputIndices.length; i++) {
            randomizedInputs[i] = inputs[inputIndices[i]];
            randomizedOutputs[i] = outputs[inputIndices[i]];
        }

//        return new TrainingData(inputs, outputs);

        //loadData();

        return new TrainingData(randomizedInputs, randomizedOutputs);
    }

    private int[] shuffle(int[] array) {

        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--) {

            int index = random.nextInt(i + 1);

            int temp = array[i];
            array[i] = array[index];
            array[index] = temp;
        }

        return array;
//        return new int[]{2, 1, 3, 0};
    }
}