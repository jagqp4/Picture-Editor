/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jagqp4finalproject1;

import java.util.HashMap;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

/**
 *
 * @author Jordan
 */
public abstract class SceneSwitcher {
    public static Scene scene;
    public static final HashMap<String, SceneSwitcher> controllers = new HashMap<>();
    
    private Parent root;  
    
    public void setRoot(Parent root) {
        this.root = root;
    }
    
    public Parent getRoot() {
        return root;
    }

    public static SceneSwitcher add(String name) {
        SceneSwitcher controller;
        
        controller = controllers.get(name);
        
        if (controller == null) {
            try {
                FXMLLoader loader = new FXMLLoader(SceneSwitcher.class.getResource(name + ".fxml"));
                Parent root = (Parent)loader.load();
                controller = (SceneSwitcher)loader.getController();
                controller.setRoot(root);
                controllers.put(name, controller);
            } catch (Exception ex) {
                System.out.println("Error loading " + name + ".fxml\n" + ex);
                controller = null;
            }
        }
        
        return controller;
    }
    
    public static void switchTo(String name, double red, double green, double blue, double opacity, Color c) {
        SceneSwitcher controller = controllers.get(name);
        
        if (controller == null) {
            controller = add(name);
        }
        
        if (controller != null) {
            if (scene != null) {
                scene.setRoot(controller.getRoot());
                if(name.equals("FinalProjectFXML")){
                   ((FinalProjectController)controller).red = red;
                   ((FinalProjectController)controller).blue = blue;
                   ((FinalProjectController)controller).green = green;
                   ((FinalProjectController)controller).paintColor = c;
                   ((FinalProjectController)controller).opacity = (0.01 * opacity);
                                     
                   Pane parent = (Pane) scene.getRoot();
                   AnchorPane anchorPane = (AnchorPane) parent.getChildren().get(3);
                   
                   Label redLabel = (Label) anchorPane.getChildren().get(1);
                   redLabel.setText("Red" + red);
                   
                   Label greenLabel = (Label) anchorPane.getChildren().get(2);
                   greenLabel.setText("Green" + green);
                   
                   Label blueLabel = (Label) anchorPane.getChildren().get(3);
                   blueLabel.setText("Blue" + blue);
                   
                   
                } 
            }
        }
    }
    
    public static SceneSwitcher getControllerByName(String name) {
        return controllers.get(name);
    }
    

}

