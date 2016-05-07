package pl.edu.pwr.elm.model;

import java.util.List;

public class ElmData {
    public static final String TAG = ElmData.class.getSimpleName();
    private List<ElmDataRow> elmInputDataList;
    private List<Float> elmOutputDataList;

    public ElmData(List<ElmDataRow> elmInputDataList, List<Float> elmOutputDataList) {
        this.elmInputDataList = elmInputDataList;
        this.elmOutputDataList = elmOutputDataList;
    }

    public List<ElmDataRow> getElmInputDataList() {
        return elmInputDataList;
    }
}
