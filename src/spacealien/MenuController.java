package spacealien;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MenuController  {


    @FXML
    private ImageView start;

    @FXML
    private void klikini(MouseEvent event) {
        // Handle the click event
        try {
           
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLDocument.fxml")); 
            Pane gamePane = loader.load();

            
            Scene gameScene = new Scene(gamePane);

            
            Stage currentStage = (Stage) start.getScene().getWindow();
            currentStage.setScene(gameScene);
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
