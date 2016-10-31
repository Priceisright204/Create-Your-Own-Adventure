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
//import java.util.HashMap;
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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
//import java.nio.file.Path; 
import javafx.scene.layout.StackPane; 
import javafx.scene.web.WebView; 
import javafx.stage.StageStyle;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
//import javafx.scene.layout.Region;
import javafx.scene.layout.Priority;
import java.util.LinkedHashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import javafx.collections.FXCollections;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.AnchorPane;
import javafx.stage.WindowEvent;

import javafx.scene.control.Dialog;
import java.util.Arrays;
import javafx.util.Pair;
import javafx.scene.Node;
import javafx.application.Platform;
import javafx.scene.control.TextFormatter;
import javafx.util.converter.IntegerStringConverter;
import javafx.beans.binding.Bindings;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import com.sun.javafx.scene.web.skin.HTMLEditorSkin;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.Toggle;
//import javafx.util.StringConverter;
//import javafx.util.converter.ShortStringConverter;
 
public class Adventure extends Application {


    
    public class StoryScene {
        String text;
        String ID;
//        List<String> childScenes = new ArrayList();
//        List< Map<String, Integer> > childScenes = new ArrayList< Map<String, Integer> >();
        Map<String, Integer> childScenes = new LinkedHashMap();
        
        
        StoryScene(){
            this.text = "<html dir=\"ltr\"><head></head><body contenteditable=\"true\"></body></html>";
            this.ID = "";
        }
        
        public void addText(String newID, String sceneText) {
            ID = newID;
            text = sceneText;
        }
        
        public void addChild(String action, int index){
            //Map<String, Integer> SceneLink = new HashMap<String,Integer>();
            //SceneLink.put(action, index);
            childScenes.put(action, index);
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
//    GridPane childGrid = new GridPane();
    ObservableList<String> childList = FXCollections.<String>observableArrayList();
    public String childSelection;
    public String toggleValue = "#";
    
    
    @Override
    public void start(Stage primaryStage) {
        
        StoryScene currentscene = new StoryScene();
        sceneList.add(currentscene);
        
        //Initial setup
        primaryStage.setTitle("Choose Your Own Adventure Story Creator");
        Group root = new Group();
        Scene scene = new Scene(root, 800, 400, Color.WHITE);
 
        String css = Adventure.class.getResource("primary.css").toExternalForm();
        scene.getStylesheets().add(css);

        
        
        
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
        
        /*  //prevent users from entering duplicate Scene ID's.
        //this one listens for every character you type. It's a bit annoying. If duplicate, erase the last character you typed.
        IDfield.textProperty().addListener((observable, oldValue, newValue) -> {
            if(findListIndex(newValue)!=-1 && findListIndex(newValue)!=currentIndex)
            {
                IDfield.setText(oldValue);
            }
        });*/

        //Prevent users from entering duplicate Scene ID's.
        //Runs when the focus changes. If it's a duplicate, set the ID to the scene #.
        IDfield.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            String newString = IDfield.getText();
            if(findListIndex(newString)!=-1 && findListIndex(newString)!=currentIndex)
            {
                IDfield.setText(Integer.toString(currentIndex + 1));
            }
        });

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
            .font(Font.font(null, FontWeight.BOLD, 14))
//            .translateY(50)
            .build();

        upperRight.getChildren().add(optionsText);

        HBox buttonRow1 = new HBox();
        
        /*
        //SAVE SCENE
        Button saveBtn = new Button("Save Text");
        saveBtn.setOnAction(e -> saveScene() );
        buttonRow1.getChildren().add(saveBtn);
        */
        
        //NEW SCENE
        Button newBtn = new Button("New Scene");
        newBtn.setOnAction(e -> newButton(primaryStage) );
        buttonRow1.getChildren().add(newBtn);


        //LOAD SCENE
        Button loadBtn = new Button("Load Scene");
        loadBtn.setOnAction(e -> selectScene(primaryStage, "load") );
        buttonRow1.getChildren().add(loadBtn);
        
