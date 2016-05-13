package pl.edu.pwr.elm;

import pl.edu.pwr.elm.model.IrisModel;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DataCreator {

    public DataCreator() {

    }

    public static List<IrisModel> readIrisData() {
        final Scanner in;
        final List<IrisModel> irisModelsList= new ArrayList<IrisModel>();
        try {
            in = new Scanner(new FileReader("iris.txt"));
            while (in.hasNext()) {
                final String[] columns = in.next().split(",");
                final IrisModel irisModel = new IrisModel();
                irisModel.setSepalLength(Double.valueOf(columns[0]));
                irisModel.setSepalWidth(Double.valueOf(columns[1]));
                irisModel.setPetalLength(Double.valueOf(columns[2]));
                irisModel.setPetalWidth(Double.valueOf(columns[3]));
                irisModel.setIrisType(columns[4]);
                irisModelsList.add(irisModel);
                System.out.println("iris type: " + irisModel.getIrisType());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return irisModelsList;
    }

    public static double[][] createInputs(List<IrisModel> irisModels) {
        double [][] inputs = new double[irisModels.size()][];
        for (int i = 0; i < irisModels.size(); i++) {
            double[] singleInput = {irisModels.get(i).getSepalLength(), irisModels.get(i).getSepalWidth(),
                    irisModels.get(i).getPetalLength(), irisModels.get(i).getPetalWidth()};
            inputs[i] = singleInput;
        }
        return inputs;
    }

    public static double [] createOutputs(List<IrisModel> irisModels) {
        double[] outputs = new double[irisModels.size()];
        for (int i = 0; i < irisModels.size(); i++) {
            outputs[i] = irisModels.get(i).getIrisTypeNumber();
        }
        return outputs;
    }
}
