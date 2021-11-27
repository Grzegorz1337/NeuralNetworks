package exercise_3;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Ex_3_Input {
    private int[][] data;

    public Ex_3_Input(int[][] data) {
        this.data = data;
    }

    public Ex_3_Input(String parseGridToString) {
        safeInFile(parseGridToString);
    }

    public int[][] getData() {
        return data;
    }

    public void setData(int[][] data) {
        this.data = data;
    }

    public void safeInFile(String inputString)
    {
        try {
            FileWriter saveInputCase = new FileWriter("testCasesEx_3.txt",true);
            BufferedWriter bw = new BufferedWriter(saveInputCase);
            bw.append(inputString);
            bw.flush();
            bw.close();
            saveInputCase.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
