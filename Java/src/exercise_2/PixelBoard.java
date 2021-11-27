package exercise_2;


import java.io.*;
import java.util.Vector;

public class PixelBoard {
    private int[][] boardInt;
    private char[][] boardChar;
    private double[] inputSet;

    PixelBoard(int[][] tab){
        boardInt = new int[15][15];
        boardInt = tab;
        boardChar = new char[15][15];
        inputSet = new double[225];
    }

    PixelBoard(){
        boardInt = new int[15][15];
        boardChar = new char[15][15];
        inputSet = new double[225];
    }

    PixelBoard(double[] tab){
        boardInt = new int[15][15];
        boardChar = new char[15][15];
        inputSet = new double[225];
        inputSet = tab;
    }

    PixelBoard(char[][] tab){
        boardInt = new int[15][15];
        boardChar = new char[15][15];
        boardChar = tab;
        inputSet = new double[225];
    }

    /**
     *
     * @param tab = Data set from board
     */
    public void safeInput(double[] tab)
    {
        String data = "";
        for(int i = 0;i<225;i++)
        {
            data += (int) tab[i];
            data += ";";
        }
        data += "\n";
        try {
            FileWriter saveInputCase = new FileWriter("testCasesEx_2.txt",true);
            BufferedWriter bw = new BufferedWriter(saveInputCase);
            bw.append(data);
            bw.flush();
            bw.close();
            saveInputCase.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Vector<String> getSavedImages()
    {
        Vector<String>fileContent = new Vector<>();
        try {
            FileReader fr = new FileReader("testCasesEx_2.txt");
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            while(line != null){
                fileContent.add(line);
                line = br.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileContent;
    }

}
