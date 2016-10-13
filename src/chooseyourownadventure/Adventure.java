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

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.util.Optional;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ButtonBar.ButtonData;


public class Adventure extends Application {

//Globals

    /*    String initialEditview = "<html><head>"
            + "</head><body contenteditable='true'>"
            +"</body></html>";*/

    List<StoryScene> sceneList = new ArrayList<StoryScene>();
    
    public class StoryScene {
        String text;
        String ID;
        Map<String, String> childScenes = new HashMap<String,String>();
//        List<String> childScenes = new ArrayList();
        
        public void addText(String newID, String sceneText) {
            ID = newID;
            text = sceneText;
        }
        
        public void addChild(String action, String ID){
            childScenes.put(action, ID);
        }

    }

    //wrapper so I can modify a local variable within a handle.
    static class IndexWrapper {
       int value;

       IndexWrapper(int value) {
           this.value = value;
       } 
    }
    
    public int findListIndex(String ID) {
        for( int i = 0; i < sceneList.size(); i++ )
        {
            if(sceneList.get(i).ID == ID ){
                return i;
            }
        }
        return -1;
    }



    
    public static void main(String[] args) {
        Application.launch(args);
    }
    
    
    @Override
    public void start(Stage primaryStage) {
        
        StoryScene currentscene = new StoryScene();
        sceneList.add(currentscene);
        int index = 0;
        IndexWrapper currentIndex = new IndexWrapper(index);
        
//        final SceneWrapper savedScene = new SceneWrapper(currentscene);

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
        //must be declared final to get value in the handle method below.
        final TextField field;
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
        final HTMLEditor textEditor = new HTMLEditor();
        textEditor.setPrefHeight(245);

        textEditor.setStyle("-fx-background-color: DARKGRAY;"
                + "-fx-text-fill: BLACK;"
                + "-fx-font-size: 9pt;");
//        + "-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");

        border.setCenter(textEditor);

//        textEditor.setHtmlText(INITIAL_TEXT);

        
        
        //now set up the right panes.
        SplitPane splitPane2 = new SplitPane();
        splitPane2.setOrientation(Orientation.VERTICAL);
        splitPane2.prefWidthProperty().bind(scene.widthProperty());
        splitPane2.prefHeightProperty().bind(scene.heightProperty());

        /*
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
        */

        //Top right
        VBox upperRight = new VBox();
        
        final Text optionsText = TextBuilder.create()
            .text("Scene Options")
//            .x(100)
//            .y(50)
             .fill(Color.BLACK)
            .font(Font.font(null, FontWeight.BOLD, 12))
//            .translateY(50)
            .build();

        upperRight.getChildren().add(optionsText);

        HBox buttonRow = new HBox();

        
        
        //SAVE SCENE
        Button saveBtn = new Button("Save Text");
                
        saveBtn.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                System.out.format("currentText: %s\n", textEditor.getHtmlText() );
                StoryScene newscene = new StoryScene();
                newscene.addText(field.getText(), textEditor.getHtmlText());
                //wrapper so I can modify a local variable within a handle.
                saveScene(currentIndex.value, newscene);
                //needs to be fixed to work with the list.

            }
        });
        //btn.setOnAction(e -> saveScene() );
        buttonRow.getChildren().add(saveBtn);
        
        
        
        
        //NEW SCENE
        Button newBtn = new Button("New Scene");
        
        newBtn.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {

                
                String savedText = new String();
                if( sceneList.get(currentIndex.value).text != null )
                { savedText = sceneList.get(currentIndex.value).text; }
                
                String newText = textEditor.getHtmlText();
                
                System.out.format("SavedText length: %s\n", savedText.length() );
                System.out.format("NewText length: %s\n", newText.length() );
                
                if (newText.length() != 0 && newText.length() != 72) // if the scene is empty, do nothing.
                {
                    if( !(field.getText().isEmpty()) ) //check that there's text in ID field
                    {
                        if( savedText != newText )                   
                        {
                            
                            Alert alert = new Alert(AlertType.CONFIRMATION);
                            alert.setTitle("Alert");
                            alert.setHeaderText(null);
                            alert.setContentText("The current scene text has not been saved. Save scene?");
                            ButtonType buttonYes = new ButtonType("Yes");
                            ButtonType buttonNo = new ButtonType("No");
                            ButtonType buttonCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
                        
                            alert.getButtonTypes().setAll(buttonYes, buttonNo, buttonCancel);
                        
                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.get() == buttonYes){
                                StoryScene newscene = new StoryScene();
                                newscene.addText(field.getText(), textEditor.getHtmlText());
                                saveScene(currentIndex.value, newscene);                                
                            } 
                            if (result.get() == buttonYes || result.get() == buttonNo){
                                StoryScene currentscene = new StoryScene();
                                sceneList.add(currentscene);
                                currentIndex.value = sceneList.size() - 1;
                                textEditor.setHtmlText("");
                                field.setText("");
                            }
                            //if they click Cancel I don't need any more code.
                        } else { 
                            StoryScene newScene = new StoryScene();
                            sceneList.add(newScene);
                            currentIndex.value = sceneList.size() - 1;
                            textEditor.setHtmlText("");
                            field.setText("");
                        }
                    }
                    else
                    {
                        Alert alert = new Alert(AlertType.WARNING);
                        alert.setTitle("Warning");
                        alert.setHeaderText("The ID field is empty!");
                        alert.setContentText("A new scene was not created. "
                                + "You must type a scene ID for the current scene before a new scene can be created. "
                                + "This allows the program to connect this scene with other scenes.");
                        alert.showAndWait();
                    }                    
                }
                System.out.format("Length of scene list: %d\n", sceneList.size() );
            }
        });
        
        buttonRow.getChildren().add(newBtn);

        
        
        upperRight.getChildren().add(buttonRow);
        
        
        
        
        //Lower right
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

