package client;

import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientController implements Initializable {
    public ListView<String> output;
    public TextField message;
    private Network network;

    public void sendMessage() throws IOException {
        network.writeMessage(message.getText());
        message.clear();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        network = Network.getInstance();

        new Thread(() -> {
            try {
                while (true) {
                    String msg = network.readMessage();
                    if (msg.equals("/exit")) {
                        network.close();
                        break;
                    }
                    Platform.runLater(() -> output.getItems().add(msg));
                }
            } catch (IOException e) {
                System.err.println("Server was broken");
                Platform.runLater(() -> output.getItems().add("Server was broken"));
                e.printStackTrace();
            }
        }).start();
    }
}
