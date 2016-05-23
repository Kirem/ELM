package pl.edu.pwr.elm.model;

import java.util.List;

import pl.edu.pwr.elm.Output;

public class ElmData {
    public static final String TAG = ElmData.class.getSimpleName();
    private double[][] elmInputDataList;
    private List<Output> elmOutputDataList;

    public ElmData(double[][] elmInputDataList, List<Output> elmOutputDataList) {
        this.elmInputDataList = elmInputDataList;
        this.elmOutputDataList = elmOutputDataList;
    }

    public double[][] getElmInputDataList() {
        return elmInputDataList;
    }

    public List<Output> getElmOutputDataList() {
        return elmOutputDataList;
    }
}
