package exercise_1;

import javafx.scene.control.TextArea;

import java.util.Random;

public class DigitPerceptrons {
    private TextArea outputStream;
    private ProbeDate probe;
    private Random gen;
    private double[] w;
    private int recognisableDigit;
    private static final double N = 0.000002337;
    private double acceptableLimes;

    public TextArea getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(TextArea outputStream) {
        this.outputStream = outputStream;
    }

    public ProbeDate getProbe() {
        return probe;
    }

    public void setProbe(ProbeDate probe) {
        this.probe = probe;
        calculateWeights();
    }

    public DigitPerceptrons(ProbeDate probe, TextArea output, int recognisableDigit){
        outputStream = output;
        this.probe = probe;
        this.recognisableDigit = recognisableDigit;
        w = new double[30];
        gen = new Random();
        acceptableLimes = gen.nextDouble() * 10;
        resetWeights();
    }

    private void resetWeights()
    {
        for (int i  = 0;i<30;i++)
        {
            w[i] = gen.nextDouble() * 2;
        }
    }

    private void calculateWeights()
    {
        resetWeights();
        probe.clearTests(recognisableDigit);
        for (long i = 0;i<100000000;i++)
        {
            if(probe.getNotPassedTestNumber(recognisableDigit)==0)
            {
                break;
            }
            double O = 0;
            double ERR;
            InputSet inCase = probe.getNextProbeInput(recognisableDigit);
            for(int j = 0;j<30;j++)
            {
                O += inCase.getInput()[j] * w[j];
            }
            O = O<acceptableLimes?-1:1;
            ERR = inCase.getResult() - O;
            System.out.println(inCase.getResult() + " dla " + recognisableDigit + " a Theta: " + acceptableLimes);
            if (ERR != 0)
            {
                //System.out.println(probe.getNotPassedTestNumber(recognisableDigit) + "\t | \t" + i + "\t | \t" + ERR);
                probe.getInputSetVector()[recognisableDigit].elementAt(probe.getInputSetVector()[recognisableDigit].indexOf(inCase)).setPassed(false);
                //System.out.println("Got: \t" + O + " \tBlad: \t" + ERR + "\t Prog: \t" + acceptableLimes);
                for(int j = 0;j<30;j++)
                {
                    w[j] += N * ERR * inCase.getInput()[j];
                    acceptableLimes -= N*ERR;
                }
            }
            else
            {
                probe.getInputSetVector()[recognisableDigit].elementAt(probe.getInputSetVector()[recognisableDigit].indexOf(inCase)).setPassed(true);
                probe.getInputSetVector()[recognisableDigit].elementAt(probe.getInputSetVector()[recognisableDigit].indexOf(inCase)).setTheta(acceptableLimes);
            }
            probe.getInputSetVector()[recognisableDigit].elementAt(probe.getInputSetVector()[recognisableDigit].indexOf(inCase)).setTheta(acceptableLimes);

        }
        outputStream.setText(outputStream.getText() + "Weights recalculated for " + recognisableDigit + "\n");
    }

    public int getRecognisedDigit(InputSet in)
    {
        double RES = 0;
        for(int i = 0;i<30;i++)
        {
            RES+= w[i] * in.getInput()[i];
        }
        System.out.println("Perceptron number " + recognisableDigit + ": " +RES + " < " + acceptableLimes + " ?");
        RES = RES<acceptableLimes?-1:1;
        if(RES ==1) {
            outputStream.setText(outputStream.getText()+ "\nPrzypomina to " + recognisableDigit);
            return 1;
        }
        else {
            outputStream.setText(outputStream.getText()+ "\nNIE przypomina to " + recognisableDigit);
            return -1;
        }
    }
}
