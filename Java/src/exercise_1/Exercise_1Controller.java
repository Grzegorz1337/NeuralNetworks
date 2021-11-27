package exercise_1;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

public class Exercise_1Controller {


    private ProbeDate samplesSet;
    private DigitPerceptrons[] aiPartOfThatExercise;

    public GridPane buttonsPane;
    public Button inputBoard[][];
    public Label info;
    public TextArea programOutput;
    public ChoiceBox digitChoiceBox;
    public ChoiceBox sampleResultChoiceBox;


    private void initButtonsPane(){
        buttonsPane.setStyle("-fx-padding: 0; " +
                "-fx-start-margin: 0; " +
                "-fx-end-margin: 0");
        inputBoard = new Button[6][5];
        for (int i = 0;i<6;i++){
            for (int j = 0;j<5;j++)
            {
                inputBoard[i][j] = new Button("");
                inputBoard[i][j].setUserData("white");
                inputBoard[i][j].setStyle("" +
                        "-fx-background-color: white; ");
                inputBoard[i][j].setPrefSize(39,31);
                int finalI = i;
                int finalJ = j;
                inputBoard[i][j].setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        if ("white".equals(inputBoard[finalI][finalJ].getUserData())) {
                            inputBoard[finalI][finalJ].setStyle("-fx-background-color: black");
                            inputBoard[finalI][finalJ].setUserData("black");
                        }
                        else{
                            inputBoard[finalI][finalJ].setStyle("-fx-background-color: white");
                            inputBoard[finalI][finalJ].setUserData("white");
                        }
                    }
                });
                buttonsPane.add(inputBoard[i][j],j,i);
            }
        }
    }
    @FXML
    public void initialize() {
        info.setText("Narysuj naplanszy po lewej\n cyfrę, którą ma rozpoznać \nprogram i naciśnij ROZPOZNAJ\n aby usunąć liczbę naciśnij CZYSC");
        buttonsPane.setVisible(false);
        initButtonsPane();
        buttonsPane.setVisible(true);
        samplesSet = new ProbeDate();
        samplesSet.loadInputFromFile();
        aiPartOfThatExercise = new DigitPerceptrons[10];
        for (int i = 0;i<10;i++)
        {
            aiPartOfThatExercise[i] = new DigitPerceptrons(samplesSet,programOutput,i);
        }
        sampleResultChoiceBox.setValue("jest");
        sampleResultChoiceBox.setDisable(true);
        for (int i = 0;i<10;i++)
        {
            //aiPartOfThatExercise[i].setProbe(samplesSet);
        }
    }

    public void clearBoard(ActionEvent actionEvent) {
        for (int i = 0;i<6;i++){
            for (int j = 0;j<5;j++) {
                inputBoard[i][j].setUserData("white");
                inputBoard[i][j].setStyle("-fx-background-color: white");
            }
        }
    }

    public void calculateDigit(ActionEvent actionEvent) {
        programOutput.setText("");
        for (int i = 0;i<10;i++)
        {
            aiPartOfThatExercise[i].getRecognisedDigit(new InputSet(getConvertedImgToInt()));
        }
    }

    public void addInputSet(ActionEvent actionEvent) {
        for(int i = 0;i<9;i++) {
            InputSet readed  = new InputSet(getConvertedImgToInt(),i==Integer.parseInt(digitChoiceBox.getValue().toString())?1:-1, i, false);
            samplesSet.addInput(readed);
            aiPartOfThatExercise[i].setProbe(samplesSet);
        }
        clearBoard(null);
    }

    private int[] getConvertedImgToInt()
    {
        int k = 0;
        int tmpBoard[] = new int[30];
        for (int i = 0;i<6;i++) {
            for (int j = 0; j < 5; j++) {
                tmpBoard[k] = inputBoard[i][j].getUserData()=="white"?0:1;
                k++;
            }
        }
        return tmpBoard;
    }


}
