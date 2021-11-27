package exercise_2;

import java.util.Random;

public class ImagePerceptron {

    private PerceptronWeights[] wagi;
    private double[] w;
    private double theta;
    private int id;

    public ImagePerceptron(int i) {
        result = new double[225];
        w = new double[225];
        this.id = i;
        resetWeights();
        wagi = new PerceptronWeights[2];
        wagi[0] = new PerceptronWeights(w,0);
        wagi[1] = new PerceptronWeights(w,0);
    }

    public double[] getResult() {
        return result;
    }

    public void setResult(double[] result) {
        this.result = result;
    }

    private double[] result;
    private static double N = 0.0001337;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public ImagePerceptron()
    {
        w = new double[225];
        resetWeights();
    }

    public ImagePerceptron(int id, double[] acceptableInput)
    {
        result = new double[225];
        w = new double[225];
        this.id = id;
        this.result = acceptableInput;
        resetWeights();
        wagi = new PerceptronWeights[2];
        wagi[0] = new PerceptronWeights(w,0);
        wagi[1] = new PerceptronWeights(w,0);
    }

    public double[] resetWeights()
    {
        Random gen = new Random();
        for(int i=0;i<225;i++)
        {
            w[i] = gen.nextDouble() / 100.0;
        }
        theta = gen.nextDouble();
        return w;
    }

    public double validateInput(double[] inputSet)
    {
        double O = 0;
        for(int i = 0;i<225;i++)
        {
            O += inputSet[i] * w[i];
        }
        //System.out.println((O - theta) + " < 0");
        return O-theta;
    }

    public void calculateWeights(double[] inputSet){
        int result = ((int) inputSet[id])==1?1:-1;
        double[] currentWeights;
        currentWeights = wagi[0].getWeights();
        for (long i = 0;i<100;i++)
        {
            double O = 0;
            double ERR;
            for(int j = 0;j<225;j++)
            {
                O += inputSet[j] * currentWeights[j];
            }
            O = O<theta?-1:1;
            ERR = (result) - O;

            if (ERR != 0 && wagi[0].getLifetime()>=wagi[1].getLifetime())
            {
                wagi[1].setWeights(currentWeights);
                wagi[1].setLifetime(wagi[0].getLifetime());
                for(int j = 0;j<225;j++)
                {
                    currentWeights[j] += N * ERR * inputSet[j];
                    theta -= N*ERR;
                }
            }
            else
            {
                wagi[0].setLifetime(wagi[0].getLifetime()+1);
                break;
            }

        }
    }
}
