import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class InterfaceUtilisateur extends Application
{
    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane root = new GridPane();
        root.setPadding(new Insets(10,10,10,10));
        root.setHgap(10);
        root.setVgap(10);
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        GridPane canvas = new GridPane();
        HBox hbox1 =new HBox();
        HBox hbox2 =new HBox();
        String[] keywordsPmm;
        ArrayList<String> keywords= new ArrayList<>();
        Label keywordsLabel = new Label("");
        Label keywordLabel = new Label("Mot-clé : ");
        root.add(keywordsLabel,0,0);
        canvas.add(keywordLabel,0,2);
        TextField keywordValue = new TextField();
        canvas.add(keywordValue,1,2);
        Button newKeyword = new Button("Ajouter");
        newKeyword.setOnAction((EventHandler<ActionEvent>) e -> {
            if(!keywordValue.getText().equals("")) {
                keywordsLabel.setText(keywordsLabel.getText() + keywordValue.getText() + ", ");
                keywords.add(keywordValue.getText());
            }
        });
        canvas.add(newKeyword,2,2);
        Label fileLabel = new Label("Fichier : ");
        canvas.add(fileLabel,0,3);
        Label errorLabel = new Label("");
        canvas.add(errorLabel,4,3);
        TextField fileValue = new TextField();
        canvas.add(fileValue,1,3);
        Button search = new Button("Chercher");
        Label nbResults = new Label();
        root.add(nbResults,1,0);
        search.setOnAction((EventHandler<ActionEvent>) e -> {
            nbResults.setText("");
            errorLabel.setText("");
            if(!hbox2.getChildren().isEmpty())
                hbox2.getChildren().remove(0);
            PatternMatchingMachine pmm = new PatternMatchingMachine(keywords);
            try {
                FileReader fileReader = new FileReader(fileValue.getText());
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                StringBuilder fileString =new StringBuilder();
                String line = null;
                try {
                    line = bufferedReader.readLine();
                    while (line != null)
                    {
                        fileString.append(line);
                        fileString.append("<br>");
                        line = bufferedReader.readLine();
                    }

                    bufferedReader.close();
                    fileReader.close();
                } catch (IOException exeption) {
                    exeption.printStackTrace();
                }
                Map<Integer,Integer> result = pmm.findPatterns(fileString.toString());

                Set<Integer> tableResultKeys = result.keySet();
                nbResults.setText(tableResultKeys.size()+"");

                StringBuilder sb = new StringBuilder("");
                for (int i = 0; i < fileString.length(); i++) {
                    if (tableResultKeys.contains(i)) {
                        sb.append("<strong>");
                    }
                    sb.append(fileString.charAt(i));
                    if (result.containsValue(i)) {
                        sb.append("</strong>");
                    }
                }
                String fontText = sb.toString();
                WebView fontWebView = new WebView();
                fontWebView.getEngine().loadContent(fontText);
                hbox2.getChildren().add(fontWebView);
            } catch (FileNotFoundException e1) {
                errorLabel.setText("Ce fichier est introuvable");
            }

        });
        canvas.add(search,2,3);
        hbox1.getChildren().add(canvas);
        root.add(hbox1,0,1);
        root.add(hbox2,0,2);

        primaryStage.show();
    }
}
