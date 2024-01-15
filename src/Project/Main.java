package Project;

import Project.Kits.Data;
import Project.NeuralNetwork.Layer;
import Project.Layers.Hidden;
import Project.Layers.Input;
import Project.Layers.Output;
import Project.NeuralNetwork.Model;
import Reader.MnistDataReader;
import Reader.MnistMatrix;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {


            MnistMatrix[] mnistMatrix = new MnistDataReader().readData("data/train-images.idx3-ubyte", "data/train-labels.idx1-ubyte");
            printMnistMatrix(mnistMatrix[mnistMatrix.length - 1]);
            mnistMatrix = new MnistDataReader().readData("data/t10k-images.idx3-ubyte", "data/t10k-labels.idx1-ubyte");
            printMnistMatrix(mnistMatrix[0]);




        Model model = new Model(new Layer[]{
                new Input(3),
                new Hidden(2),
                new Output(2)

        }, 0.01);

        Data data = new Data(new double[]{0.3,0.14,5.8}, new double[]{0,1});

        model.train(100, data);
        model.print();


    }

    private static void printMnistMatrix(final MnistMatrix matrix) {
        System.out.println("label: " + matrix.getLabel());
        for (int r = 0; r < matrix.getNumberOfRows(); r++ ) {
            for (int c = 0; c < matrix.getNumberOfColumns(); c++) {
                System.out.print(matrix.getValue(r, c) + " ");
            }
            System.out.println();
        }
    }
}
