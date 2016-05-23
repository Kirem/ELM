package pl.edu.pwr.elm;

import org.ejml.data.MatrixIterator64F;
import org.ejml.simple.SimpleMatrix;

import java.util.List;
import java.util.Random;

import pl.edu.pwr.elm.model.ElmData;

public class ELM {
    public static final String TAG = ELM.class.getSimpleName();
    private final ElmConfig config;
    private final ElmData elmData;
    private final Random random;
    private static final int RANDOM_SEED = 1024;
    private SimpleMatrix inputMatrix;
    private SimpleMatrix outputMatrix;
    private SimpleMatrix hiddenLayer;
    private SimpleMatrix biasMatrix;
    private SimpleMatrix hiddenLayerOutput;
    private SimpleMatrix output;

    public ELM(ElmConfig config, ElmData elmData) {
        ConfigValidator.with(config).validate();
        this.elmData = elmData;
        this.config = config;
        random = new Random(RANDOM_SEED);
        initValues();
    }

    private void initValues() {
        List<Output> elmOutputDataList = elmData.getElmOutputDataList();
        double[][] data = new double[elmOutputDataList.size()][];
        for (int i = 0; i < elmOutputDataList.size(); i++) {
            data[i] = elmOutputDataList.get(i).getOutput();
        }
        outputMatrix = new SimpleMatrix(data);
        inputMatrix = new SimpleMatrix(elmData.getElmInputDataList());
        hiddenLayer = SimpleMatrix.random(config.numberOfNodes, config.numberOfInputClasses, 0, 1, random);
        biasMatrix = SimpleMatrix.random(1, config.numberOfNodes, 0, 1, random);
        System.out.println(outputMatrix);
        System.out.println(inputMatrix);
    }

    public void train() {
        hiddenLayerOutput = applyActivationFunctionForEveryElement(calculateHiddenOutputMatrix());
        hiddenLayerOutput = hiddenLayerOutput.pseudoInverse();
        output = hiddenLayerOutput.mult(outputMatrix);
    }

    private SimpleMatrix calculateHiddenOutputMatrix() {
        SimpleMatrix output = new SimpleMatrix(inputMatrix.numRows(), config.numberOfNodes);
        System.out.println("\n\nCalculating hidden output...");
        for (int i = 0; i < inputMatrix.numRows(); i++) {
            for (int j = 0; j < config.numberOfNodes; j++) {
                double plus = calculateValueForPosition(i, j);
                output.set(i, j, plus);
            }
        }
        return output;
    }

    private double calculateValueForPosition(int i, int j) {
        double[] values = new double[config.numberOfInputClasses];
        double[] weights = new double[config.numberOfNodes];
        for (int k = 0; k < inputMatrix.numCols(); k++) {
            values[k] = inputMatrix.get(i, k);
            weights[k] = hiddenLayer.get(j, k);
        }
        SimpleMatrix valuesMatrix = new SimpleMatrix(new double[][]{values});
        SimpleMatrix weightsMatrix = new SimpleMatrix(new double[][]{weights});
        return valuesMatrix.transpose().dot(weightsMatrix) + (biasMatrix.get(j));
    }

    private SimpleMatrix applyActivationFunctionForEveryElement(SimpleMatrix hiddenLayerOutput) {
        MatrixIterator64F iterator = hiddenLayerOutput.iterator(true, 0, 0, hiddenLayerOutput.numRows() - 1, hiddenLayerOutput.numCols() - 1);
        while (iterator.hasNext()) {
            Double next = iterator.next();
            iterator.set(activationFunction(next));
        }
        return hiddenLayerOutput;
    }

    public double activationFunction(double x) {
        return 1 / (1 + Math.exp(-x));
    }

    public double[] test(double[] row) {
        SimpleMatrix testInput = new SimpleMatrix(new double[][]{row});
        SimpleMatrix hiddenLayerOutput = applyActivationFunctionForEveryElement(testInput.mult(hiddenLayer.transpose()).plus(biasMatrix));
        SimpleMatrix outputMatrix = hiddenLayerOutput.mult(output);
        double[] outputArray = new double[config.numberOfOutputClasses];
        for (int i = 0; i < config.numberOfOutputClasses; i++) {
            outputArray[i] = outputMatrix.get(i);
        }
        return outputArray;
    }

}