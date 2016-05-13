package pl.edu.pwr;

import pl.edu.pwr.elm.DataCreator;
import pl.edu.pwr.elm.ELM;
import pl.edu.pwr.elm.ElmConfig;
import pl.edu.pwr.elm.model.ElmData;
import pl.edu.pwr.elm.model.IrisModel;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Starter {
    public static final String TAG = Starter.class.getSimpleName();

    public Starter() {
    }

    public static void main(String[] args) {
        ElmConfig config = new ElmConfig();
        config.numberOfNodes = 4;
        config.numberOfInputClasses = 4;
        config.numberOfOutputClasses = 1;
        List<IrisModel> irisModels = DataCreator.readIrisData();
        double [][] inputs = DataCreator.createInputs(irisModels);
        double [] doubles = DataCreator.createOutputs(irisModels);

        ElmData elmData = new ElmData(inputs, doubles);
        ELM elm = new ELM(config, elmData);
        elm.train();
        System.out.println(elm.test(new double[]{5.7,3.0,4.2,1.2}));
        System.out.println(elm.test(new double[]{5.1,2.0,4.1,0.2}));
        System.out.println(elm.test(new double[]{5.0,4.0,3.2,2.2}));
        System.out.println(elm.test(new double[]{3.7,1.0,1.2,0.2}));
    }
}
