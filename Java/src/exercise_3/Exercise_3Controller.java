package exercise_3;

import exercise_2.ImagePerceptron;
import exercise_2.PixelBoard;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class Exercise_3Controller {


    @FXML public Pane pixelsPane;
    @FXML public VBox vButtonsContainer;
    @FXML public Button[][] buttonBoard;
    @FXML public ChoiceBox digitSelection;
    private int[][] grid;
    private Vector<Ex_3_Input> learningData;


    @FXML void initialize()
    {
        learningData = new Vector<>();
        vButtonsContainer = new VBox();
        grid = new int[5][5];
        buttonBoard = new Button[5][5];
        for(int i = 0;i<5;i++)
        {
            HBox hButtonsContainer = new HBox();
            for(int j = 0;j<5;j++)
            {
                buttonBoard[i][j] = new Button();
                buttonBoard[i][j].setUserData("w");
                buttonBoard[i][j].setStyle("-fx-border-color: grey;" +
                        "-fx-border-width: 1px; " +
                        "-fx-border-style: solid;" +
                        "-fx-background-color: white");
                buttonBoard[i][j].setPrefSize(80,80);
                buttonBoard[i][j].setMinSize(5,5);
                int finalI = i;
                int finalJ = j;
                buttonBoard[i][j].setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        if("w".equals(buttonBoard[finalI][finalJ].getUserData().toString()))
                        {
                            buttonBoard[finalI][finalJ].setUserData('b');
                            buttonBoard[finalI][finalJ].setStyle("-fx-border-color: grey;" +
                                    "-fx-border-width: 1px; " +
                                    "-fx-border-style: solid;" +
                                    "-fx-background-color: black");
                        }
                        else
                        {
                            buttonBoard[finalI][finalJ].setUserData('w');
                            buttonBoard[finalI][finalJ].setStyle("-fx-border-color: grey;" +
                                    "-fx-border-width: 1px; " +
                                    "-fx-border-style: solid; " +
                                    "-fx-background-color: white");
                        }
                    }
                });
                hButtonsContainer.getChildren().add(buttonBoard[i][j]);
            }
            vButtonsContainer.getChildren().add(hButtonsContainer);
        }
        pixelsPane.getChildren().add(vButtonsContainer);
    }

    private int[] transform2DGridTo1D(int[][] grid2d)
    {
        int k = 0;
        int[] grid1d = new int[25];
        for(int i = 0;i<5;i++)
        {
            for(int j = 0;j<5;j++)
            {
                grid1d[k] = grid2d[i][j];
                k++;
            }
        }
        return grid1d;
    }

    private int[][] transform1DGridTo2D(int[] grid1d)
    {
        int k = 0;
        int[][] grid2d = new int[5][5];
        for(int i = 0;i<5;i++)
        {
            for(int j = 0;j<5;j++)
            {
                grid2d[i][j] = grid1d[k];
                k++;
            }
        }
        return grid2d;
    }

    private int[][] transformButtonGridToInt(Button[][] grid)
    {
        int[][] intGrid = new int[5][5];
        for(int i = 0;i<5;i++)
        {
            for(int j = 0;j<5;j++)
            {
                intGrid[i][j] = "w".equals(grid[i][j].getUserData().toString())?0:1;
            }
        }
        return intGrid;
    }

    private String parseGridToString(Button[][] grid, int digit, int value)
    {
        return doParseGridToString(transform2DGridTo1D(transformButtonGridToInt(grid)),digit,value);
    }

    private String parseGridToString(int[][] grid, int digit, int value)
    {
        return doParseGridToString(transform2DGridTo1D(grid),digit,value);
    }

    private String parseGridToString(int[] grid, int digit, int value)
    {
        return doParseGridToString(grid,digit,value);
    }

    private String doParseGridToString(int[] grid, int digit, int value)
    {
        String output = "";
        output+=digit;
        output+=";";
        output+=value;
        output+=";";
        for(int i = 0;i<25;i++)
        {
            output+=grid[i];
            output+=",";
        }
        output+="\n";
        return output;
    }

    public void loadData()
    {
        Vector<String>fileContent = new Vector<>();
        try {
            FileReader fr = new FileReader("testCasesEx_3.txt");
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            while(line != null){
                addInputToData(line);
                fileContent.add(line);
                line = br.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addInputToData(String line) {
        String[] lineSplit = line.split(";");
    }

    public void saveDigit(ActionEvent actionEvent) {
        int selectedDigit = Integer.parseInt(digitSelection.getValue().toString());
        for(int i = 0;i<=9;i++)
        {
            learningData.add(new Ex_3_Input(parseGridToString(buttonBoard,i,selectedDigit==i?1:-1)));
        }
        learnPerceptrons();
    }

    private void learnPerceptrons() {
    }
}
