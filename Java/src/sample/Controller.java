package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {
    public Button exerciseOne;
    public Button exerciseTwo;
    public Button exerciseThree;


    @FXML
    public void initialize() {
        System.out.println("second");
        exerciseOne.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                try {
                    Stage exerciseOneStage = new Stage();
                    Parent exerciseOneRoot = FXMLLoader.load(getClass().getResource("../exercise_1/Exercise_1.fxml"));
                    exerciseOneStage.setTitle("Rozpoznawanie Liczb");
                    exerciseOneStage.setScene(new Scene(exerciseOneRoot, 600, 220));
                    exerciseOneStage.show();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }
        });
        exerciseTwo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    Stage exerciseOneStage = new Stage();
                    Parent exerciseOneRoot = FXMLLoader.load(getClass().getResource("../exercise_2/Exercise_2.fxml"));
                    exerciseOneStage.setTitle("Usuwanie zaszumień z obrazka");
                    exerciseOneStage.setScene(new Scene(exerciseOneRoot, 900, 600));
                    exerciseOneStage.show();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }
        });

        exerciseThree.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    Stage exerciseOneStage = new Stage();
                    Parent exerciseOneRoot = FXMLLoader.load(getClass().getResource("../exercise_3/Exercise_3.fxml"));
                    exerciseOneStage.setTitle("Adaline, czyli znów cyfry, ale z przesunieciem");
                    exerciseOneStage.setScene(new Scene(exerciseOneRoot, 600, 400));
                    exerciseOneStage.show();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }
        });


    }
}
