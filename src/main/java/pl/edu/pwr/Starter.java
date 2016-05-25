package pl.edu.pwr;

import pl.edu.pwr.elm.*;
import pl.edu.pwr.elm.model.ElmData;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.jar.Pack200;

public class Starter {
    public static final String TAG = Starter.class.getSimpleName();

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

//        IRIS TEST DATA
//        System.out.println(Arrays.toString(elm.test(new double[]{5.1, 3.5, 1.4, 0.2})));
//        System.out.println(elm.test(new double[]{7.0,3.2,4.7,1.4}));
//        System.out.println(elm.test(new double[]{5.8,2.7,5.1,1.9}));
//        System.out.println(elm.test(new double[]{6.3,3.3,6.0,2.5}));
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
        if(datasetNumber != enableDatasets.size() + 1) {
            dataCreator = DataCreator.from(configReader.getFilePath(enableDatasets.get(datasetNumber - 1)));
        } else {
            System.out.println("Please enter path to file: ");
            String newPath =  scanner.next();
            configReader.addPathToDataset(newPath);
            dataCreator = DataCreator.from(newPath);
        }
        return dataCreator;
    }

    private static int fetchCountOfLearningSets() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter count of learning sets: ");
        return scanner.nextInt();
    }

    private static int fetchCountOfTestingSets() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter count of testing sets: ");
        return scanner.nextInt();
    }
}