//        splitPane2.getItems().add(centerArea);

        splitPane2.getItems().add(upperRight);
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
    
    public void saveScene(int index, StoryScene savedata)
    {
//                System.out.println("You clicked the button");
//                if (field.getText() == null || field.getText().trim().isEmpty()) {
        //DO NOT SAVE if the ID pane is empty.
//        System.out.format("Oldscene ID: %s   newscene ID: %s\n", sceneList.get(index).ID, savedata.ID);
/*
        if (savedata.ID.isEmpty() )
        {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("The ID field is empty!");
            alert.setContentText("The scene was not saved. "
                    + "Please enter an ID in the field \"Scene ID / Short Title\". "
                    + "This allows the program to connect this scene with other scenes.");
            alert.showAndWait();
        }  //The pane is not empty
        else //if (sceneList.get(index).ID == null) 
        {
*/          int newindex = findListIndex(savedata.ID);
            //If the scene is NOT in the scene list...
            if (newindex == -1){
                sceneList.get(index).addText(savedata.ID, savedata.text);
            } //The scene IS in the scene list, and the user is about to overwrite it.
            else {
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Alert");
                alert.setHeaderText(null);
                alert.setContentText("This scene ID already exists in the scene list. Overwrite?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){ 
                    //YES overwrite.
                    sceneList.get(index).addText(savedata.ID, savedata.text);
                    sceneList.remove(newindex);
                }
                // ... if the user chose CANCEL or closed the dialog...do nothing.
            }
//        }
//    System.out.format("Length of scene list: %d\n", sceneList.size() );
    }
        

/*
//Save a scene a second time with the SAME ID.
        else if ( savedata.ID == sceneList.get(index).ID )
        {
            sceneList.get(index).addText(savedata.ID, savedata.text);
        }//if the user CHANGED the scene ID.
        else {
            int oldIndex = findListIndex(sceneList.get(index).ID);
            int newIndex = findListIndex(savedata.ID);
            //If the scene is NOT in the scene list...
            if (newIndex == -1){
                sceneList.set(oldIndex, savedata);
            } 
            else {
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Alert");
                alert.setHeaderText(null);
                alert.setContentText("This scene ID already exists in the scene list. Overwrite?");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.get() == ButtonType.OK){ 
                    //YES overwrite.
                    sceneList.set(oldIndex, savedata);
                    sceneList.remove(newIndex);
                }
            }
        }*/
    
    
    
    
    
}