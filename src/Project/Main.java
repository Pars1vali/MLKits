package Project;

import Project.Kits.Data;
import Project.NeuralNetwork.Layer;
import Project.Layers.Hidden;
import Project.Layers.Input;
import Project.Layers.Output;
import Project.NeuralNetwork.Model;

public class Main {
    public static void main(String[] args) {

        Model model = new Model(new Layer[]{
                new Input(3),
                new Hidden(2),
                new Output(2)

        }, 0.01);

        Data data = new Data(new double[]{0.3,0.14,0.8}, new double[]{0,1});

        model.train(100, data);
        model.print();


    }
}
