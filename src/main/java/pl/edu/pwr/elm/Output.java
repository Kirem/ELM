package pl.edu.pwr.elm;

import java.util.Arrays;

public class Output {
    public static final String TAG = Output.class.getSimpleName();

    public double[] getOutput() {
        return output;
    }

    private double[] output;

    public Output(double[] output) {
        this.output = output;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Output)) return false;

        Output output1 = (Output) o;

        return Arrays.equals(output, output1.output);

    }

    @Override
    public String toString() {
        return Arrays.toString(output);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(output);
    }
}
