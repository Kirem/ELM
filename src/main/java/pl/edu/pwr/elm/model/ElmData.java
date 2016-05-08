package pl.edu.pwr.elm.model;

public class ElmData {
    public static final String TAG = ElmData.class.getSimpleName();
    private double[][] elmInputDataList;
    private double[] elmOutputDataList;

    public ElmData(double[][] elmInputDataList, double[] elmOutputDataList) {
        this.elmInputDataList = elmInputDataList;
        this.elmOutputDataList = elmOutputDataList;
    }

    public double[][] getElmInputDataList() {
        return elmInputDataList;
    }

    public double[] getElmOutputDataList() {
        return elmOutputDataList;
    }
}
