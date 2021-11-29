package uhk.lab.neural.main.test;

import uhk.lab.dataloading.LoadData;
import uhk.lab.dataprecompute.DataPrecompute;
import uhk.lab.encapsulation.Result;
import uhk.lab.encapsulation.dataset.DataSet;
import uhk.lab.neural.NeuralManager;
import uhk.lab.neural.SuccessListener;
import uhk.lab.neural.core.structure.NeuralNetwork;
import uhk.lab.neural.experimental.NeuralNetworkExperimental;
import uhk.lab.neural.experimental.extended.Creator;
import uhk.lab.neural.main.datagenerator.AndTrainingDataGenerator;
import uhk.lab.neural.support.trainer.Backpropagator;
import uhk.lab.neural.support.creating.NeuralNetworkCreator;
import uhk.lab.neural.main.datagenerator.TrainingDataGenerator;
import uhk.lab.neural.main.datagenerator.XorTrainingGenerator;
import uhk.lab.neural.support.trainer.MFArtMapTrainer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tob on 4. 2. 2016.
 */
public class Testik {

    public static void test() {
        TrainingDataGenerator xorTrainingDataGenerator = new XorTrainingGenerator();

        int[] layers = new int[] {2,4,4,1};

        NeuralNetworkExperimental feedForward = Creator.createFeedForward(layers);

        System.out.println("testik");
        System.out.println("testik");
        System.out.println("testik");


        feedForward.setInput(new double[]{0, 1});
        System.out.println("testik");
        //System.out.println("0 XOR 0: " + (feedForward.getOutput()[0]));
        feedForward.setInput(new double[]{0, 1});
        System.out.println("testik");
        feedForward.setInput(new double[]{0, 1});
        System.out.println("testik");
        feedForward.setInput(new double[]{0, 1});
        System.out.println("testik");
        feedForward.setInput(new double[]{0, 1});
        System.out.println("testik");
        feedForward.setInput(new double[]{0, 1});
        System.out.println("testik");
        feedForward.setInput(new double[]{0, 1});
        System.out.println("testik");
    }

    public static void main(String[] args) {
/*
        String filePath = "C:/Neural/Data/data-115.csv";
        String filePathTest = "C:/Neural/Data/dataTest-115.csv";

        //1, 0.05, 0.65

        DataSet dataSet = DataPrecompute.makeDataset(LoadData.loadData(filePath,";"),filePath,"",false,"lala");
        DataSet testingData = DataPrecompute.makeDataset(LoadData.loadData(filePathTest,";"),filePathTest,"",false,"lala");

        System.out.println(dataSet.getOutput().getData()[0].length);
        System.out.println(dataSet.getInput().getData()[0].length);

        int[] layers = new int[] {dataSet.getInput().getData()[0].length,75,25,dataSet.getOutput().getData()[0].length};

        NeuralNetwork feedForward = NeuralNetworkCreator.createFeedForward(layers,0,0,"blabla");

        final List<Result> results = new ArrayList<Result>();

        NeuralManager neuralManager = new NeuralManager();
        neuralManager.learnBackProp(new SuccessListener() {
            @Override
            public void result(Result result) {
               results.add(result);
            }
        },feedForward, dataSet, dataSet, 10000, 95);

        //neuralManager.printResultList(results, "bubisek");

        NeuralNetwork mfArtMap = NeuralNetworkCreator.createMFArtMap(dataSet.getInput().getData()[0].length,dataSet.getOutput().getData()[0].length,"blabla");

        MFArtMapTrainer trainer = new MFArtMapTrainer(mfArtMap, 0.65, 0.05, 1);
        trainer.train(dataSet);

        double[][] outputMf = mfArtMap.getOutputs(testingData.getInput().getData());
        List<Result> resultsMfArtMap = new ArrayList<Result>();
        Result resultMF = new Result();
        resultMF.setOutput(outputMf);
        resultsMfArtMap.add(resultMF);
        neuralManager.printResultList(resultsMfArtMap, "mfArt");

        double[][] outputFeed = feedForward.getOutputs(testingData.getInput().getData());
        List<Result> resultsFeedForward = new ArrayList<Result>();
        Result resultFeed = new Result();
        resultFeed.setOutput(outputFeed);
        resultsFeedForward.add(resultFeed);
        neuralManager.printResultList(resultsFeedForward, "feedfforward");

*/

        testMain();

    }