        //CONNECT CHILD
        Button addChild = new Button("Connect Child");
        //Makes sure the scene isn't empty before connecting anything.
        addChild.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                String sceneText = textEditor.getHtmlText();
                if( !sceneText.isEmpty() && sceneText.length()!=72 )
                {
                    selectScene(primaryStage, "child");
                }
                else
                {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Empty Scene");
                    alert.setHeaderText(null);
                    alert.setContentText("Put some text into the scene before adding child scenes.");

                    alert.showAndWait();
                }
            }
        });
        buttonRow1.getChildren().add(addChild);
        
        upperRight.getChildren().add(buttonRow1);


        //Empty Button Row
        HBox buttonRow2 = new HBox();
        upperRight.getChildren().add(buttonRow2);
        
        //Radio Buttons
        final ToggleGroup group = new ToggleGroup();

        RadioButton rb1 = new RadioButton("Connect scenes by scene number");
        rb1.setToggleGroup(group);
        rb1.setSelected(true);
        rb1.setUserData("#");

        RadioButton rb2 = new RadioButton("Connect scenes by ID");
        rb2.setToggleGroup(group);
        rb2.setUserData("ID");
        upperRight.getChildren().add(rb1);
        upperRight.getChildren().add(rb2);

        //give the radio buttons some space.
        rb1.setStyle( "-fx-padding: 0 0 0 20;");
        rb2.setStyle( "-fx-padding: 0 0 0 20;");

        //update the global setting variable when the toggle changes.
        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
          public void changed(ObservableValue<? extends Toggle> ov,
                Toggle old_toggle, Toggle new_toggle) {
            if (group.getSelectedToggle() != null) {
                System.out.println(group.getSelectedToggle().getUserData().toString());
                toggleValue = group.getSelectedToggle().getUserData().toString();

                if(toggleValue == "#")
                {  IDfield.setEditable(false);  }
                if(toggleValue == "ID")
                {  IDfield.setEditable(true);  }
            }
      }
    });
    
        
        
        
        //Empty Space
        VBox Vgrowbox = new VBox();
        VBox.setVgrow(Vgrowbox, Priority.ALWAYS);
        upperRight.getChildren().add(Vgrowbox);
        Text emptySpace = TextBuilder.create().text("   ").build();
        Vgrowbox.getChildren().add(emptySpace);
        
        
        
        //Row containing label and "Edit Link" button
        HBox labelBox = new HBox();
        
        //Label for Child Scenes Area        
        Text childText = TextBuilder.create()
            .text("Child Scenes")
            .fill(Color.BLACK)
            .font(Font.font(null, FontWeight.BOLD, 14))
            .build();

        labelBox.getChildren().add(childText);
        
        HBox Hgrowbox = new HBox();
        HBox.setHgrow(Hgrowbox, Priority.ALWAYS);
        labelBox.getChildren().add(Hgrowbox);

        //EDIT LINK
        Button editLink = new Button("Edit Link");
        editLink.setOnAction(e -> editChild(primaryStage) );
        labelBox.getChildren().add(editLink);



//        labelBox.getChildren().add(addChild);

        upperRight.getChildren().add(labelBox);
        
        setChildArea(primaryStage);

        ListView<String> childArea = new ListView<>(childList);
        upperRight.getChildren().add(childArea);
        
        // Update the variables when the selected scene changes
	childArea.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>()
	{
	    public void changed(ObservableValue<? extends String> ov,
	            final String oldvalue, final String newvalue) 
	    {

                //List<String> myList = new ArrayList<String>(Arrays.asList(newvalue.split(" ")));
                //int sceneNumber = Integer.parseInt(myList.get(0)) - 1;
                childSelection = newvalue.substring( newvalue.indexOf(":") + 2);
          }});
        
