package Project.Kits;

import Project.NeuralNetwork.Layer;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

public class Matrix {
    private int row;
    private int column;
    public double[][] values;

    public Matrix(int row, int column, boolean init) {
        this.row = row;
        this.column = column;
        values = new double[row][column];
        initialize();
    }
    public Matrix(int row, int column) {
        this.row = row;
        this.column = column;
        values = new double[row][column];
    }
    public Matrix(Layer layer){
        this.row = 1;
        this.column = layer.getLength();
        this.values = new double[row][column];
        for (int i = 0; i < values[0].length; i++) {
            values[0][i] = layer.neurons[i].getValue();
        }
    }

    private void initialize() {
        IntStream.range(0, row)
                .forEach(r -> IntStream.range(0, column)
                        .forEach(c -> values[r][c] = new Random().nextDouble() - 0.5));

    }
    public static void print(Matrix matrix) {

        Arrays.stream(matrix.values)
                .forEach(row -> {
                    Arrays.stream(row).forEach(element-> System.out.printf(" %+1.2f", element));
                    System.out.println();
                });
        System.out.println("-----------");
    }
    public static Matrix multiply(Matrix m1, Matrix m2) {
        Matrix newMatrix = new Matrix(m1.row, m2.column);

        IntStream.range(0, newMatrix.row)
                .forEach(r -> IntStream.range(0, newMatrix.column)
                        .forEach(c -> {
                            for (int elem = 0; elem < m1.values[0].length; elem++) {
                                newMatrix.values[r][c] += m1.values[r][elem] * m2.values[elem][c];
                            }
                        }));

        return newMatrix;
    }

    @Override
    public String toString() {
        return "Test.Matrix{" +
                "row=" + row +
                ", column=" + column +
                ", values=" + Arrays.toString(Arrays.stream(values).toArray()) +
                '}';
    }
}
