package pl.edu.pwr;

import org.ejml.ops.RandomMatrices;
import org.ejml.simple.SimpleMatrix;

import java.util.Random;

public class Starter {
    public static final String TAG = Starter.class.getSimpleName();

    public Starter() {
    }

    public static void main(String[] args) {
        SimpleMatrix matrix = new SimpleMatrix(RandomMatrices.createRandom(10, 10, new Random(234565)));
        System.out.println(matrix.mult(matrix.invert()).mult(matrix));
        System.out.println(matrix);
    }
}
