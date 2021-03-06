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
//import javafx.scene.Node;  //conflicts. Referring to it by full name.
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

import java.io.*;
import javafx.stage.FileChooser;
import java.awt.Desktop;
import java.nio.charset.StandardCharsets; 
import java.nio.file.Files; 
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

//import org.w3c.dom.*;
import org.dom4j.*;
//import org.dom4j.Document;
//import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import java.util.*;
import org.jaxen.*;

//import javax.xml.parsers.*;
//import org.xml.sax.SAXException;


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
    public String childSelection; //the action key that is selected.
//    public String toggleValue = "#";
    File file;
    
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
//        final HBox titleBox = new HBox();
        final Text upperLeft = TextBuilder.create()
            .text("Scene Editor\t\t\t\t\t")
//            .x(100)
//            .y(50)
             .fill(Color.BLUE)
            .font(Font.font(null, FontWeight.BOLD, 20))
//            .translateY(50)
            .build();

       final HBox IDspace = new HBox();
//       IDspace.setAlignment(Pos.CENTER_LEFT);
       
       final Text IDlabel = TextBuilder.create()
            .text("ID:")
//            .x(100)
//            .y(50)
             .fill(Color.BLACK)
            .font(Font.font(null, FontWeight.LIGHT, 18))
//            .translateY(50)
            .build();

        IDfield = new TextField();
        IDfield.setStyle("-fx-background-color: WHEAT;"
                    + "-fx-text-fill: BLACK;"
                    + "-fx-font-size: 11pt;"
        + "-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");
        IDfield.setPromptText("Scene ID / Short Title");
        
        //prevent users from entering duplicate Scene ID's.
        //this one listens for every character you type. It's a bit annoying. If duplicate, erase the last character you typed.
        IDfield.textProperty().addListener((observable, oldValue, newValue) -> {
//            if(findListIndex(newValue)!=-1 && findListIndex(newValue)!=currentIndex)
            if( newValue.contains(":") )
            {
                IDfield.setText(oldValue);
            }
        });

        //Prevent users from entering duplicate Scene ID's.
        //Runs when the focus changes. If it's a duplicate, set the ID to the scene #.
        IDfield.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            String newString = IDfield.getText();
            if( (findListIndex(newString)!=-1 && findListIndex(newString)!=currentIndex ) || newString.isEmpty() )
            {
//                IDfield.setText(Integer.toString(currentIndex + 1));
                IDfield.setText(sceneList.get(currentIndex).ID );
            }
        });

       IDspace.getChildren().add(IDlabel);
       IDspace.getChildren().add(IDfield);

        
        titleBox.setLeft(upperLeft);
        titleBox.setRight(IDspace);
//        titleBox.setRight(IDlabel);
//        titleBox.setRight(IDfield);
//        titleBox.getChildren().add(upperLeft);
//        titleBox.getChildren().add(IDlabel);
//        titleBox.getChildren().add(IDfield);


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
                
        //DELETE SCENE
        Button deleteBtn = new Button("Delete Scene");
        deleteBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
              if(sceneList.size()!=1)
              {
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Delete Scene");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    // ... user chose OK
                    sceneList.remove(currentIndex);
                    if(currentIndex >= sceneList.size())
                    {   currentIndex--;   }
                        IDfield.setText(sceneList.get(currentIndex).ID );
                        textEditor.setHtmlText(sceneList.get(currentIndex).text);                        
                        setChildArea(primaryStage);
                }
              }
            }});
        buttonRow1.getChildren().add(deleteBtn);
        
        upperRight.getChildren().add(buttonRow1);

        
        //Empty Row
        Text emptySpace = TextBuilder.create().text("   ").build();
        upperRight.getChildren().add(emptySpace);        

        //Row containing child options label
        //HBox labelBox1 = new HBox();
        /*
        final Text childOptions = TextBuilder.create()
            .text("Child Scene Options")
//            .x(100)
//            .y(50)
             .fill(Color.BLACK)
            .font(Font.font(null, FontWeight.BOLD, 14))
//            .translateY(50)
            .build();
        
//        labelBox1.getChildren().add(childOptions);
        
        HBox Hgrowbox0 = new HBox();
        HBox.setHgrow(Hgrowbox0, Priority.ALWAYS);
        labelBox1.getChildren().add(Hgrowbox0);
        */
        
//        upperRight.getChildren().add(childOptions);
                
        //Child Scene Options
