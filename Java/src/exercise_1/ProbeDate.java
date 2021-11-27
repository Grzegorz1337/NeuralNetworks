package exercise_1;

import java.io.*;
import java.util.Random;
import java.util.Vector;

public class ProbeDate {
    private InputSet[][] input;
    private Vector<InputSet>[] inputSetVector;

    public Vector<InputSet>[] getInputSetVector() {
        return inputSetVector;
    }

    public void setInputSetVector(Vector<InputSet>[] inputSetVector) {
        this.inputSetVector = inputSetVector;
    }

    public void addInput(InputSet readed)
    {
        inputSetVector[readed.getDigit()].add(readed);
        String inputString = "";
        inputString += readed.getDigit();
        inputString += ";";
        inputString += readed.getResult();
        inputString += ";";
        for (int i = 0;i<30;i++) {
            inputString += readed.getInput()[i];
            inputString += ",";
        }
        inputString += "\n";
        try {
            FileWriter saveInputCase = new FileWriter("testCases.txt",true);
            BufferedWriter bw = new BufferedWriter(saveInputCase);
            bw.append(inputString);
            bw.flush();
            bw.close();
            saveInputCase.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void loadInputFromFile()
    {
        try {
            FileReader fr = new FileReader("testCases.txt");
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            while(line != null){
                String[] splitedLine = line.split(";");
                String[] splitedIntTab = splitedLine[2].split(",");
                int digit = Integer.parseInt(splitedLine[0]);
                int result = Integer.parseInt(splitedLine[1]);
                int input[] = new int[30];
                for (int i = 0;i<30;i++)
                {
                    input[i] = Integer.parseInt(splitedIntTab[i]);
                }
                inputSetVector[digit].add(new InputSet(input,result,digit, false));
                line = br.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Random generator;

    public ProbeDate() {
        generator = new Random();
        inputSetVector = new Vector[10];
        for(int i = 0;i<10;i++)
        {
            inputSetVector[i] = new Vector<InputSet>();
        }
    }

    public InputSet getNextProbeInput(int probingDigit){
        //System.out.print("Testcase: " + Math.abs(generator.nextInt())%inputSetVector[probingDigit].size() + ": ");
        return inputSetVector[probingDigit].elementAt(Math.abs(generator.nextInt())%inputSetVector[probingDigit].size());
    }

    public void clearTests(int recognisableDigit) {
        for(int i = 0;i < inputSetVector[recognisableDigit].size();i++)
        {
            inputSetVector[recognisableDigit].get(i).setPassed(false);
        }
    }

    public int getNotPassedTestNumber(int digit)
    {
        int RESULT = 0;
        for(int i = 0;i < inputSetVector[digit].size();i++)
        {
            if(!inputSetVector[digit].get(i).isPassed())
            {
                RESULT++;
            }
        }
        return RESULT;
    }
}