//        upperRight.getChildren().add(childGrid);

        
        
        
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
        
        //set focus on HTMLEditor
        final WebView view = (WebView) ((GridPane)((HTMLEditorSkin)textEditor.getSkin()).getChildren().get(0)).getChildren().get(2);

            Platform.runLater(() -> {
            view.fireEvent(new MouseEvent(MouseEvent.MOUSE_PRESSED, 100, 100, 200, 200, MouseButton.PRIMARY, 1, false, false, false, false, false, false, false, false, false, false, null));
            textEditor.requestFocus();
            view.fireEvent(new MouseEvent(MouseEvent.MOUSE_RELEASED, 100, 100, 200, 200, MouseButton.PRIMARY, 1, false, false, false, false, false, false, false, false, false, false, null));
        });

            
        IDfield.setEditable(false);

        sceneList.get(currentIndex).ID = Integer.toString(currentIndex + 1);
        
    }
    

    public void setChildArea(Stage primaryStage)
    {
        childList.clear();
        childSelection = null;
        
        Iterator< Entry<String,Integer> > itr = sceneList.get(currentIndex).childScenes.entrySet().iterator();

        for (int index = 0; index < sceneList.get(currentIndex).childScenes.size(); index++)
//          while( itr.hasNext() )
        {
//            index += 1;
            Entry<String,Integer> entry = itr.next();
            String key = entry.getKey();
            int childIndex = entry.getValue();
            
//            childList.add( Integer.toString(childIndex) + " " + sceneList.get(index).text.replaceAll("(?s)<[^>]*>(\\s*<[^>]*>)*", " ") );
            childList.add( Integer.toString(childIndex + 1) + " : " + key );

            /*
            Text childID = new Text();
            childID = TextBuilder.create()
                    .text( "  " + sceneList.get( childIndex ).ID )
                    .fill(Color.BLACK)
                    .font(Font.font(null, FontWeight.NORMAL, 12))
                    .build();
            */
        }
    }
    
    public void editChild(Stage primaryStage)
    {
      if(childSelection != null)
      { 
          
        // Create the custom dialog.
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Edit Child Link");
        dialog.setHeaderText("Edit Child Link");

        // Set the button types.
        ButtonType submitButton = new ButtonType("Change Link / OK", ButtonData.OK_DONE);
        ButtonType delete = new ButtonType("Delete Link");
        dialog.getDialogPane().getButtonTypes().addAll(submitButton, delete, ButtonType.CANCEL);

        // Create the labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        
        TextField action = new TextField();
        action.setPromptText("Action Text");
        TextField sceneNumber = new TextField();
        sceneNumber.setPromptText("Scene Number");
        
        grid.add(new Label("Action Text:"), 0, 0);
        grid.add(action, 1, 0);
        grid.add(new Label("Scene Number:"), 0, 1);
        grid.add(sceneNumber, 1, 1);

        action.setText(childSelection);
        
        final TextFormatter<Integer> formatter = new TextFormatter<>(new IntegerStringConverter());
        sceneNumber.setTextFormatter(formatter);        
        formatter.setValue( sceneList.get(currentIndex).childScenes.get(childSelection) + 1 );
        
        
        // Enable/Disable login button depending on whether a username was entered.
        Node loginButton = dialog.getDialogPane().lookupButton(submitButton);
        loginButton.setDisable(true);

        // Do some validation (using the Java 8 lambda syntax).
        action.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());  });
        sceneNumber.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty() || Integer.parseInt(newValue) > sceneList.size() || Integer.parseInt(newValue) <= 0); });

        
        dialog.getDialogPane().setContent(grid);

        // Request focus on the username field by default.
        Platform.runLater(() -> action.requestFocus());
        
        // Convert the result to a key-value-pair when the OK button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == submitButton) {
                return new Pair<>(action.getText(), sceneNumber.getText() );
            }
            if (dialogButton == delete)
            {
                sceneList.get(currentIndex).childScenes.remove(childSelection);
                return null;
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent(keyvalue -> {
            sceneList.get(currentIndex).childScenes.remove(childSelection);
            sceneList.get(currentIndex).addChild(keyvalue.getKey(), Integer.parseInt(keyvalue.getValue())-1 );
            System.out.println("Action Text=" + keyvalue.getKey() + ", Scene Number=" + keyvalue.getValue());
        });

        
        
        setChildArea(primaryStage);
      }
    }
    
    
    public String getActionString()
    {
        String actionString = new String();

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Scene ID");
        dialog.setHeaderText("Enter an action string to connect the child scene.");
        //dialog.setContentText("");

        dialog.getDialogPane().lookupButton(ButtonType.CANCEL).setDisable(true);
            
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            actionString = result.get();
        }
        return actionString;
    }
    
    
    public void selectScene(Stage primaryStage, String mode)
    {
      if(sceneList.size() != 1)
      {
        saveScene();
        int startingScene = currentIndex;

        class valueHolder { int value = -1 ; }
        final valueHolder selection = new valueHolder();
        
        
        //Popup
        Stage popupStage = new Stage();
        popupStage.initOwner(primaryStage);
//        popupStage.initStyle(StageStyle.UNDECORATED);

        BorderPane border = new BorderPane();
        
        Scene sc = new Scene(border, 300, 300);
        
        String css = Adventure.class.getResource("popup.css").toExternalForm();
        sc.getStylesheets().add(css);

        
        border.setStyle( "-fx-border-color: black;\n"
                + "-fx-border-width: 1;");


        HBox bottomRow = new HBox();
        HBox hbox2 = new HBox();
        HBox.setHgrow(hbox2, Priority.ALWAYS);
        bottomRow.getChildren().add(hbox2);
                
        
        popupStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                loadButton(startingScene,primaryStage);
                popupStage.close();
            }
        });
        
        
        Button closeButton = new Button("Close/Cancel");
        closeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                loadButton(startingScene,primaryStage);
                popupStage.close();
            }
        });

        
            
        Button selectButton = new Button("Select This Scene");

            selectButton.setOnAction(new EventHandler<ActionEvent>() {

              @Override
              public void handle(ActionEvent t) {
                  if(selection.value != -1)
                  {
                    if(mode.equals("load"))
                    {
//                        loadButton(sceneNumber,primaryStage);                
                        popupStage.close();
                    }
                    else if(mode.equals("child"))
                    {
                        loadButton(startingScene,primaryStage);
                        sceneList.get(currentIndex).addChild( getActionString(), selection.value);
                        
                        //refresh list of child scenes
                        setChildArea(primaryStage);
                        popupStage.close();                    
                    }
                  }
              }
            });


        bottomRow.getChildren().add(closeButton);
        bottomRow.getChildren().add(selectButton);
        border.setBottom(bottomRow);

	// Create the Lists for the ListViews
        ObservableList<String> listOfScenes = FXCollections.<String>observableArrayList();

    	// Create the ListView for the sceneChooser
	ListView<String> sceneChooser = new ListView<>(listOfScenes);
	// Set the Orientation of the ListView
	sceneChooser.setOrientation(Orientation.VERTICAL);
	// Set the Size of the ListView
	sceneChooser.setPrefSize(120, 200);


        
        // Update the variables when the selected scene changes
	sceneChooser.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>()
	{
	    public void changed(ObservableValue<? extends String> ov,
	            final String oldvalue, final String newvalue) 
	    {

                List<String> myList = new ArrayList<String>(Arrays.asList(newvalue.split(" ")));
                int sceneNumber = Integer.parseInt(myList.get(0)) - 1;
                
                loadButton(sceneNumber,primaryStage);                
                selection.value = sceneNumber;
            }});
                
        sceneChooser.setStyle("    -fx-background-color: WHEAT ;\n"
                                   + "    -fx-padding: 5 ;\n"
            //                       + "-fx-margin: 10;\n"
                + "-fx-border-color: blue;\n"
                + "-fx-border-insets: 5;\n"
                + "-fx-background-insets: 0,1,2;\n"
                + "-fx-border-width: 3;\n"
             //   + "-fx-border-style: dashed;\n" 
                                            );
        
        for (int index = 0; index < sceneList.size(); index++) {

            listOfScenes.add( Integer.toString(index + 1) + " : " + sceneList.get(index).text.replaceAll("(?s)<[^>]*>(\\s*<[^>]*>)*", " ") );

        }
        
        border.setCenter(sceneChooser);

        popupStage.setScene(sc);

        // Set the Title
	popupStage.setTitle("Select Scene");

        popupStage.show();
      }
    }
    
    
    
    public void loadButton(int newIndex, Stage primaryStage)
    {
            String savedText = sceneList.get(currentIndex).text;                    
            String newText = textEditor.getHtmlText();
            
            System.out.format("SavedText length: %s,  NewText length:%s\n", savedText.length(), newText.length() );
            
            if (newText.length() == 72 && savedText.length() == 72)
            {   // if the scene is empty, discard scene and load.
                sceneList.remove(currentIndex);
                currentIndex = newIndex;
                IDfield.setText(sceneList.get(newIndex).ID );
                textEditor.setHtmlText(sceneList.get(newIndex).text);
                setChildArea(primaryStage);
            }
            else if ( savedText.equals(newText)  )
            {                    
                //just set the current scene to the loaded scene.
                currentIndex = newIndex;
                IDfield.setText(sceneList.get(newIndex).ID );
                textEditor.setHtmlText(sceneList.get(newIndex).text);                        
                setChildArea(primaryStage);
            }
            else if ( !(savedText.equals(newText))  ) 
            {
                        saveScene();                                

                        //and load the new scene...
                        currentIndex = newIndex;
                        IDfield.setText(sceneList.get(newIndex).ID );
                        textEditor.setHtmlText(sceneList.get(newIndex).text);                        
                        setChildArea(primaryStage);
            }
        System.out.format("Length of scene list: %d\n", sceneList.size() );
    }
    
    
    public void newButton(Stage primaryStage)
    {
        String savedText = sceneList.get(currentIndex).text;
        String newText = textEditor.getHtmlText();
               
        System.out.format("SavedText length: %s,  NewText length:%s\n", savedText.length(), newText.length() );
        if (savedText.equals(newText) ) {
            System.out.print("NewText = SavedText");   }
        
        if (newText.length() != 0 && newText.length() != 72) // if the scene is empty, do nothing.
        {
            if( !(savedText.equals(newText))  )   //scene is not saved
            {
                saveScene();
            }  //scene is saved. Proceed.
            createScene(primaryStage);
        }
        System.out.format("Length of scene list: %d\n", sceneList.size() );
    }
    
    
    public void persistentGetNewID()
    {
        String replaceID = new String();

        while( findListIndex(replaceID) != -1 )
        {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Scene ID");
            dialog.setHeaderText("The ID field is blank, or the ID already exists in another scene.\nPlease enter a new scene ID.");
            dialog.setContentText("Scene ID:");

            //ButtonType buttonOK = new ButtonType("OK");
            //dialog.getButtonTypes().setAll(buttonOK);
            
            dialog.getDialogPane().lookupButton(ButtonType.CANCEL).setDisable(true);
            
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()){
                replaceID = result.get();
                IDfield.setText(replaceID);
            }
        }       
    }
    
    public void createScene(Stage primaryStage)
    {
        StoryScene currentscene = new StoryScene();  //create a new scene
        sceneList.add(currentscene);
        currentIndex = sceneList.size() - 1;
        sceneList.get(currentIndex).ID = Integer.toString(currentIndex + 1);
        textEditor.setHtmlText("");
        IDfield.setText(sceneList.get(currentIndex).ID);
        setChildArea(primaryStage);
    }
    
    
    public void saveScene()
    {
        System.out.format("currentText: %s\n", textEditor.getHtmlText() );

        int newindex = findListIndex(IDfield.getText());
        System.out.format("newindex: %d  currentIndex: %d", newindex, currentIndex);

        sceneList.get(currentIndex).addText(IDfield.getText(), textEditor.getHtmlText());
    }



}