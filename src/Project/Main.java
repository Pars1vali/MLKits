package Project;

import Project.Layers.Hidden;
import Project.Layers.Input;
import Project.Layers.Output;

public class Main {
    public static void main(String[] args) {

        Model model = new Model(new Layer[]{
                new Input(5),
                new Hidden(3),
                new Hidden(3),
                new Output(3)

        }, 0.1);

        model.train(new double[]{0.3,0.14,0.8,0.2,0.05}, new double[]{0,1,0});
        model.print();


    }
}
