package pl.edu.pwr.elm.model;

import java.util.List;

public class ElmDataRow {
    public static final String TAG = ElmDataRow.class.getSimpleName();
    private List<Float> values;

    public ElmDataRow(List<Float> values) {
        this.values = values;
    }

    public List<Float> getValues() {
        return values;
    }
}
