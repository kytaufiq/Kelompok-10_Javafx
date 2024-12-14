package spacealien;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SpaceAlien extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Memuat Menu.fxml terlebih dahulu
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Menu.fxml"));
        Parent root = loader.load();

        
        Scene scene = new Scene(root, 800, 600);

       
        primaryStage.setScene(scene);
        primaryStage.setTitle("Space Alien Game");
        primaryStage.setResizable(true);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