    public static NeuralNetwork testMain() {
        NeuralNetwork untrained = NeuralNetworkCreator.createFeedForward(new int[]{2, 2, 1}, NeuralNetworkCreator.ACTIVATOR_SIGMOID, NeuralNetworkCreator.ACTIVATOR_LINEAR, "");
        TrainingDataGenerator xorTrainingDataGenerator = new XorTrainingGenerator();

        Backpropagator backpropagator = new Backpropagator(untrained, 0.1, 0.9, 0);
        backpropagator.train(xorTrainingDataGenerator, 0.0001, 1000);

        System.out.println("Testing trained XOR neural network");

        //test(untrained, xorTrainingDataGenerator.getTrainingData().getInputs());

        untrained.setInput(new double[]{0, 0});
        System.out.println("0 XOR 0: " + (untrained.getOutput()[0]));

        untrained.setInput(new double[]{0, 1});
        System.out.println("0 XOR 1: " + (untrained.getOutput()[0]));

        untrained.setInput(new double[]{1, 0});
        System.out.println("1 XOR 0: " + (untrained.getOutput()[0]));

        untrained.setInput(new double[]{1, 1});
        System.out.println("1 XOR 1: " + (untrained.getOutput()[0]) + "\n");

        return untrained;
    }

    public static NeuralNetwork testLTSM() {
        NeuralNetworkCreator.createLTSM(10);
        return null;
    }

    public static NeuralNetwork testRecurrent() {
        NeuralNetwork untrained = NeuralNetworkCreator.createFullRecurrent(new int[]{2, 2, 1}, NeuralNetworkCreator.ACTIVATOR_SIGMOID, NeuralNetworkCreator.ACTIVATOR_LINEAR, "");
        TrainingDataGenerator xorTrainingDataGenerator = new XorTrainingGenerator();

        Backpropagator backpropagator = new Backpropagator(untrained, 0.1, 0.9, 0);
        backpropagator.train(xorTrainingDataGenerator, 0.0001, 1000000);

        System.out.println("Testing trained XOR neural network");

        //test(untrained, xorTrainingDataGenerator.getTrainingData().getInputs());

        untrained.setInput(new double[]{0, 0});
        System.out.println("0 XOR 0: " + (untrained.getOutput()[0]));

        untrained.setInput(new double[]{0, 1});
        System.out.println("0 XOR 1: " + (untrained.getOutput()[0]));

        untrained.setInput(new double[]{1, 0});
        System.out.println("1 XOR 0: " + (untrained.getOutput()[0]));

        untrained.setInput(new double[]{1, 1});
        System.out.println("1 XOR 1: " + (untrained.getOutput()[0]) + "\n");

        return untrained;
    }

    public static NeuralNetwork testMFArt() {
        NeuralNetwork untrained = NeuralNetworkCreator.createMFArtMap(2, 1, "");
        TrainingDataGenerator xorTrainingDataGenerator = new XorTrainingGenerator();

        MFArtMapTrainer backpropagator = new MFArtMapTrainer(untrained, 0.65, 0.05, 1);
        backpropagator.trainBack(xorTrainingDataGenerator, 2);

        System.out.println("Testing trained XOR neural network");

        //test(untrained, xorTrainingDataGenerator.getTrainingData().getInputs());

        /*untrained.setInput(new double[]{0, 0});
        System.out.println("0 XOR 0: " + (untrained.getOutput()[0]));

        untrained.setInput(new double[]{0, 1});
        System.out.println("0 XOR 1: " + (untrained.getOutput()[0]));

        untrained.setInput(new double[]{1, 0});
        System.out.println("1 XOR 0: " + (untrained.getOutput()[0]));

        untrained.setInput(new double[]{1, 1});
        System.out.println("1 XOR 1: " + (untrained.getOutput()[0]) + "\n");
*/
        return untrained;
    }

}