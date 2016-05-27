package pl.edu.pwr;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import pl.edu.pwr.elm.ConfigReader;
import pl.edu.pwr.elm.CrossValidator;
import pl.edu.pwr.elm.DataCreator;
import pl.edu.pwr.elm.ELM;
import pl.edu.pwr.elm.ElmConfig;
import pl.edu.pwr.elm.model.ElmData;

public class Starter {
    public static final String TAG = Starter.class.getSimpleName();
    private static final int DEFAULT_LEARNING_SETS_COUNT = 2;
    private static final int DEFAULT_TESTING_SETS_COUNT = 1;

    public Starter() {
    }

    public static void main(String[] args) {
        ElmConfig config = new ElmConfig();
        config.numberOfNodes = 15;
        DataCreator dataCreator = getUserPreferences();
        int learningSetsCount = fetchCountOfLearningSets();
        int testingSetsCount = fetchCountOfTestingSets();

        if (dataCreator != null) {
            dataCreator.readData();
            config.numberOfOutputClasses = dataCreator.getNumberOfOutputClasses();
            config.numberOfInputClasses = dataCreator.getNumberOfInputsClasses();
            ElmData elmData = new ElmData(dataCreator.getDataInputRows(), dataCreator.getOutputValues());
            ELM elm = new ELM(config, elmData);
            elm.train();
            CrossValidator validator = new CrossValidator(learningSetsCount, testingSetsCount, dataCreator);
            validator.startValidation(15);
        }

        CrossValidator validator = new CrossValidator(12, 2, dataCreator);
        double accuracy = validator.startValidation(40);
        System.out.println(String.format("\n\nAccumulated accuracy: %f", accuracy));

    }

    private static DataCreator getUserPreferences() {
        ConfigReader configReader = new ConfigReader();
        List<String> enableDatasets = configReader.getEnableDatasetsNames();
        Collections.sort(enableDatasets);
        Scanner scanner = new Scanner(System.in);
        for (String dataset : enableDatasets) {
            System.out.println(dataset);
        }
        System.out.println(enableDatasets.size() + 1 + ". Add new dataset.");
        System.out.println("Please enter your choice: ");
        int datasetNumber = scanner.nextInt();
        DataCreator dataCreator = null;
        if (datasetNumber != enableDatasets.size() + 1) {
            dataCreator = DataCreator.from(configReader.getFilePath(enableDatasets.get(datasetNumber - 1)));
        } else {
            System.out.println("Please enter path to file: ");
            String newPath = scanner.next();
            configReader.addPathToDataset(newPath);
            dataCreator = DataCreator.from(newPath);
        }
        return dataCreator;
    }

    private static int fetchCountOfLearningSets() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter count of learning sets: ");
        int learningSetsCount = scanner.nextInt();
        if (learningSetsCount <= 0) {
            return DEFAULT_LEARNING_SETS_COUNT;
        }
        return learningSetsCount;
    }

    private static int fetchCountOfTestingSets() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter count of testing sets: ");
        int testingSetsCount = scanner.nextInt();
        if (testingSetsCount <= 0) {
            return DEFAULT_TESTING_SETS_COUNT;
        }
        return testingSetsCount;
    }
}