//        HBox buttonRow2 = new HBox();        
        
        //CONNECT CHILD
        Button addChild = new Button("Connect Child Scene");
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

        upperRight.getChildren().add(addChild);
        
//        upperRight.getChildren().add(labelBox1);

 
        
/*        
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
                setChildArea(primaryStage);
                
                if(toggleValue == "#")
                {  IDfield.setEditable(false);  }
                if(toggleValue == "ID")
                {  IDfield.setEditable(true);  }
            }
      }
    });
*/
        
        
        //Empty Row
        Text emptySpace1 = TextBuilder.create().text("   ").build();
        upperRight.getChildren().add(emptySpace1);

        final Text globalOptions = TextBuilder.create()
            .text("Global Options")
//            .x(100)
//            .y(50)
             .fill(Color.BLACK)
            .font(Font.font(null, FontWeight.BOLD, 14))
//            .translateY(50)
            .build();

        upperRight.getChildren().add(globalOptions);
        
        HBox globalRow = new HBox();

        Button saveall = new Button("Save");
        saveall.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                if(file == null)
                {
                    saveButton(primaryStage);
                }
                else
                {
                    saveFile();
                }
            }
        });
        globalRow.getChildren().add(saveall);
        
        Button savenew = new Button("Save As");
        savenew.setOnAction(e -> saveButton(primaryStage) );

        globalRow.getChildren().add(savenew);
        
        
        Button loadall = new Button("Load");
        loadall.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open Resource File");
                
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Story File (*.story)", "*.story");
                fileChooser.getExtensionFilters().add(extFilter);
                
//                fileChooser.showOpenDialog(primaryStage);

                File temp = fileChooser.showOpenDialog(primaryStage);
                if(temp!=null)
                {
                    file = temp;
                    loadFile(primaryStage);
                }
            }
        });
        globalRow.getChildren().add(loadall);
        
        upperRight.getChildren().add(globalRow);
        
        //Empty Row
        Text emptySpace2 = TextBuilder.create().text("   ").build();
        upperRight.getChildren().add(emptySpace2);
        
        //CREATE EMPTY STORY
        Button newStory = new Button("Create Empty Story");
        newStory.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
              if(sceneList.size()!=1)
              {
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Create Empty Story");
                alert.setHeaderText(null);
                alert.setContentText("Save current story?");

                ButtonType buttonYes = new ButtonType("Yes");
                ButtonType buttonNo = new ButtonType("No");
                ButtonType buttonCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

                alert.getButtonTypes().setAll(buttonYes, buttonNo, buttonCancel);
                
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == buttonYes){
                    // ... user chose Yes
                    if(file == null)
                    {  saveButton(primaryStage); }
                    else
                    {  saveFile(); }
                }
                if (result.get() == buttonYes || result.get() == buttonNo)
                {
                    sceneList = new ArrayList<StoryScene>();
                    currentIndex = 0;
                    StoryScene newScene = new StoryScene();
                    sceneList.add(newScene);
                    IDfield.setText("");
                    textEditor.setHtmlText("");
                }
              }
            }});
        upperRight.getChildren().add(newStory);
        
        //Empty Space
        VBox Vgrowbox = new VBox();
        VBox.setVgrow(Vgrowbox, Priority.ALWAYS);
        upperRight.getChildren().add(Vgrowbox);
        
        







       //Lower right
        VBox lowerRight = new VBox();
        
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

        lowerRight.getChildren().add(labelBox);
        
        setChildArea(primaryStage);

        ListView<String> childArea = new ListView<>(childList);
        lowerRight.getChildren().add(childArea);
        
        // Update the variables when the selected scene changes
	childArea.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>()
	{
	    public void changed(ObservableValue<? extends String> ov,
	            final String oldvalue, final String newvalue) 
	    {

                //List<String> myList = new ArrayList<String>(Arrays.asList(newvalue.split(" ")));
                //int sceneNumber = Integer.parseInt(myList.get(0)) - 1;

                /*
                if(toggleValue == "#")
                {
                    childSelection = Integer.parseInt(newvalue.substring( newvalue.indexOf(":") + 2)) - 1;                
                }
                else if(toggleValue == "ID")
                {
                    childSelection = findListIndex( newvalue.substring( newvalue.indexOf(":") + 2) );
                }
                */
                try{
                  childSelection = newvalue.substring( newvalue.indexOf(":") + 2);
                } catch ( java.lang.NullPointerException ex2 ) { System.out.println("Not sure why this is crashing. Don't care.");}

          }});
        
