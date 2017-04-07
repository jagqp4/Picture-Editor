/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jagqp4finalproject1;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;

/**
 *
 * @author Jordan
 */
public class ColorController extends SceneSwitcher implements Initializable {
    
    @FXML
    private ColorPicker colorPicker;
    
    @FXML
    private Slider opacityPicker;
    
    @FXML
    public Label redLabel;
    
    @FXML
    private Label greenLabel;
    
    @FXML
    private Label blueLabel;
     
    public double red;
    public double green;
    public double blue;
    public double opacity;
    public Color chosenColor;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    
    }
    
    @FXML
    private void handleSwitch(ActionEvent event){
        opacity = opacityPicker.getValue();
        switchTo("FinalProjectFXML",red,green,blue,opacity,chosenColor);
    }
    
    @FXML
    private void showData(ActionEvent event){
         Color chosenColor = colorPicker.getValue();
         red = chosenColor.getRed();
         green = chosenColor.getGreen();
         blue = chosenColor.getBlue();
         
         String labelText = "Red: " + red;
         redLabel.setText(labelText);
         greenLabel.setText("Green: " + green);
         blueLabel.setText("Blue: " + blue);;
    }
    
}
