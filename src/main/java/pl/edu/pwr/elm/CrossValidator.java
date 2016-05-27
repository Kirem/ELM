package pl.edu.pwr.elm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javafx.util.Pair;
import pl.edu.pwr.elm.model.ElmData;

public class CrossValidator {
    public static final String TAG = CrossValidator.class.getSimpleName();
    private static final String BAD_PARTS_COUNT_EXCEPTION = "Cannot split data(%d rows) into %d datasets";
    private final int trainingParts;
    private final int testingParts;
    private final DataCreator dataCreator;
    List<Output> outputList;
    List<Pair<Integer, Integer>> testingDataIndexes;
    private double[][] dataInputRows;
    private int singleStep;
    private int rowCount;

    public CrossValidator(int trainingParts, int testingParts, DataCreator dataCreator) {
        this.trainingParts = trainingParts;
        this.testingParts = testingParts;
        this.dataCreator = dataCreator;
        initValues();
    }

    private void initValues() {
        this.rowCount = dataCreator.getRowCount();
        this.rowCount = this.rowCount - Math.floorMod(this.rowCount, trainingParts + testingParts);
        List<Integer> permutationList = new ArrayList<>(this.rowCount);
        for (int i = 0; i < this.rowCount; i++) {
            permutationList.add(i);
        }
        Collections.shuffle(permutationList);
        double[][] dataInputRows = dataCreator.getDataInputRows();
        List<Output> outputValues = dataCreator.getOutputValues();
        outputList = new ArrayList<>();
        this.dataInputRows = new double[dataInputRows.length][];
        int idx = 0;
        for (Integer index : permutationList) {
            outputList.add(idx, outputValues.get(index));
            this.dataInputRows[idx++] = dataInputRows[index];
        }
        splitIntoValidationSets();
    }

    private void splitIntoValidationSets() {
        testingDataIndexes = new ArrayList<>();
        int var = testingParts + trainingParts;
        this.singleStep = dataCreator.getRowCount() / var;
        for (int i = 0; i <= trainingParts; i++) {
            testingDataIndexes.add(new Pair<>(i * this.singleStep, (i + testingParts) * this.singleStep));
        }
        System.out.println(testingDataIndexes);
    }

    public double startValidation(int numberOfNodes) {
        ElmConfig config = new ElmConfig();
        config.numberOfNodes = numberOfNodes;
        config.numberOfInputClasses = dataInputRows[0].length;
        config.numberOfOutputClasses = outputList.get(0).getOutput().length;
        int totalCorrectValues = 0;
        int totalTestValues = 0;
        for (Pair<Integer, Integer> testingDataPair : testingDataIndexes) {
            List<Output> testingOutput = outputList.subList(testingDataPair.getKey(), testingDataPair.getValue());
            List<Output> outputs = outputList.subList(0, testingDataPair.getKey());
            List<Output> trainingOutput = new ArrayList<>(outputs);
            List<Output> sub1 = new ArrayList<>(outputList.subList(testingDataPair.getValue(), outputList.size()));
            trainingOutput.addAll(sub1);
            double[][] trainingData = new double[trainingParts * singleStep][];
            double[][] testingData = new double[testingParts * singleStep][];
            int trainingDataIndex = 0;
            int testingDataIndex = 0;
            for (int i = 0; i < rowCount; i++) {
                if (i >= testingDataPair.getKey() && i < testingDataPair.getValue()) {
                    testingData[testingDataIndex++] = dataInputRows[i];
                } else {
                    trainingData[trainingDataIndex++] = dataInputRows[i];
                }
            }
            ElmData data = new ElmData(trainingData, trainingOutput);
            ELM elm = new ELM(config, data);
            elm.train();
            double[][] result = elm.test(testingData);
            int correctResults = 0;
            for (int i = 0; i < result.length; i++) {
                totalTestValues++;
                if (Arrays.equals(result[i], testingOutput.get(i).getOutput())) {
                    correctResults++;
                    totalCorrectValues++;
                }
            }
            int size = testingOutput.size();
            float accuracy = correctResults / (float) size;
            System.out.println(String.format("%d/%d - (%.2f)", correctResults, size, accuracy));
        }

        return totalCorrectValues / (float) totalTestValues;
    }

}
