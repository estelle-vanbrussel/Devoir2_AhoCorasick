/**
 * Created by Vincent on 13/10/2018.
 */
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Interface extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        GridPane root = new GridPane();
        root.setPadding(new Insets(10,10,10,10));
        root.setHgap(10);
        root.setVgap(10);
        Scene scene = new Scene(root, 800, 800);
        primaryStage.setScene(scene);

        //String[] keywords = {"ART","Carte","CLE","C'eSt","TABLE!"};
        String[] keywords = {"he", "she", "his", "hers", "ers", "rs"};
        //String[] keywords = {"ART","CARTE","CLE","COUP","TABLE"};
        PatternMatchingMachine pmm = new PatternMatchingMachine(keywords);

        HBox canvas = new HBox();
        canvas.setPrefSize(scene.getWidth() / pmm.globalTable[0].length, scene.getHeight() / pmm.globalTable.length);
        Label label = new Label("");
        canvas.getChildren().add(label);

        root.add(canvas, 0,0);

        //première ligne -> numéro des états
        for (int i = 0 ; i < pmm.globalTable[0].length ; ++i) {
            HBox hbox = new HBox();
            hbox.setPrefSize(scene.getWidth() / pmm.globalTable[0].length, scene.getHeight() / pmm.globalTable.length);
            Label stateNumber = new Label(String.valueOf(i));
            hbox.getChildren().add(stateNumber);
            root.add(hbox,i+1,0);
        }

        //toutes les lignes : colonne 1 = caractère, autres colonnes = états correspondants
        int indexHboxLigne = 1;
        for (int i = 0; i < pmm.globalTable.length ; ++i) {
            HBox[] hBoxesStates = new HBox[pmm.globalTable[0].length];
            boolean empty = true;
            for (int j = 0; j < pmm.globalTable[0].length; ++j) {
                if (pmm.globalTable[i][j] != 0) {
                    empty = false;
                }
                //renseignement des états pour chaque ligne
                HBox hboxState = new HBox();
                hboxState.setPrefSize(scene.getWidth() / pmm.globalTable[0].length, scene.getHeight() / pmm.globalTable.length);
                Label stateNumber = new Label(String.valueOf(pmm.globalTable[i][j]));
                hboxState.getChildren().add(stateNumber);
                hBoxesStates[j] = hboxState;
            }
            if (!empty) {
                //on n'affiche la ligne que si le caractère correspondant est utilisé
                HBox hBoxLettre = new HBox();
                hBoxLettre.setPrefSize(scene.getWidth() / pmm.globalTable[0].length, scene.getHeight() / pmm.globalTable.length);
                Label letter = new Label(Character.toString((char)i));
                hBoxLettre.getChildren().add(letter);
                root.add(hBoxLettre, 0, indexHboxLigne);

                int indexHboxColonne = 1;
                for (HBox hboxState : hBoxesStates) {
                    root.add(hboxState, indexHboxColonne, indexHboxLigne);
                    ++indexHboxColonne;
                }
                ++indexHboxLigne;
            }
            //System.out.println((char)i + " -> " + ligne);
        }

        indexHboxLigne += 3;

        HBox hboxStateLabel = new HBox();
        hboxStateLabel.setPrefSize(scene.getWidth() / pmm.globalTable[0].length, scene.getHeight() / pmm.globalTable.length);
        Label labelState = new Label("state");
        hboxStateLabel.getChildren().add(labelState);
        root.add(hboxStateLabel, 0, indexHboxLigne);

        HBox hboxFailStateLabel = new HBox();
        hboxFailStateLabel.setPrefSize(scene.getWidth() / pmm.globalTable[0].length, scene.getHeight() / pmm.globalTable.length);
        Label labelFailState = new Label("fail(state)");
        hboxFailStateLabel.getChildren().add(labelFailState);
        root.add(hboxFailStateLabel, 0, indexHboxLigne+1);

        for (int i = 1; i < pmm.failTable.length ; ++i) {
            HBox hBoxStateNumber = new HBox(new Label(String.valueOf(i)));
            hBoxStateNumber.setPrefSize(scene.getWidth() / pmm.globalTable[0].length, scene.getHeight() / pmm.globalTable.length);
            root.add(hBoxStateNumber, i, indexHboxLigne);

            HBox hboxFailStateNumber = new HBox(new Label(String.valueOf(pmm.failTable[i])));
            hboxFailStateNumber.setPrefSize(scene.getWidth() / pmm.globalTable[0].length, scene.getHeight() / pmm.globalTable.length);
            root.add(hboxFailStateNumber, i, indexHboxLigne+1);
        }


        indexHboxLigne += 6;

        HBox hboxStateLabelBis = new HBox();
        hboxStateLabelBis.setPrefSize(scene.getWidth()*3 / (pmm.globalTable[0].length + 10), scene.getHeight() / (pmm.globalTable.length + 10));
        Label labelStateBis = new Label("state");
        hboxStateLabelBis.getChildren().add(labelStateBis);
        root.add(hboxStateLabelBis, 0, indexHboxLigne);

        HBox hboxOutputStateLabel = new HBox();
        hboxOutputStateLabel.setPrefSize(scene.getWidth()*3 / (pmm.globalTable[0].length + 10), scene.getHeight() / (pmm.globalTable.length + 10));
        Label labelOutputState = new Label("output(state)");
        hboxOutputStateLabel.getChildren().add(labelOutputState);
        root.add(hboxOutputStateLabel, 0, indexHboxLigne+1);

        for (int i = 0; i < pmm.outputTable.size() ; ++i) {
            if (pmm.outputTable.get(i).isEmpty()) {
                continue;
            }

            HBox hBoxStateNumber = new HBox(new Label(String.valueOf(i)));
            hBoxStateNumber.setPrefSize(scene.getWidth() / (pmm.globalTable[0].length + 10), scene.getHeight() / (pmm.globalTable.length + 10));
            root.add(hBoxStateNumber, i, indexHboxLigne);

            int indexStringLigne = indexHboxLigne + 1;
            for (String outputString : pmm.outputTable.get(i)) {
                HBox hboxOutputStrings = new HBox();
                hboxOutputStrings.setPrefHeight((scene.getHeight() / pmm.globalTable.length)*3);
                Label labelOutputStrings = new Label(outputString);
                hboxOutputStrings.getChildren().add(labelOutputStrings);
                root.add(hboxOutputStrings, i, indexStringLigne++);
            }
        }
        primaryStage.show();
    }
}
