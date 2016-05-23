package pl.edu.pwr.elm;

import java.util.ArrayList;
import java.util.List;

import javafx.util.Pair;

import static java.lang.String.format;

public class CrossValidator {
    public static final String TAG = CrossValidator.class.getSimpleName();
    private static final String BAD_PARTS_COUNT_EXCEPTION = "Cannot split data(%d rows) into %d datasets";
    private final int trainingParts;
    private final int testingParts;
    private final DataCreator dataCreator;
    List<Output> outputList;
    private double[][] dataInputRows;
    List<Pair<Integer, Integer>> testingDataIndexes;
    private int singleStep;

    public CrossValidator(int trainingParts, int testingParts, DataCreator dataCreator) {
        this.trainingParts = trainingParts;
        this.testingParts = testingParts;
        this.dataCreator = dataCreator;
        int var = testingParts + trainingParts;
        double v = dataCreator.getRowCount() / (double) var;
        if (v != (int) v) {
            throw new IllegalStateException(format(BAD_PARTS_COUNT_EXCEPTION, dataCreator.getRowCount(), var));
        }
        initValues();
    }

    private void initValues() {
        int rowCount = dataCreator.getRowCount();
        List<Integer> permutationList = new ArrayList<>(rowCount);
        for (int i = 0; i < rowCount; i++) {
            permutationList.add(i);
        }
//        Collections.shuffle(permutationList);
        double[][] dataInputRows = dataCreator.getDataInputRows();
        List<Output> outputValues = dataCreator.getOutputValues();
        outputList = new ArrayList<>();
        this.dataInputRows = new double[dataInputRows.length][];
        for (Integer index : permutationList) {
            outputList.add(outputValues.get(index));
            this.dataInputRows[index] = dataInputRows[index];
        }
        splitIntoValidationSets();
    }

    private void splitIntoValidationSets() {
        testingDataIndexes = new ArrayList<>();
        int var = testingParts + trainingParts;
        this.singleStep = dataCreator.getRowCount() / var;
        for (int i = 0; i <= trainingParts; i++) {
            testingDataIndexes.add(new Pair<>(i* this.singleStep, (i + testingParts)* this.singleStep));
        }
        System.out.println(testingDataIndexes);
    }

    public void startValidation(int numberOfNodes) {
        ElmConfig config = new ElmConfig();
        config.numberOfNodes = numberOfNodes;
        config.numberOfInputClasses = dataInputRows[0].length;
        config.numberOfOutputClasses = outputList.get(0).getOutput().length;
        for (Pair<Integer, Integer> testingDataPair : testingDataIndexes) {
            List<Output> testingOutput = outputList.subList(testingDataPair.getKey(), testingDataPair.getValue());
            List<Output> outputs = outputList.subList(0, testingDataPair.getKey());
            List<Output> trainingOutput = new ArrayList<>(outputs);
            List<Output> sub1 = new ArrayList<>(outputList.subList(testingDataPair.getValue(), outputList.size()));
            trainingOutput.addAll(sub1);
            double[][] trainingData = new double[trainingParts*singleStep][];
            double[][] testingData = new double[testingParts*singleStep][];
            int trainingDataIndex = 0;
            int testingDataIndex = 0;
            for (int i = 0; i < dataInputRows.length; i++) {
                if (i >= testingDataPair.getKey() && i < testingDataPair.getValue()) {
                    testingData[testingDataIndex++] = dataInputRows[i];
                } else {
                    trainingData[trainingDataIndex++] = dataInputRows[i];
                }
            }

//            System.out.println("Training parts:\n");
//            for (int i = 0; i < trainingParts*singleStep; i++) {
//                System.out.println(Arrays.toString(trainingData[i]));
//                System.out.println(Arrays.toString(trainingOutput.get(i).getOutput()));
//            }
//            System.out.println("\n\nTesting parts:\n");
//            for (int i = 0; i < testingParts*singleStep; i++) {
//                System.out.println(Arrays.toString(testingData[i]));
//                System.out.println(Arrays.toString(testingOutput.get(i).getOutput()));
//            }
        }
    }

}
