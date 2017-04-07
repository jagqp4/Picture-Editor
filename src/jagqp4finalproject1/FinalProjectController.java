package jagqp4finalproject1;

import java.awt.Color; //Switching between color imports was really confusing
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.net.MalformedURLException;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
//import javafx.scene.paint.Color;  Couldn't create a new color with this import!
import javax.imageio.ImageIO;

public class FinalProjectController extends SceneSwitcher implements Initializable {

    @FXML
    private VBox root;
    
    @FXML
    private VBox imageContainer;
    
    @FXML
    private ScrollPane scrollPane;
    
    @FXML
    private Canvas canvas;
    
    @FXML
    public Label redLabel;
    
    @FXML
    public Label greenLabel;
    
    @FXML
    public Label blueLabel;
    
    public ImageView currImageView;
    private int imageWidth;
    private int imageHeight;
    
    public double red;
    public double green;
    public double blue;
    public double opacity;
    public javafx.scene.paint.Color paintColor;  //set in other scene
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
    }  
    
    public void handleOpen(ActionEvent event){
        
        FileChooser fileChooser = new FileChooser();
        Stage stage = (Stage) root.getScene().getWindow();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image File", "*.jpg", "*.jpeg", "*.png"));
        File file = fileChooser.showOpenDialog(stage);
        if(file != null){
            setupImage(file);
        }
    }
    
public void setupImage(File file){  
    String imageUrl = null;
        try {
            imageUrl = file.toURI().toURL().toExternalForm();
        } catch (MalformedURLException ex) {
            Logger.getLogger(FinalProjectController.class.getName()).log(Level.SEVERE, null, ex);
        }
    ImageView myImageView = new ImageView(imageUrl);
    Image myImage = myImageView.getImage();
    currImageView = myImageView;
    
    imageHeight = (int) myImage.getHeight();
    imageWidth = (int) myImage.getWidth();
    
    canvas.setWidth(imageWidth);
    canvas.setHeight(imageHeight);
    
    writeImageViewToCanvas(myImageView);   
}

public void writeImageViewToCanvas(ImageView myImageView){
    Image image = myImageView.getImage();
    PixelReader pixelReader = image.getPixelReader();
    GraphicsContext gc = canvas.getGraphicsContext2D();
    PixelWriter pixelWriter = gc.getPixelWriter();
    for (int x = 0; x < imageWidth; x++){
           for (int y = 0; y < imageHeight; y++){
               int argb = pixelReader.getArgb(x, y);
               pixelWriter.setArgb(x, y, argb);
           }
    }
}
    
    
 public void handleSave(ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        Stage stage = (Stage) root.getScene().getWindow();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image File", "*.jpg", "*.jpeg", "*.png"));
        
        File file = fileChooser.showSaveDialog(stage);
        
        FileOutputStream writer = null;
        
        if(file != null){
             try {
                WritableImage writableImage = new WritableImage(imageWidth, imageHeight);
                canvas.snapshot(null, writableImage);
                RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                ImageIO.write(renderedImage, "png", file);
            } catch (IOException ex) {
                Logger.getLogger(FinalProjectController.class.getName()).log(Level.SEVERE, null, ex);
            }
           
        }    
    }
    
    @FXML
    private void handleSwitch(ActionEvent event){
        double test = 0.0;
        switchTo("ColorFXML", test, test, test, test, null);
        
    }
    
    @FXML
    public void handleDraw(){
        manipulateImage(currImageView);
    }
    
    @FXML
    private void manipulateImage(ImageView imgV){
        if(imgV == null){
            System.out.println("You need to upload an image first!");
            return;
        }
        //Color awtC = new Color((float)red, (float)green, (float)blue);
        Image image = imgV.getImage();
        PixelReader pixelReader = image.getPixelReader();
       
        PixelFormat format = pixelReader.getPixelFormat();//.getType();
        //System.out.println("Pixel Format" + format); 
        
        //NEW!!!
        BufferedImage buffImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TRANSLUCENT);
        Graphics2D graphics = buffImage.createGraphics();
       
        GraphicsContext gc = canvas.getGraphicsContext2D();
        PixelWriter pw = gc.getPixelWriter();
        int test = pixelReader.getArgb(10, 10);
       
        //byte[] imageData = new byte[imageWidth * imageHeight * 4];
        int i = 0;
        for (int x = 0; x < imageWidth; x++){
           for (int y = 0; y < imageHeight; y++){
              int argb = pixelReader.getArgb(x, y);
              
              javafx.scene.paint.Color currentColor = pixelReader.getColor(x, y);
              javafx.scene.paint.Color finalColor = modifyPixelsByColor(currentColor);
               pw.setColor(x, y, finalColor);
               //pw.setArgb(x, y, argb);
            }
       }
}
 
    public javafx.scene.paint.Color modifyPixelsByColor(javafx.scene.paint.Color oldColor){
        double newRed = red;
        double newGreen = green;
        double newBlue = blue;
        
        double oldRed = oldColor.getRed();
        double oldGreen = oldColor.getGreen();
        double oldBlue = oldColor.getBlue();
        //These values are arbitrary. 0.3 seemed to produce interesting results
        if(newRed > oldRed){
            oldRed = oldRed + 0.3;
            if(oldRed > 1.0){
                oldRed = 1.0;
            }
        }
        else{
            oldRed = oldRed - 0.3;
            if(oldRed < 0.0){
                oldRed = 0.0;
            }
        }
        if(newGreen > oldGreen){
            oldGreen = oldGreen + 0.3;
            if(oldGreen > 1.0){
                oldGreen = 1.0;
            }
        }
        else{
            oldGreen = oldGreen - 0.3;
            if(oldGreen < 0.0){
                oldGreen = 0.0;
            }
        }
        if(newBlue > oldBlue){
            oldBlue = oldBlue + 0.3;
            if(oldBlue > 1.0){
                oldBlue = 1.0;
            }
        }
        else{
            oldBlue = oldBlue - 0.3;
            if(oldBlue < 0.0){
                oldBlue = 0.0;
            }
        }        
        return new javafx.scene.paint.Color((float) oldRed, (float) oldGreen, (float) oldBlue, opacity);

    }
    
    @FXML
    private void handleAbout(ActionEvent event){
        switchTo("AboutFXML",0,0,0,0,null);
    }
}
    