//        upperRight.getChildren().add(childGrid);

        

        splitPane2.getItems().add(upperRight);
        splitPane2.getItems().add(lowerRight);

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

            
//        IDfield.setEditable(false);

        sceneList.get(currentIndex).ID = Integer.toString(currentIndex + 1);
        
    }
    
    private void saveButton(Stage primaryStage)
    {
        saveScene(); //first update the values in memory for the current scene!
                
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Story");

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Story File (*.story)", "*.story");
        fileChooser.getExtensionFilters().add(extFilter);

        file = fileChooser.showSaveDialog(primaryStage);

        saveFile();
    }
    
    
    private void saveFile() //File file)
    {
        /* FileOutputStream f = null;
        try { f = new FileOutputStream(file); }
        catch(FileNotFoundException ex){
            System.out.println("File Not Found");  } */
        
        if(file!=null)
        {
        try { 

            Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8")); 
        
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            writer.write("<story>\n");
//            writer.write("<settings>");
//            writer.write("<connectby>" + toggleValue + "</connectby>");
//            writer.write("</settings>\n");

//            writer.write("<scenelist length=\"" + Integer.toString(sceneList.size()) + "\">\n");
            
            int sceneIndex = -1;
            for(StoryScene current: sceneList)
            {
                sceneIndex += 1; //scene index

                writer.write("\n    <scene index=\"" + Integer.toString(sceneIndex) + "\">");
                writer.write("<ID>" + current.ID + "</ID>");
                writer.write("<text><![CDATA[" + current.text + "]]></text>");
                
                if(current.childScenes.size() != 0)
                {
                    //writer.write("<childscenes>");
                    
                    Iterator< Entry<String,Integer> > itr = current.childScenes.entrySet().iterator();
                    
                    while( itr.hasNext() )
                    {
                        Entry<String,Integer> entry = itr.next();
                        String key = entry.getKey();
                        int childIndex = entry.getValue();
                        writer.write("<link>");
                        writer.write("<actionstring>" + key + "</actionstring>");
                        writer.write("<childIndex>" + Integer.toString(childIndex) + "</childIndex>");
                        writer.write("</link>");
                    }          
                    //writer.write("</childscenes>");                    
                }
                writer.write("</scene>\n");
            }
//            writer.write("</scenelist>\n");            
            writer.write("</story>");
            
            
            writer.close();

        }
//            catch(  FileNotFoundException ex1 ) { System.out.println("File Not Found"); }
//            catch( UnsupportedEncodingException ex2 ) { System.out.println("Unsupported Encoding"); }
            catch( IOException ex3 ) { System.out.println("IOException"); }
        }        
}

    
    private void loadFile(Stage primaryStage) {

//      if(file!=null) //we already tested in for null in the handle in Start().
//      {
        try {

            /*
            InputStream is = new FileInputStream(file);
            BufferedReader buf = new BufferedReader(new InputStreamReader(is));
            String line = buf.readLine();
            StringBuilder sb = new StringBuilder();
            while(line != null)
                { sb.append(line).append("\n");
                line = buf.readLine(); } 
            //String fileAsString = sb.toString();
            //System.out.println("Contents : " + fileAsString);
            */
            
            SAXReader reader = new SAXReader();
            Document document = reader.read(file);
            
            //Element classElement = document.getRootElement();
            
            //reset the scene list
            sceneList = new ArrayList<StoryScene>();
            
            List<Node> nodes = document.selectNodes("/story/scene");
            
            for (Node node : nodes) {
                StoryScene nextscene = new StoryScene();
                nextscene.ID = node.selectSingleNode("ID").getText();
                
                String sceneText = node.selectSingleNode("text").asXML();
                
                nextscene.text = sceneText.substring(15, sceneText.length() - 10);
                
                List<Node> childScenes = node.selectNodes("./link");
                
                for(Node child: childScenes)
                {
                    nextscene.addChild(child.selectSingleNode("actionstring").getText(), Integer.parseInt(child.selectSingleNode("childIndex").getText()) );
                }
                sceneList.add(nextscene);
            }
            
            //load the first scene in the list.
            currentIndex = 0;
            IDfield.setText(sceneList.get(0).ID );
            textEditor.setHtmlText(sceneList.get(0).text);                        
            setChildArea(primaryStage);

        } catch (DocumentException ex) {
            Logger.getLogger(Adventure.class.getName()).log(Level.SEVERE, null, ex); }
//      }
    }

    public void setChildArea(Stage primaryStage)
    {
            childList.clear();
        
//        childSelection = -1;
        childSelection = null;

        Iterator< Entry<String,Integer> > itr = sceneList.get(currentIndex).childScenes.entrySet().iterator();

//        for (int index = 0; index < sceneList.get(currentIndex).childScenes.size(); index++)
          while( itr.hasNext() )
          {
//            index += 1;
            Entry<String,Integer> entry = itr.next();
            String key = entry.getKey();
            int childIndex = entry.getValue();
            
//            childList.add( Integer.toString(childIndex) + " " + sceneList.get(index).text.replaceAll("(?s)<[^>]*>(\\s*<[^>]*>)*", " ") );
            
//            if(toggleValue == "#")
//            {
//                childList.add( Integer.toString(childIndex + 1) + " : " +  key );
//            }
//            else if(toggleValue == "ID")
//           {
//                childList.add( Integer.toString(childIndex + 1) + " : " + sceneList.get(entry.getValue()).ID );
                childList.add( sceneList.get(childIndex).ID +  " : " + key );
//            }
        }
    }
    
    public void editChild(Stage primaryStage)
    {
//      if(childSelection != -1)
      if(!childSelection.isEmpty() && childSelection!=null )
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
        TextField sceneID = new TextField();
        
        
        grid.add(new Label("Action Text:"), 0, 0);
        grid.add(action, 1, 0);

//        if(toggleValue == "ID")
//        {
            grid.add(new Label("Scene ID:"), 0, 1);
            grid.add(sceneID,1,1);
            sceneID.setText(sceneList.get(sceneList.get(currentIndex).childScenes.get( childSelection ) ).ID );            
//        }

        grid.add(new Label("Scene Number:"), 0, 2);
        grid.add(sceneNumber, 1, 2);
        
        action.setText( childSelection );
        
        final TextFormatter<Integer> formatter = new TextFormatter<>(new IntegerStringConverter());
        sceneNumber.setTextFormatter(formatter);        
        formatter.setValue( sceneList.get(currentIndex).childScenes.get( childSelection ) + 1);
//        formatter.setValue( childSelection );
        
        
        // Enable/Disable login button depending on whether a username was entered.
        javafx.scene.Node loginButton = dialog.getDialogPane().lookupButton(submitButton);
        loginButton.setDisable(true);

        // Do some validation (using the Java 8 lambda syntax).
        action.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());  });
        sceneNumber.textProperty().addListener((observable, oldValue, newValue) -> {
            try{
                loginButton.setDisable(newValue.trim().isEmpty() || newValue.trim() == null || Integer.parseInt(newValue) > sceneList.size() || Integer.parseInt(newValue) <= 1); 
                if(Integer.parseInt(newValue)<= sceneList.size() && Integer.parseInt(newValue)>=1)
                    { sceneID.setText(sceneList.get(Integer.parseInt(newValue) - 1).ID); }
               } catch(java.lang.NumberFormatException ex1) {System.out.println("Empty box error (??)");}
        });
        sceneID.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty() || findListIndex(newValue)==-1 );
            if(findListIndex(newValue)!=-1)
            { formatter.setValue(findListIndex(newValue)+1); 
              loginButton.setDisable(false); }
        });
        
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
                int sceneNumber=-1;
                /*
                if(toggleValue=="#")
                {
                    List<String> myList = new ArrayList<String>(Arrays.asList(newvalue.split(" ")));
                    sceneNumber = Integer.parseInt(myList.get(0)) - 1;
                }
                else if(toggleValue=="ID")
                {*/
                    int stopSpot = newvalue.indexOf(":") - 1;
                    String ID = newvalue.substring(0, stopSpot);
                    sceneNumber = findListIndex(ID);
//                }
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

            /*
            if(toggleValue == "#")
            {
                listOfScenes.add( Integer.toString(index + 1) + " : " + sceneList.get(index).text.replaceAll("(?s)<[^>]*>(\\s*<[^>]*>)*", " ") );
            }
            else if(toggleValue == "ID")
            {*/
                listOfScenes.add( sceneList.get(index).ID + " : " + sceneList.get(index).text.replaceAll("(?s)<[^>]*>(\\s*<[^>]*>)*", " ") );
            //}
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
                //Nevermind don't discard the scene.
//                sceneList.remove(currentIndex);
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