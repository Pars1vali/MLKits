package Project.NeuralNetwork;

import Project.Kits.Data;
import Project.Kits.Matrix;
import Project.Layers.Input;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Model {

    private Layer[] layers;
    private Matrix[] weights;
    private double[][] bias;
    private double a;
    private double error;

    public Model(Layer[] layers, double a) {
        this.layers = layers;
        this.a = a;
        initModel();
    }

    private void initModel() {
        createWeight();
        createBias();
    }

    private void createWeight() {
        AtomicInteger counter = new AtomicInteger(0);

        weights = Arrays.stream(new Matrix[layers.length - 1])
                .map(e -> {
                    int i = counter.getAndIncrement();
                    return new Matrix(layers[i].getLength(), layers[i + 1].getLength(), true);
                })
                .toArray(Matrix[]::new);
        //Arrays.stream(weights).forEach(m-> Matrix.print(m));
    }

    private void createBias() {
        bias = Arrays.stream(layers)
                .skip(1)
                .map(layer -> new Random().doubles(layer.getLength(),1,10).toArray())
                .toArray(double[][]::new);
    }

    public void train(int epoch, Data data){
        layers[0].setValueLayer(data.getInput());

        for (int iter = 0; iter < epoch; iter++) {
            //расчитать значения нейронов скрытых и выходного слоя

            for (int i = 0; i < weights.length; i++) {
                layers[i + 1].setValueNeuron(Matrix.multiply(new Matrix(layers[i]), weights[i]), bias[i]);
            }

            // расчитать ошибку одного прогона
            AtomicInteger counter = new AtomicInteger(0);
            error = Arrays.stream(layers[layers.length - 1].neurons)
                .map(neuron -> {
                    int i = counter.getAndIncrement();
                    return Math.pow(neuron.getValue() - data.getOutput()[i], 2);
                })
                .reduce(0.0, Double::sum);
        }
    }






//    public void train(double[] valuesInput, double[] valuesOutput) {
//
//        predict(valuesInput);
//
//        AtomicInteger counter = new AtomicInteger(0);
//
//        error = Arrays.stream(layers[layers.length - 1].neurons)
//                .map(neuron -> {
//                    int i = counter.getAndIncrement();
//                    return Math.pow(neuron.getValue() - valuesOutput[i], 2);
//                })
//                .reduce(0.0, Double::sum);
//
//        System.out.println(weights.length + " " + layers.length + " " + bias.length);
//        correctParameters(valuesOutput);
//        double dif_1 = 2 * layers[layers.length - 1].neurons[0].getValue() - valuesOutput[0];
//        double dif_2 = 1 / 1 + Math.exp(-layers[layers.length - 1].neurons[0].getValueWithOutActivate());
//        double dif_3 = layers[layers.length - 2].neurons[0].getValue();
//        double gradient = a * (dif_1 * dif_2 * dif_2);
//    }
//
//    private void correctParameters(double[] valuesOutput) {
//        for (int i = weights.length - 1; i >= 0; i--) {
//            Matrix weight_l = weights[i];
//
//            Layer layer_l = layers[i + 1];
//            Layer layer = layers[i];
//            double[] bias_l = bias[i];
//
//            double gradient = gradientWeight(weight_l, layer_l, layer, bias_l,valuesOutput);
//
//        }
//
//    }
//
//    private double gradientWeight(Matrix weight_l, Layer layer_l, Layer layer, double[] bias_l, double[] valuesOutput) {
//        double gradient = 0;
//
//
//        double dif_1 = 2 * layers[layers.length - 1].neurons[0].getValue() - valuesOutput[0];
//        double dif_2 = 1 / 1 + Math.exp(-layers[layers.length - 1].neurons[0].getValueWithOutActivate());
//        double dif_3 = layers[layers.length - 2].neurons[0].getValue();
//
//
//        return gradient;
//    }
//
//    public void predict(double[] valuesInput) {
//        layers[0].setValueLayer(valuesInput);
//
//        for (int i = 0; i < weights.length; i++) {
//            Matrix matrix = Matrix.multiply(new Matrix(layers[i]), weights[i]);
//            layers[i + 1].setValueNeuron(matrix, bias[i]);
//        }
//    }

    public void print() {
        for (int i = 0; i < weights.length; i++) {
            System.out.println(layers[i].toString());
            Matrix.print(weights[i]);
            System.out.println("Beas - ");
            Arrays.stream(bias[i]).forEach(e-> System.out.print(" " + String.format("%1.0f",e)));
            System.out.println("\n" + layers[i + 1]);
        }
        System.out.println("Error = " + error);
    }
}
