package exercise_2;

import com.sun.org.apache.xpath.internal.operations.Bool;
import exercise_1.ProbeDate;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.*;

public class Exercise_2Controller {

    @FXML public Pane pixelsPane;
    @FXML public VBox rightPanel;
    @FXML public Button clearButton;

    private PixelBoard pixelBoard;
    private Button[][] buttonBoard;
    private Vector<ImagePerceptron> perceptrons;
    private Vector<String> file;

    @FXML void initialize()
    {

        pixelBoard = new PixelBoard();
        buttonBoard = new Button[15][15];
        perceptrons = new Vector<>();
        HBox wiersze = new HBox();
        for(int i = 0;i < 15;i++) {
            VBox kolumny = new VBox();
            for(int j = 0;j < 15;j++)
            {
                buttonBoard[i][j] = new Button();
                buttonBoard[i][j].setUserData("w");
                buttonBoard[i][j].setStyle("-fx-border-color: grey;" +
                        "-fx-border-width: 1px; " +
                        "-fx-border-style: solid;" +
                        "-fx-background-color: white");
                buttonBoard[i][j].setPrefSize(39,39);
                buttonBoard[i][j].setMinSize(5,5);
                int finalI = i;
                int finalJ = j;
                buttonBoard[i][j].setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        if("w".equals(buttonBoard[finalI][finalJ].getUserData().toString()))
                        {
                            buttonBoard[finalI][finalJ].setUserData("b");
                            buttonBoard[finalI][finalJ].setStyle("-fx-border-color: grey;" +
                                    "-fx-border-width: 1px; " +
                                    "-fx-border-style: solid;" +
                                    "-fx-background-color: black");
                        }
                        else
                        {
                            buttonBoard[finalI][finalJ].setUserData("w");
                            buttonBoard[finalI][finalJ].setStyle("-fx-border-color: grey;" +
                                    "-fx-border-width: 1px; " +
                                    "-fx-border-style: solid; " +
                                    "-fx-background-color: white");
                        }
                    }
                });
            }
            kolumny.getChildren().addAll(buttonBoard[i]);
            wiersze.getChildren().add(kolumny);
        }
        pixelsPane.getChildren().add(wiersze);

        file = pixelBoard.getSavedImages();
        loadInputsFromFile(file);
        learnPerceptrons();
        paintImageOnBoard(parseLineToDoubleTab(file.elementAt(0)));
    }

    public void clearBoard(ActionEvent actionEvent) {
        for(int i = 0;i<15;i++) {
            for(int j = 0;j<15;j++){
                buttonBoard[i][j].setStyle("-fx-border-color: grey;-fx-border-width: 1px; -fx-border-style: solid; -fx-background-color: white");
                buttonBoard[i][j].setUserData("w");
            }
        }

    }

    public void safeInput(ActionEvent actionEvent) {
        pixelBoard.safeInput(parseInputToDoubleTab());
        learnPerceptrons();
        //clearBoard(null);
    }

    private double[] parseInputToDoubleTab()
    {
        double[] send = new double[225];
        int k = 0;
        for(int i = 0;i < 15;i++)
        {
            for(int j = 0;j < 15;j++)
            {
                send[k] = buttonBoard[i][j].getUserData().toString()=="w"?0:1;
                k++;
            }
        }
        return send;
    }

    private void printCurrentBoard()
    {
        for(int i = 0;i < 15;i++)
        {
            for(int j = 0;j < 15;j++)
            {
                System.out.print(buttonBoard[i][j].getUserData().toString()=="w"?0:1);
            }
            System.out.println();
        }
    }
    private double[] parseLineToDoubleTab(String send)
    {
        double[] sendDouble = new double[225];
        for(int j = 0;j<225;j++)
        {
            sendDouble[j] = Double.parseDouble(send.split(";")[j]);
        }
        return sendDouble;
    }

    private void loadInputsFromFile(Vector<String> file)
    {
        for(int i = 0;i<225;i++)
        {
            perceptrons.add(new ImagePerceptron(i));
        }
    }

    private void learnPerceptrons()
    {
        if(file.size()==0) return;
        Random ge = new Random();
        for(int i=0;i<perceptrons.size();i++)
        {
            perceptrons.elementAt(i).resetWeights();
            System.out.println("------------------------------------------------------------\nLearning perceptron number: " + i + "\n------------------------------------------------------------\n");
            for(int j=0;j<file.size()*100;j++)
            {
                int k = abs(ge.nextInt(file.size()));
                String line = file.elementAt(k);
                String inputString = line;
                double[] inputDouble = parseLineToDoubleTab(inputString);
                //System.out.println("Learning line " + k + " with expected result: " + inputDouble[i]);
                perceptrons.elementAt(i).calculateWeights(inputDouble);
                //System.out.println("Test " + perceptrons.elementAt(i).validateInput(inputDouble));
            }
        }
    }

    public void recogniseImage(ActionEvent actionEvent) {
        rightPanel.getChildren().clear();
        Button b = new Button();
        Boolean anyoneAccepted = false;
        double min = 100;
        int maxI = 0;
        for(int i = 0;i<225;i++)
        {
            if(perceptrons.elementAt(i).validateInput(parseInputToDoubleTab())>0)
            {
                if(buttonBoard[i/15][i%15].getUserData().equals("w")){
                    buttonBoard[i/15][i%15].fire();
                }
            }
            else
            {
                if(buttonBoard[i/15][i%15].getUserData().equals("b")) {
                    buttonBoard[i / 15][i % 15].fire();
                }
            }
        }

        /*if(!anyoneAccepted) {
            for (int i = 0; i < perceptrons.size(); i++) {
                if (abs(perceptrons.elementAt(i).validateInput(parseInputToDoubleTab())) < min) {
                    maxI = i;
                    min = abs(perceptrons.elementAt(i).validateInput(parseInputToDoubleTab()));
                    System.out.println("Zaden nie akceptuje wartosc blizsza 0 : " + min + " w perceptronie " + (maxI + 1));
                }
            }
        }
        else
        {
            for (int i = 0; i < perceptrons.size(); i++) {
                if (perceptrons.elementAt(i).validateInput(parseInputToDoubleTab()) < min && perceptrons.elementAt(i).validateInput(parseInputToDoubleTab())>=0) {
                    maxI = i;
                    min = perceptrons.elementAt(i).validateInput(parseInputToDoubleTab());
                    System.out.println("Jest akceptujacy wartosc blizsza 0 : " + min + " w perceptronie " + (maxI + 1));
                }
            }
        }
        System.out.println(min + " byl najblizej 0 " + (maxI+1));
        b.setText("Najbardziej poobal sie ten obrazek perceptronowi " + (maxI+1));
        int finalI = maxI;
        b.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                clearButton.fire();
                paintImageOnBoard(perceptrons.elementAt(finalI).getResult());
            }
        });
        rightPanel.getChildren().add(b);*/
    }

    private void paintImageOnBoard(double[] result)
    {
        //printTab(result,225);
        for(int i = 0;i<225;i++)
        {
            if(result[i]==1)
                buttonBoard[i/15][i%15].fire();
        }
    }

    private int abs(int a)
    {
        return a<0?a*(-1):a;
    }

    private double abs(double a)
    {
        return a<0?a*(-1.0):a;
    }

    private void printTab(double[] tab,int size)
    {
        for(int i = 0;i<size;i++)
        {
            System.out.print((int)tab[i] + " ");
        }
        System.out.println();
    }

}
