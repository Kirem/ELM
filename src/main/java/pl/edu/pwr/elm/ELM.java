package pl.edu.pwr.elm;

import org.ejml.simple.SimpleMatrix;

import pl.edu.pwr.elm.model.ElmData;
import pl.edu.pwr.elm.model.ElmDataRow;

public class ELM {
    public static final String TAG = ELM.class.getSimpleName();
    private final ElmConfig config;
    private final ElmData elmData;
    private SimpleMatrix inputMatrix;
    private SimpleMatrix outputMatrix;

    public ELM(ElmConfig config, ElmData elmData) {
        ConfigValidator.with(config).validate();
        this.elmData = elmData;
        this.config = config;
        initValues();
    }

    private void initValues() {
        inputMatrix = new SimpleMatrix();
        outputMatrix = new SimpleMatrix();
        Float[] values;
        for (ElmDataRow elmDataRow : elmData.getElmInputDataList()) {
            values = elmDataRow.getValues().toArray(new Float[0]);
        }

    }

    public void train() {

    }

}