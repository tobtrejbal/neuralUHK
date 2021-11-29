package uhk.lab.neural;

import uhk.lab.App;
import uhk.lab.encapsulation.dataset.DataSet;
import uhk.lab.encapsulation.Experiment;
import uhk.lab.encapsulation.FeedForwardExperiment;
import uhk.lab.encapsulation.Result;
import uhk.lab.neural.support.trainer.Backpropagator;
import uhk.lab.neural.core.structure.NeuralNetwork;
import uhk.lab.neural.support.trainer.Trainer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Tobous on 14. 11. 2015.
 */
public class NeuralManager {

    public void doExperiment(Experiment experiment, SuccessListener listener) {

        List<Result> results = new ArrayList<Result>();

        NeuralNetwork neuralNetwork = experiment.getNetwork();
        for(int i = 0; i < experiment.getExperimentList().size(); i++) {

            FeedForwardExperiment feedForwardExperiment = experiment.getExperimentList().get(i);
            switch (feedForwardExperiment.getLearningType()) {
                case 0:

                    results = learnBackProp(listener, neuralNetwork, feedForwardExperiment.getLearningDataSet(), feedForwardExperiment.getTestingDataSet(),
                            feedForwardExperiment.getNumberOfIterations(), 95d);

                    break;
                default:

                    break;
            }

        }

        printResultList(results,  experiment.getName());

    }

    public List<Result> learnBackProp(SuccessListener listener, NeuralNetwork neuralNetwork, DataSet learning, DataSet testing, int iterations, double successRate) {
        /*String path = "/C:/Tobous/Projects/Apps/Java/NeuralLibraryUHK/out/production/NeuralLibraryUHK/DataSets\\Kruh.csv";
        learning = DataPrecompute.makeDataset(
                LoadData.loadData(path, ";"),
                path, -999, true, "bio");*/
        List<Result> resultList = new ArrayList<Result>();

        double bestSuccess = 0;
        int bestIteration;
        Result result;

        double error;
        double sum = 0.0;
        double average = 25;
        int epoch = 1;
        int samples = 25;
        double[] errors = new double[samples];

        neuralNetwork.randomizeWeights();

        Trainer trainer = new Backpropagator(neuralNetwork, 0.1, 0.9, 0);

        for (epoch = 1; epoch < iterations; epoch++) {

            error = trainer.train(learning.getInput().getData(), learning.getOutput().getData(), epoch);
            if ((epoch % 100 != 0) && !(epoch + 1 == iterations)) {
                continue;
            }

            sum -= errors[epoch % samples];
            errors[epoch % samples] = error;
            sum += errors[epoch % samples];

            if(epoch > samples) {
                average = sum / samples;
            }

            result = classifyTest(testing, neuralNetwork, 0.05);
            double success = result.getSuccess();
            /*if (success >= successRate) {
                bestIteration = epoch;
                break;
            }*/

            System.out.println(epoch+ "epoch"+ iterations);

            result.setIteration(epoch);

            listener.result(result);

            resultList.add(result);

            System.out.println(success);

            epoch++;

            if (success >= successRate) {

                break;

            }

        }

        return resultList;

    }

    public void printResultList(List<Result> results, String experimentName) {

        App app = App.getInstance();

        String date = String.valueOf(new Date().getTime());

        File file = new File(app.getPathExperimentsLog()+"//"+experimentName+date+".csv");

        try {

            file.createNewFile();

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            StringBuilder sb = new StringBuilder();
            Result rt = results.get(0);

            for(int a = 0; a < rt.getOutput()[0].length; a++) {

                for(int l = 0; l < results.size(); l++) {
                    sb.append(l*10);
                    sb.append(";");
                }

                bw.write(sb.toString());
                sb.delete(0,sb.length());
                bw.write("\n");

                for(int i = 0; i < rt.getOutput().length; i++) {
                    for(int j = 0; j < results.size(); j++) {
                        sb.append(results.get(j).getOutput()[i][a]);
                        sb.append(";");
                    }
                    String temp = sb.toString();
                    bw.write(temp);
                    sb.delete(0,sb.length());
                    bw.write("\n");
                }

               /* for(int i = 0; i < table[0].length; i++) {
                    sb.append(i);
                    sb.append(";");
                }
                sb.deleteCharAt(sb.length()-1);
                sb.append("\n");

                for(int i = 0; i < table.length; i++) {
                    sb.append(i);
                    sb.append(";");
                    for(int j = 0; j < table[i].length; j++) {
                        sb.append(table[i][j]);
                        sb.append(";");
                    }
                    sb.append("\n");
                }*/

                bw.write("\n");
                bw.write("\n");

            }

            bw.close();




        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //TODO  naposled tuto metodu, kde posledni polozka z vraceneho pole je % uspesnost
    public Result classifyTest(DataSet testing, NeuralNetwork neuralNetwork, double error) {

        double[][] input = testing.getInput().getData();
        double[][] outputRight = testing.getOutput().getData();
        double[][] output = neuralNetwork.getOutputs(input);
        double[] averages = new double[output[0].length];

        for(int i = 0; i < output[0].length;i++) {
            double average = 0;
            double sum = 0;
            for(int j =0; j < output.length;j++) {
                sum += output[j][i];
            }
            average = sum / output.length;
            averages[i] = average;

        }

        Result result = new Result();
        result.setOutput(output);
        result.setAverageResult(averages);
        result.setSuccess(1.0);

        return result;

    }

}
