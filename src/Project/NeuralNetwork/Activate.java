package Project.NeuralNetwork;

public class Activate {

    public static double step(double num){
        if(num < 0){
            return 0;
        }
        return 1;
    }
    public static double sigmoid(double num){
        return 1/(1+Math.exp(-num));
    }

    public static double tahn(double num){
        return 2 * sigmoid(2 * num) - 1;
    }

    public static double relu(double num){
        return Math.max(0, num);
    }
}
