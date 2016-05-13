package pl.edu.pwr.elm.model;

public class IrisModel {
    private static final String IRIS_SETOSA = "Iris-setosa";
    private static final String IRIS_VERSICOLOR = "Iris-versicolor";
    private static final String IRIS_VIRGINICA = "Iris-virginica";

    private double sepalLength;
    private double sepalWidth;
    private double petalLength;
    private double petalWidth;
    private String irisType;

    public IrisModel() {
    }

    public String getIrisType() {
        return irisType;
    }

    public void setIrisType(String irisType) {
        this.irisType = irisType;
    }

    public double getSepalLength() {
        return sepalLength;
    }

    public void setSepalLength(double sepalLength) {
        this.sepalLength = sepalLength;
    }

    public double getSepalWidth() {
        return sepalWidth;
    }

    public void setSepalWidth(double sepalWidth) {
        this.sepalWidth = sepalWidth;
    }

    public double getPetalLength() {
        return petalLength;
    }

    public void setPetalLength(double petalLength) {
        this.petalLength = petalLength;
    }

    public double getPetalWidth() {
        return petalWidth;
    }

    public void setPetalWidth(double petalWidth) {
        this.petalWidth = petalWidth;
    }

    public double getIrisTypeNumber() {
        if(irisType.equals(IRIS_SETOSA)) {
            return 0;
        } else if(irisType.equals(IRIS_VERSICOLOR)) {
            return 1;
        } else {
            return 2;
        }
    }
}
