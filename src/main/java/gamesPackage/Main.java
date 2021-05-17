package gamesPackage;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Game.load("games.xml");
        Parent root = FXMLLoader.load(getClass().getResource("games.fxml"));
        primaryStage.setTitle("Játékok kezelése");
        primaryStage.setScene(new Scene(root, 825, 500));
        primaryStage.setResizable(false);
        primaryStage.show();
        primaryStage.setOnCloseRequest(event -> Game.save("games.xml"));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
