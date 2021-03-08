package client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Client extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Network network = Network.getInstance();
        Parent root = FXMLLoader.load(getClass().getResource("client.fxml"));
        primaryStage.setTitle(" Chat Client");
        primaryStage.setScene(new Scene(root, 500, 700));
        primaryStage.getIcons().add(new Image(getClass().getResource("Battletoads.png").toExternalForm()));
        primaryStage.setResizable(false);
        primaryStage.show();
        primaryStage.setOnCloseRequest(request -> {
            try {
                network.writeMessage("/exit");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
