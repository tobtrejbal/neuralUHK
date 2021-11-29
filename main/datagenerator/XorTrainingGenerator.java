package uhk.lab.neural.main.datagenerator;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Tob on 4. 2. 2016.
 */
public class XorTrainingGenerator implements TrainingDataGenerator {

    double[][] inputs = {{0, 0}, {0, 1}, {1, 0}, {1, 1}};
    double[][] outputs = {{-1}, {1}, {1}, {-1}};
    int[] inputIndices = {0, 1, 2, 3};


    public TrainingData getTrainingData() {
        double[][] randomizedInputs = new double[4][2];
        double[][] randomizedOutputs = new double[4][1];

        inputIndices = shuffle(inputIndices);

        for(int i = 0; i < inputIndices.length; i++) {
            randomizedInputs[i] = inputs[inputIndices[i]];
            randomizedOutputs[i] = outputs[inputIndices[i]];
        }

//        return new TrainingData(inputs, outputs);

       // loadData();

        return new TrainingData(inputs, outputs);
    }

    private int[] shuffle(int[] array) {

        Random random = new Random();
        for(int i = array.length - 1; i > 0; i--) {

            int index = random.nextInt(i + 1);

            int temp = array[i];
            array[i] = array[index];
            array[index] = temp;
        }

        return array;
//        return new int[]{2, 1, 3, 0};
    }

    private void loadData() {
        File file = new File("C:\\Tobous\\Projects\\Apps\\Java\\NeuralLibraryUHK\\out\\production\\DataSets\\Nevim-115.csv");

        BufferedReader br = null;
        String line = "";

        List<String[]> data = new ArrayList<String[]>();

        try {

            br = new BufferedReader(new FileReader(file));

            br.readLine();

            while ((line = br.readLine()) != null) {

                // use comma as separator
                data.add(line.split(";"));

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        inputs = new double[data.size()][data.get(0).length-1];
        outputs = new double[data.size()][1];

        for(int i = 0; i < inputs.length;i++) {
            String[] hh = data.get(i);
            for(int j = 0; j < inputs[0].length;j++) {
                inputs[i][j] = Double.parseDouble(hh[j]);
            }
        }
        for(int i = 0; i < inputs.length;i++) {
            String[] hh = data.get(i);
            outputs[i][0] = Double.parseDouble(hh[hh.length-1]);
        }


    }
}