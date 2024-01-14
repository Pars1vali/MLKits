package Project.NeuralNetwork;

import Project.Kits.Matrix;

import java.util.Arrays;

public class Layer {

    public Neuron[] neurons;

    public Layer(int length) {
        neurons = Arrays.stream(new Neuron[length]).map(e -> e = new Neuron()).toArray(Neuron[]::new);
    }

    public int getLength(){
        return neurons.length;
    }
    public void setValueLayer(double[] valueNeuron){
        for (int i = 0; i < neurons.length; i++) {
            neurons[i] = new Neuron(valueNeuron[i]);
        }
    }
    public void setValueNeuron(Matrix matrix, double[] bias) {

        for (int i = 0; i < neurons.length; i++) {
            double z = matrix.values[0][i] + bias[i];
            Neuron neuron = new Neuron(Activate.sigmoid(z));
            neuron.setValueWithOutActivate(z);
            neurons[i] = neuron;
        }
    }

    @Override
    public String toString() {
        return "Test.Layer{" +
                "neurons=" + Arrays.toString(neurons) +
                '}';
    }
}
