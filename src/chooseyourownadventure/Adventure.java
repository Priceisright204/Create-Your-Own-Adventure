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
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.AnchorPane;


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
    GridPane childGrid = new GridPane();

    
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
            .font(Font.font(null, FontWeight.BOLD, 14))
//            .translateY(50)
            .build();

        upperRight.getChildren().add(optionsText);

        HBox buttonRow1 = new HBox();
        
        //SAVE SCENE
        Button saveBtn = new Button("Save Text");
        saveBtn.setOnAction(e -> saveScene() );
        buttonRow1.getChildren().add(saveBtn);

        
        //NEW SCENE
        Button newBtn = new Button("New Scene");
        newBtn.setOnAction(e -> newButton(primaryStage) );
        buttonRow1.getChildren().add(newBtn);


        //LOAD SCENE
        Button loadBtn = new Button("Load Scene");
        loadBtn.setOnAction(e -> selectScene(primaryStage, "load") );
        buttonRow1.getChildren().add(loadBtn);
        
        
        upperRight.getChildren().add(buttonRow1);
        
        HBox buttonRow2 = new HBox();
        
        //Empty Button Row
        upperRight.getChildren().add(buttonRow2);
        
        //Empty Space
        VBox Vgrowbox = new VBox();
        VBox.setVgrow(Vgrowbox, Priority.ALWAYS);
        upperRight.getChildren().add(Vgrowbox);
        
        
        //Label for Child Scenes Area
        HBox labelBox = new HBox();
        
        Text childText = TextBuilder.create()
            .text("Child Scenes")
            .fill(Color.BLACK)
            .font(Font.font(null, FontWeight.BOLD, 14))
            .build();

        labelBox.getChildren().add(childText);
        
        HBox Hgrowbox = new HBox();
        HBox.setHgrow(Hgrowbox, Priority.ALWAYS);
        labelBox.getChildren().add(Hgrowbox);

        Button addChild = new Button("Connect Child Scene");
        addChild.setOnAction(e -> selectScene(primaryStage, "child") );
        labelBox.getChildren().add(addChild);

        upperRight.getChildren().add(labelBox);
        
        setChildArea(primaryStage);

        upperRight.getChildren().add(childGrid);

        
        
        
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
    

    public void setChildArea(Stage primaryStage)
    {
        childGrid.getChildren().clear();
        childGrid.setStyle("    -fx-background-color: WHEAT ;\n"
                                   + "    -fx-padding: 5 ;\n"
                + "-fx-border-color: black;\n"
                + "-fx-background-insets: 0,1,2;\n"
                + "-fx-border-width: 3;\n"
                                            );

        if(sceneList.get(currentIndex).childScenes.size() == 0)
        {
            Text noScenes = TextBuilder.create()
                .text("No Connected Scenes")
                .fill(Color.BLUE)
                .font(Font.font(null, FontWeight.NORMAL, 12))
                .build();
            childGrid.add(noScenes, 1, 1);
        }
        else
        {
//          int index = 0;
          Iterator< Entry<String,Integer> > itr = sceneList.get(currentIndex).childScenes.entrySet().iterator();

          for (int index = 0; index < sceneList.get(currentIndex).childScenes.size(); index++)
//          while( itr.hasNext() )
          {
//            index += 1;
            Entry<String,Integer> entry = itr.next();
            String key = entry.getKey();
            int childIndex = entry.getValue();
            
            Button edit = new Button( "Edit" );

//            class valueHolder { int value = -1 ; }
//            final valueHolder loopIndex = new valueHolder();
//            loopIndex.value = index;
            
            edit.setOnAction(new EventHandler<ActionEvent>() {

              @Override
              public void handle(ActionEvent t) {
                  editChild(key, primaryStage);
              }
            });

            childGrid.add( edit, 1, index);
            
            Text childID = new Text();
            if ( childIndex == currentIndex )
            {
                childID = TextBuilder.create()
                    .text( "  Link to Current Scene" )
                    .fill(Color.BLACK)
                    .font(Font.font(null, FontWeight.NORMAL, 12))
                    .build();
            }
            else
            {
                childID = TextBuilder.create()
                    .text( "  " + sceneList.get( childIndex ).ID )
                    .fill(Color.BLACK)
                    .font(Font.font(null, FontWeight.NORMAL, 12))
                    .build();
            }
            
            childGrid.add( childID, 2, index );
            
            /*
            String strippedText = sceneList.get(index).text.replaceAll("(?s)<[^>]*>(\\s*<[^>]*>)*", " ");

            final Text sceneText;

            sceneText = TextBuilder.create()
                    .text(strippedText)
                    .fill(Color.BLUE)
                    .font(Font.font(null, FontWeight.THIN, 12))
                    .build();
            childGrid.add(sceneText, 4, index);
            */
            
            
          }
          
        }
/*        for (int i = 0; i < 10; i++) {
            RowConstraints row = new RowConstraints(50);
            childGrid.getRowConstraints().add(row);
        }*/
    }
    
    public void editChild(String key, Stage primaryStage)
    {
        //Popup
        Stage popupStage = new Stage();
        popupStage.initOwner(primaryStage);
        popupStage.initStyle(StageStyle.UNDECORATED);

        BorderPane border = new BorderPane();

        border.setStyle( "-fx-border-color: black;\n"
                + "-fx-border-width: 1;"
                                        );
        Text headerText = TextBuilder.create()
            .text("Edit Or Remove Child Link")
            .fill(Color.BLUE)
            .font(Font.font(null, FontWeight.NORMAL, 16))
            .build();

        border.setTop(headerText);
        
        //Bottom Closebutton Row
        HBox bottomRow = new HBox();
        HBox hbox2 = new HBox();
        HBox.setHgrow(hbox2, Priority.ALWAYS);
        bottomRow.getChildren().add(hbox2);
        Button closebutton = new Button("Close/Cancel");
        closebutton.setOnAction(e -> popupStage.close() );
        bottomRow.getChildren().add(closebutton);

//        border.setBottom(hbox1);
        Scene sc = new Scene(border, 300, 300);
        popupStage.setScene(sc);
        popupStage.show();
        
        //refresh the list of child scenes
        setChildArea(primaryStage);
    }
    
    
    public String getActionString()
    {
        String actionString = new String();

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Scene ID");
        dialog.setHeaderText("Enter an action string to connect this scene to the next one.");
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
        //Popup
        Stage popupStage = new Stage();
        popupStage.initOwner(primaryStage);
        popupStage.initStyle(StageStyle.UNDECORATED);

        BorderPane border = new BorderPane();
        
//        VBox vertical = new VBox();

        Scene sc = new Scene(border, 300, 300);
        
//        border.prefHeightProperty().bind(sc.heightProperty());
//        border.prefWidthProperty().bind(sc.widthProperty());
        border.setStyle( "-fx-border-color: black;\n"
                + "-fx-border-width: 1;");

/*        
        vertical.setStyle( "-fx-border-color: black;\n"
                + "-fx-border-width: 1;");
*/
        
        Text headerText = new Text();
        if(mode.equals("load"))
        { headerText = TextBuilder.create()
            .text("Load Scene: Select Scene By ID")
            .fill(Color.BLUE)
            .font(Font.font(null, FontWeight.NORMAL, 16))
            .build();
        }
        else if(mode.equals("child"))
        { headerText = TextBuilder.create()
            .text("Add Child Scene: Select Scene By ID")
            .fill(Color.BLUE)
            .font(Font.font(null, FontWeight.NORMAL, 16))
            .build();
        }
        
        border.setTop(headerText);
//        vertical.getChildren().add(headerText);
        //Bottom Closebutton Row
        //anchor.setTopAnchor(headerText,0.0);

        HBox bottomRow = new HBox();
        HBox hbox2 = new HBox();
        HBox.setHgrow(hbox2, Priority.ALWAYS);
        bottomRow.getChildren().add(hbox2);
        Button closebutton = new Button("Close/Cancel");
        closebutton.setOnAction(e -> popupStage.close() );
        bottomRow.getChildren().add(closebutton);

        border.setBottom(bottomRow);
//        anchor.setBottomAnchor(bottomRow, 10.0);


//        GridPane sceneChooser = new GridPane();
        ScrollPane scroll = new ScrollPane();
        VBox sceneChooser = new VBox();
        scroll.setContent(sceneChooser);
        scroll.setHbarPolicy(ScrollBarPolicy.NEVER);
        scroll.setVbarPolicy(ScrollBarPolicy.ALWAYS);
        //scroll.setFitToHeight(true);
        scroll.setFitToWidth(true);
        scroll.setMaxHeight(100);
        //        border.setBottom(hbox1);

        
//        Scene sc = new Scene(anchor, 300, 300);

//        vertical.prefHeightProperty().bind(sc.heightProperty());
        
        
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

            HBox row = new HBox();
            sceneChooser.getChildren().add(row);
            row.setAlignment(Pos.CENTER_LEFT);
            
            Button selection = new Button( sceneList.get(index).ID );

            class valueHolder { int value = -1 ; }
            final valueHolder loopIndex = new valueHolder();
            loopIndex.value = index;
            
            selection.setOnAction(new EventHandler<ActionEvent>() {

              @Override
              public void handle(ActionEvent t) {
                popupStage.close();
                
                if(mode.equals("load"))
                {
                    loadButton(loopIndex.value,primaryStage);                
                }
                else if(mode.equals("child"))
                {
                    sceneList.get(currentIndex).addChild( getActionString(), loopIndex.value);
                    
                    //refresh list of child scenes
                    setChildArea(primaryStage);
                }
                
              }
            });
            
            row.getChildren().add(selection);
            
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
            
            row.getChildren().add(sceneText);
        }
        
        border.setCenter(sceneChooser);
//        vertical.getChildren().add(sceneChooser);

//        vertical.getChildren().add(bottomRow);
        
//        anchor.setTopAnchor(sceneChooser,10.0);


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
        textEditor.setHtmlText("");
        IDfield.setText("");
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