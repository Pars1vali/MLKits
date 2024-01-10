package Project;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Model {

    private Layer[] layers; // ���� �������� ����
    private Matrix[] weights; // ���� ����� ������
    private double[] bias; //�������� ��� ������� ����
    private double a; //�������� ��������
    private double error; // ������ �������� ����

    public Model(Layer[] layers, double a) { // ��� �������� ��������� �������� ���� ���������� ������� ����������� ���� � ��� � �������� ��������
        this.layers = layers;
        this.a = a;
        initModel();
    }

    private void initModel() {
        createWeight(); // ������� ���� ����� ������
        createBias(); // ������� �������� ��� ������� ����
    }

    private void createWeight() {
        AtomicInteger counter = new AtomicInteger(0);

        weights = Arrays.stream(new Matrix[layers.length - 1])
                .map(e -> {
                    int i = counter.getAndIncrement();
                    return new Matrix(layers[i].getLength(), layers[i + 1].getLength(), true);
                })
                .toArray(Matrix[]::new);
    }

    private void createBias() {
        // ������ �������� �������� ��� ������� ����
        bias = new Random().doubles(layers.length, 1, 10).toArray();
    }

    public void train(double[] valuesInput, double[] valuesOutput) { // ������ �������� ���������� �������� ���� ��� ������� �������� ������� ������ ����� ������������
        layers[0].setValueLayer(valuesInput); // ���� ������� �������� - Test.Layers.Input

        //�������������� �������� ����
        for (int i = 0; i < weights.length; i++) { // �������� �������� �������� ��� ������� ����
            Matrix matrix = Matrix.multiply(new Matrix(layers[i]), weights[i]); // �������� �������� �������� �� ���� �� ��� � ������� ������� �������� �������� ��� ���������� ����
            layers[i + 1].setValueNeuron(matrix, bias[i]); // ������������ ���� �������� �� �������� �� ���������� �������, ����������� �� �������� - bias
        }

        //������ �������� ����
        AtomicInteger counter = new AtomicInteger(0);

        error = Arrays.stream(layers[layers.length - 1].neurons)
                .map(neuron -> {
                    int i = counter.getAndIncrement();
                    return Math.pow(neuron.getValue() - valuesOutput[i], 2);
                })
                .reduce(0.0, Double::sum);



        double dif_1 = 2 * layers[layers.length - 1].neurons[0].getValue() - valuesOutput[0];
        double dif_2 = 1 / 1 + Math.exp(-layers[layers.length - 1].neurons[0].getValueWithOutActivate());
        double dif_3 = layers[layers.length - 2].neurons[0].getValue();
        double gradient = a * (dif_1 * dif_2 * dif_2);
    }

    public void print() {
        for (int i = 0; i < weights.length; i++) {
            System.out.println(layers[i].toString());
            Matrix.print(weights[i]);
            System.out.println("Bias = " + String.format("%1.0f", bias[i]));
            System.out.println(layers[i + 1]);
        }
        System.out.println("Error = " + error);
    }
}
