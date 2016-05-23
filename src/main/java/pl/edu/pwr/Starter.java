package pl.edu.pwr;

import pl.edu.pwr.elm.CrossValidator;
import pl.edu.pwr.elm.DataCreator;
import pl.edu.pwr.elm.ELM;
import pl.edu.pwr.elm.ElmConfig;
import pl.edu.pwr.elm.model.ElmData;

public class Starter {
    public static final String TAG = Starter.class.getSimpleName();

    public Starter() {
    }

    public static void main(String[] args) {
        ElmConfig config = new ElmConfig();
        config.numberOfNodes = 15;
//        List<IrisModel> irisModels = DataCreator.readIrisData();
//        double [][] inputs = DataCreator.createInputs(irisModels);
//        config.numberOfInputClasses = inputs[0].length;
//        config.numberOfOutputClasses = 1;
//        double [] doubles = DataCreator.createOutputs(irisModels);

        DataCreator dataCreator = DataCreator.from("iris.txt");
        dataCreator.readData();
////        DataCreator.readData("mammographicDataSet.txt");
//
//        double[][] inputs = DataCreator.getDataInputRows();
//        double[] doubles = DataCreator.getOutputs();
        config.numberOfOutputClasses = dataCreator.getNumberOfOutputClasses();
        config.numberOfInputClasses = dataCreator.getNumberOfInputsClasses();
//
        ElmData elmData = new ElmData(dataCreator.getDataInputRows(), dataCreator.getOutputValues());
        ELM elm = new ELM(config, elmData);
        elm.train();
        CrossValidator validator = new CrossValidator(2,1, dataCreator);
        validator.startValidation(15);

//        IRIS TEST DATA
//        System.out.println(Arrays.toString(elm.test(new double[]{5.1, 3.5, 1.4, 0.2})));
//        System.out.println(elm.test(new double[]{7.0,3.2,4.7,1.4}));
//        System.out.println(elm.test(new double[]{5.8,2.7,5.1,1.9}));
//        System.out.println(elm.test(new double[]{6.3,3.3,6.0,2.5}));
    }
}
