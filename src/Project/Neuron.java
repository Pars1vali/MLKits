package Project;

import java.util.function.Function;

public class Neuron {

    private double value;
    private double valueWithOutActivate;

    public Neuron() {
        this.value = 0;
    }
    public Neuron(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }
    public void setValueWithOutActivate(double valueWithOutActivate) {
        this.valueWithOutActivate = valueWithOutActivate;
    }
    public double getValueWithOutActivate() {
        return valueWithOutActivate;
    }

    @Override
    public String toString() {
        return "Test.Neuron{" +
                "value=" + value +
                '}';
    }
}
