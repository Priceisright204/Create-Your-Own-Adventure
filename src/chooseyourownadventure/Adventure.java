package chooseyourownadventure;

import javafx.application.Application;
import javafx.scene.text.TextBuilder;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

//import javafx.scene.text.TextFlow;
import javafx.scene.web.HTMLEditor;
//import javafx.beans.binding.Bindings;
//import javafx.scene.web.WebView;



public class Adventure extends Application {

    String initialEditview = "<html><head>"
            + "</head><body contenteditable='true'>"
            +"</body></html>";

    public static void main(String[] args) {
        Application.launch(args);
    }
    @Override
    public void start(Stage primaryStage) {
        //Initial setup
        primaryStage.setTitle("Choose Your Own Adventure Story Creator");
        Group root = new Group();
        Scene scene = new Scene(root, 800, 400, Color.WHITE);
        
        SplitPane splitPane = new SplitPane();
        splitPane.prefWidthProperty().bind(scene.widthProperty());
        splitPane.prefHeightProperty().bind(scene.heightProperty());

        //Set up the left area of the splitpane.
        final BorderPane border = new BorderPane();

        //graveyard of failed ideas
//        final WebView editor = new WebView();
//        editor.getEngine().loadContent(initialEditview);
//        TextArea textArea = new TextArea();
//        textArea.setPromptText("Scene Text");
//        textArea.setWrapText(true);

        //start from the top and work down.

        //Title
        final BorderPane titleBox = new BorderPane();

        final Text upperLeft = TextBuilder.create()
            .text("Scene Editor\t\t\t\t\t\t")
//            .x(100)
//            .y(50)
             .fill(Color.BLUE)
            .font(Font.font(null, FontWeight.BOLD, 20))
//            .translateY(50)
            .build();

        //Scene ID
        TextField field;
        field = new TextField();
        field.setStyle("-fx-background-color: WHEAT;"
                    + "-fx-text-fill: BLACK;"
                    + "-fx-font-size: 11pt;"
        + "-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");
        field.setPromptText("Scene ID / Short Title");
//        field.setAlignment(Pos.CENTER_RIGHT); //Not what I was looking for.

        titleBox.setLeft(upperLeft);
        titleBox.setRight(field);
        
        border.setTop(titleBox);

        //Text Editor
        HTMLEditor htmlEditor = new HTMLEditor();
        htmlEditor.setPrefHeight(245);


        htmlEditor.setStyle("-fx-background-color: DARKGRAY;"
                + "-fx-text-fill: BLACK;"
                + "-fx-font-size: 9pt;"
        + "-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");

        border.setCenter(htmlEditor);

        
        
        
        //now set up the right panes.
        SplitPane splitPane2 = new SplitPane();
        splitPane2.setOrientation(Orientation.VERTICAL);
        splitPane2.prefWidthProperty().bind(scene.widthProperty());
        splitPane2.prefHeightProperty().bind(scene.heightProperty());

        HBox centerArea = new HBox();
 
        final Text upperRight = TextBuilder.create()
            .text("Text")
            .x(100)
            .y(50)
             .fill(Color.RED)
            .font(Font.font(null, FontWeight.BOLD, 35))
            .translateY(50)
            .build();
        centerArea.getChildren().add(upperRight);

        HBox rightArea = new HBox();
        
        final Text lowerRight = TextBuilder.create()
            .text("Lower Right")
            .x(100)
            .y(50)
             .fill(Color.RED)
            .font(Font.font(null, FontWeight.BOLD, 35))
            .translateY(50)
            .build();
        rightArea.getChildren().add(lowerRight);

        splitPane2.getItems().add(centerArea);
        splitPane2.getItems().add(rightArea);

        splitPane.getItems().add(border);

        splitPane.getItems().add(splitPane2);

        ObservableList<SplitPane.Divider> dividers = splitPane.getDividers();
        for (int i = 0; i < dividers.size(); i++) {
            dividers.get(i).setPosition((i + 1.0)* 2 / 3);
        }
        HBox hbox = new HBox();
        hbox.getChildren().add(splitPane);
        root.getChildren().add(hbox);
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }  
}