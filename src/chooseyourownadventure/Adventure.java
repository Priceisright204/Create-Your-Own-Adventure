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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.TextInputDialog;
import javafx.scene.shape.Circle;
import javafx.stage.Popup;


import javafx.stage.Screen; 
import javafx.geometry.Rectangle2D; 
//import javafx.scene.control.ScrollPane;
//import java.util.concurrent.CompletableFuture; 
//import java.nio.file.Path; 
import javafx.scene.layout.StackPane; 
import javafx.scene.web.WebView; 
import javafx.stage.StageStyle;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
//import javafx.scene.layout.Region;
import javafx.scene.layout.Priority;



public class Adventure extends Application {


    
    public class StoryScene {
        String text;
        String ID;
        Map<String, String> childScenes = new HashMap<String,String>();
//        List<String> childScenes = new ArrayList();
        
        StoryScene(){
            this.text = "<html dir=\"ltr\"><head></head><body contenteditable=\"true\"></body></html>";
            this.ID = "";
        }
        
        public void addText(String newID, String sceneText) {
            ID = newID;
            text = sceneText;
        }
        
        public void addChild(String action, String ID){
            childScenes.put(action, ID);
        }

    }

    
    public int findListIndex(String ID) {
        for( int i = 0; i < sceneList.size(); i++ )
        {
            if(sceneList.get(i).ID.equals(ID)  ){
                return i;
            }
        }
        return -1;
    }



    
    public static void main(String[] args) {
        Application.launch(args);
    }
    
    
    //Globals
    List<StoryScene> sceneList = new ArrayList<StoryScene>();
    public TextField IDfield;
    public HTMLEditor textEditor;
    public int currentIndex = 0;
//    Stage primaryStage;
    
    @Override
    public void start(Stage primaryStage) {
        
        StoryScene currentscene = new StoryScene();
        sceneList.add(currentscene);

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

        
        IDfield = new TextField();
        IDfield.setStyle("-fx-background-color: WHEAT;"
                    + "-fx-text-fill: BLACK;"
                    + "-fx-font-size: 11pt;"
        + "-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");
        IDfield.setPromptText("Scene ID / Short Title");
//        IDfield.setAlignment(Pos.CENTER_RIGHT); //Not what I was looking for.

        titleBox.setLeft(upperLeft);
        titleBox.setRight(IDfield);
        
        border.setTop(titleBox);

        //Text Editor
        textEditor = new HTMLEditor();
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
        saveBtn.setOnAction(e -> saveScene() );
        buttonRow.getChildren().add(saveBtn);

        
        //NEW SCENE
        Button newBtn = new Button("New Scene");
        newBtn.setOnAction(e -> newButton() );
        buttonRow.getChildren().add(newBtn);


        //LOAD SCENE
        Button loadBtn = new Button("Load Scene");
        loadBtn.setOnAction(e -> loadDialog(primaryStage) );
        buttonRow.getChildren().add(loadBtn);
        
        
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
    

    
    
    public void loadDialog(Stage primaryStage)
    {
        //Popup
        Stage popupStage = new Stage();
        popupStage.initOwner(primaryStage);
        popupStage.initStyle(StageStyle.UNDECORATED);

        BorderPane border = new BorderPane();

        border.setStyle( "-fx-border-color: black;\n"
                + "-fx-border-width: 1;"
                                        );
        
        final Text headerText = TextBuilder.create()
            .text("Select Scene By ID")
//            .x(100)
//            .y(50)
            .fill(Color.BLUE)
            .font(Font.font(null, FontWeight.NORMAL, 16))
//            .translateY(50)
            .build();
        
        border.setTop(headerText);
        
        
        Button closebutton = new Button("Close");

        closebutton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                popupStage.close();

            }
        });

        HBox hbox1 = new HBox();
        HBox hbox2 = new HBox();
//        Region region = new Region();

        HBox.setHgrow(hbox2, Priority.ALWAYS);
        hbox1.getChildren().add(hbox2);
        hbox1.getChildren().add(closebutton);
        
        border.setBottom(hbox1);

//        sceneChooser.add(closebutton,1,1);

        GridPane sceneChooser = new GridPane();
//        sceneChooser.setPadding(new Insets(5));
        
        sceneChooser.setStyle("    -fx-background-color: WHEAT ;\n"
                                   + "    -fx-padding: 5 ;\n"
            //                       + "-fx-margin: 10;\n"
                + "-fx-border-color: blue;\n"
                + "-fx-border-insets: 5;\n"
                + "-fx-background-insets: 0,1,2;\n"
                + "-fx-border-width: 3;\n"
             //   + "-fx-border-style: dashed;\n" 
                                            );

        
//        valueHolder loadIndex = new valueHolder();
//        loadIndex.value = -1;
        
        for (int index = 0; index < sceneList.size(); index++) {

            Button selection = new Button( sceneList.get(index).ID );

            class valueHolder { int value = -1 ; }
            final valueHolder loopIndex = new valueHolder();
            loopIndex.value = index;
            
            selection.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                popupStage.close();
                loadButton(loopIndex.value);
                }
            });
            
            sceneChooser.add(selection, 2, index);
            
            String strippedText = sceneList.get(index).text.replaceAll("(?s)<[^>]*>(\\s*<[^>]*>)*", " ");

            final Text sceneText;
            
            if(currentIndex == index)
            {
                sceneText = TextBuilder.create()
                    .text(" Current Scene")
//                    .x(100)
//                    .y(50)
                    .fill(Color.RED)
                    .font(Font.font(null, FontWeight.BOLD, 12))
//                    .translateY(50)
                    .build();
            }
            else
            {
                sceneText = TextBuilder.create()
                    .text(strippedText)
//                    .x(100)
//                    .y(50)
                    .fill(Color.BLUE)
                    .font(Font.font(null, FontWeight.THIN, 12))
//                    .translateY(50)
                    .build();    
            }
            
            sceneChooser.add(sceneText, 4, index);
        }

        
        
        border.setCenter(sceneChooser);
        
        Scene sc = new Scene(border, 300, 300);
        
        popupStage.setScene(sc);

