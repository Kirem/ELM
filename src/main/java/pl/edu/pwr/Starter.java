package pl.edu.pwr;

import pl.edu.pwr.elm.CrossValidator;
import pl.edu.pwr.elm.DataCreator;

public class Starter {
    public static final String TAG = Starter.class.getSimpleName();

    public Starter() {
    }

    public static void main(String[] args) {
        DataCreator dataCreator = DataCreator.from("bloodTransfusionDataSet.txt");
        dataCreator.readData();

        CrossValidator validator = new CrossValidator(12,2, dataCreator);
        double accuracy = validator.startValidation(40);
        System.out.println(String.format("accuracy: %f", accuracy));

    }
}
