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

import java.io.FileNotFoundException;
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

        String[] keywordsPmm;
        ArrayList<String> keywords= new ArrayList<>();
        Label keywordsLabel = new Label("");
        Label keywordLabel = new Label("Mot-clé : ");
        root.add(keywordsLabel,0,1);
        root.add(keywordLabel,0,2);
        TextField keywordValue = new TextField();
        root.add(keywordValue,1,2);
        Button newKeyword = new Button("Ajouter");
        newKeyword.setOnAction((EventHandler<ActionEvent>) e -> {
            if(!keywordValue.getText().equals("")) {
                keywordsLabel.setText(keywordsLabel.getText() + keywordValue.getText() + ", ");
                keywords.add(keywordValue.getText());
            }
        });
        root.add(newKeyword,2,2);
        Label fileLabel = new Label("Fichier : ");
        root.add(fileLabel,0,3);
        TextField fileValue = new TextField();
        root.add(fileValue,1,3);
        Button search = new Button("Chercher");
        search.setOnAction((EventHandler<ActionEvent>) e -> {
            PatternMatchingMachine pmm = new PatternMatchingMachine(keywords);
            try {
                Map<Integer,Integer> result = pmm.findPatterns(fileValue.getText());
                TextArea areaString = new TextArea();

                Set<Integer> tableResultKeys = result.keySet();

                StringBuilder sb = new StringBuilder("");
                for (int i = 0; i < fileValue.getText().length(); i++) {
                    if (tableResultKeys.contains(i)) {
                        sb.append("<strong>");
                        sb.append(fileValue.getText().charAt(i));
                    }
                    else if (result.containsValue(i)) {
                        sb.append(fileValue.getText().charAt(i));
                        sb.append("</strong>");
                    }
                    else {
                        sb.append(fileValue.getText().charAt(i));
                    }
                }
                String fontText = sb.toString();
                WebView fontWebView = new WebView();
                fontWebView.getEngine().loadContent(fontText);
                root.add(fontWebView,3,4);
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }

        });
        root.add(search,2,3);


        primaryStage.show();
    }
}