/*        popupStage.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
                Boolean newValue) {
                if(!newValue)
                    popupStage.close();

            }
        });
*/        
        popupStage.show();
    }
    
    
    
    public void loadButton(int newIndex)
    {
        
//        if( newIndex != currentIndex && newIndex != -1) //Ensures it's a different scene,
//        {                                               //and that it exists.
            String savedText = sceneList.get(currentIndex).text;                    
            String newText = textEditor.getHtmlText();
            
            System.out.format("SavedText length: %s,  NewText length:%s\n", savedText.length(), newText.length() );
            
            if (newText.length() == 72 && savedText.length() == 72)
            {   // if the scene is empty, discard scene and load.
                sceneList.remove(currentIndex);
                currentIndex = newIndex;
                IDfield.setText(sceneList.get(newIndex).ID );
                textEditor.setHtmlText(sceneList.get(newIndex).text);                        
            }
            else if ( savedText.equals(newText)  )
            {                    
                //just set the current scene to the loaded scene.
                currentIndex = newIndex;
                IDfield.setText(sceneList.get(newIndex).ID );
                textEditor.setHtmlText(sceneList.get(newIndex).text);                        
            }
            else if ( !(savedText.equals(newText))  ) 
            {
                if( IDfield.getText().isEmpty() ) //check that there's text in ID field
                {
                    Alert alert = new Alert(AlertType.CONFIRMATION);
                    alert.setTitle("Alert");
                    alert.setHeaderText(null);
                    alert.setContentText("The current scene text has not been saved, and the ID field is empty."
                            + "The current scene will be discarded. Continue?");
                    ButtonType buttonYes = new ButtonType("Yes, Discard");
                    ButtonType buttonNo = new ButtonType("No, Cancel");
    //                ButtonType buttonCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
            
                    alert.getButtonTypes().setAll(buttonYes, buttonNo);

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == buttonYes){
                        //Discard scene and load new
                        sceneList.remove(currentIndex);
                        currentIndex = newIndex;
                        IDfield.setText(sceneList.get(newIndex).ID );
                        textEditor.setHtmlText(sceneList.get(newIndex).text);                        
                    }
                    //no code needed to cancel.
                }
                else  //if there IS text in the ID field...
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
                        saveScene();                                

                        //and load the new scene...
                        currentIndex = newIndex;
                        IDfield.setText(sceneList.get(newIndex).ID );
                        textEditor.setHtmlText(sceneList.get(newIndex).text);                        
                    } 
                    if (result.get() == buttonNo){

                        // discard the current scene and load the new scene...
                        sceneList.remove(currentIndex);
                        currentIndex = newIndex;
                        IDfield.setText(sceneList.get(newIndex).ID );
                        textEditor.setHtmlText(sceneList.get(newIndex).text);                        
                    }
                    //if they click Cancel I don't need any more code.
                }
            }
//        }        
        System.out.format("Length of scene list: %d\n", sceneList.size() );
    }
    
    
    public void newButton()
    {
                String savedText = sceneList.get(currentIndex).text;
                String newText = textEditor.getHtmlText();
                
                System.out.format("SavedText length: %s,  NewText length:%s\n", savedText.length(), newText.length() );
                if (savedText.equals(newText) ) {
                    System.out.print("NewText = SavedText");   }
                
                if (newText.length() != 0 && newText.length() != 72) // if the scene is empty, do nothing.
                {
                    if( !(IDfield.getText().isEmpty()) ) //check that there's text in ID field
                    {
                        if( !(savedText.equals(newText))  )   //scene is not saved
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
                                saveScene();                                
                            } 
                            if (result.get() == buttonYes || result.get() == buttonNo){
                                createScene();
                            }
                            //if they click Cancel I don't need any more code.
                        } else {   //scene is saved. Proceed.
                            createScene();
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
    
    
    public void createScene()
    {
        StoryScene currentscene = new StoryScene();  //create a new scene
        sceneList.add(currentscene);
        currentIndex = sceneList.size() - 1;
        textEditor.setHtmlText("");
        IDfield.setText("");
    }
    
    
    public void saveScene()
    {
        System.out.format("currentText: %s\n", textEditor.getHtmlText() );

        int newindex = findListIndex(IDfield.getText());
            //If the scene is NOT in the scene list...
            if (newindex == -1 || newindex == currentIndex){
                sceneList.get(currentIndex).addText(IDfield.getText(), textEditor.getHtmlText());
            } //The scene IS in the scene list, and the user is about to overwrite it.
            else {
                String replaceID = IDfield.getText();

                while( findListIndex(replaceID) != -1 )
                {
                    TextInputDialog dialog = new TextInputDialog();
                    dialog.setTitle("Scene ID");
                    dialog.setHeaderText("The current scene ID already exists in the scene list. Please enter a new one.");
                    dialog.setContentText("Scene ID:");
                    Optional<String> result = dialog.showAndWait();
                    if (result.isPresent()){
                        replaceID = result.get();
                    }
                }
                IDfield.setText(replaceID);
            }
    }







}