package pl.edu.pwr;

import pl.edu.pwr.elm.ELM;
import pl.edu.pwr.elm.ElmConfig;
import pl.edu.pwr.elm.model.ElmData;

public class Starter {
    public static final String TAG = Starter.class.getSimpleName();

    public Starter() {
    }

    public static void main(String[] args) {
        ElmConfig config = new ElmConfig();
        config.numberOfNodes = 3;
        config.numberOfInputClasses = 2;
        config.numberOfOutputClasses = 1;
        double[][] inputs = {
                {1, 1},
                {1, 0},
                {0, 1},
                {0, 0}
        };
        double[] doubles = {
                1, 1, 1,0
        };
        ElmData elmData = new ElmData(inputs, doubles);
        ELM elm = new ELM(config, elmData);
        elm.train();
        System.out.println(elm.test(new double[]{1, 0}));
        System.out.println(elm.test(new double[]{1, 1}));
        System.out.println(elm.test(new double[]{0, 1}));
        System.out.println(elm.test(new double[]{0, 0}));
    }
}
