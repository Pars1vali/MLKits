package Project.NeuralNetwork;

import Project.Kits.Data;
import Project.Kits.Matrix;

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

        Arrays.stream(weights).forEach(m-> Matrix.print(m));
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



            //Заполняем слои нейроной сети
            for (int i = 0; i < weights.length; i++) {
                layers[i + 1].setValueNeuron(Matrix.multiply(new Matrix(layers[i]), weights[i]), bias[i]);
            }

            //Ошибка нейроной сети
            AtomicInteger counter = new AtomicInteger(0);
            error = Arrays.stream(layers[layers.length - 1].neurons)
                .map(neuron -> {
                    int i = counter.getAndIncrement();
                    return Math.pow(neuron.getValue() - data.getOutput()[i], 2);
                })
                .reduce(0.0, Double::sum);

            calcDeltaWeight(data);
            calcDeltaBias(data);
           // System.out.println("iter - " + iter + ", error = " + error);


        }
    }



    private void calcDeltaWeight(Data data) {
        //Опредлеяем delta для весов нейроной сети
        //обходим массив весов, представленных в виде матрицы, обходим с конца в начаол так как обратное распроастнение ошибки
        for (int i = weights.length - 1; i >= 0; i--) {

            //после получения матрицы весов нам необходимо получить элементы ее и передать их в функцию опредления градиента,  а также передать значения правилное для этого нейрона
            for (int row = 0; row < weights[i].values.length; row++) {
                for (int col = 0; col < weights[i].values[row].length; col++) {
                    double delta = calcGradientWeight(col, row, i + 1, data);
                    weights[i].values[row][col] += delta;
                }
            }
        }
    }
    private void calcDeltaBias(Data data) {

        for (int row = bias.length - 1; row >= 0; row--) { //проходимся по массиву смещений нейронов для слоёв
            for (int col = 0; col < bias[row].length; col++) {
                bias[row][col] += calcGradientBias(row, col, data);
            }

        }
    }


    private double calcGradientWeight(int numberNeuronOutput, int numberNeuronInput, int numberLayout, Data data){
        double gradient;
        double dif_1 =  2 * (layers[numberLayout].neurons[numberNeuronOutput].getValue() - data.getOutput()[numberNeuronOutput]);
        double dif_2 = 1 / 1 + Math.exp(-(layers[numberLayout].neurons[numberNeuronOutput].getValueWithOutActivate()));
        double dif_3 = layers[numberLayout - 1].neurons[numberNeuronInput].getValue();

        gradient = a * (dif_1 * dif_2 * dif_3);
        System.out.println(numberLayout + " " + numberNeuronInput + " " + numberNeuronOutput + " " + data.getOutput()[numberNeuronOutput]);
        System.out.println("gradient - " + gradient);
        return gradient;
    }
    private double calcGradientBias(int numberLayout, int numberNeuron, Data data) {
        double gradient;
        double dif_1 =  2 * (layers[numberLayout].neurons[numberNeuron].getValue() - data.getOutput()[numberNeuron]);
        double dif_2 = 1 / 1 + Math.exp(-layers[numberLayout].neurons[numberNeuron].getValueWithOutActivate());


        gradient = a * (dif_1 * dif_2);

        return gradient;
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
            Arrays.stream(bias[i]).forEach(e-> System.out.print(" " + String.format("%1.3f",e)));
            System.out.println("\n" + layers[i + 1]);
        }
        System.out.println("Error = " + error);
    }
}
