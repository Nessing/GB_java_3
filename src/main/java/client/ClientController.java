package client;

import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ClientController implements Initializable {
    public ListView<String> output;
    public TextField message;
    private Network network;
    private String path;
    private String name;
    private boolean isSave = false;

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
                    // установка флага для вывода сообщения в чат
                    isSave = false;
                    String msg = network.readMessage();
                    // отображение всех служебных сообщений с шаблонами
                    if (msg.startsWith("/help")) {
                        Platform.runLater(() -> {
                            output.getItems().add("/enter [nick] [password] - Для входа в чат\n");
                            output.getItems().add("/reg [nick] [password] - Для регистрации в чате\n");
                            output.getItems().add("/p [nick to] - Для отправки приватного сообщения\n");
                            output.getItems().add("/save - Для сохранения истории чата\n");
                        });
                    }
                    // если принятое сообщение начинается с "[Вы вошли", тогда из этого сообщения получаем ник
                    if (name == null && msg.startsWith("[Вы вошли")) {
                        String[] a = msg.split(" ");
                        // получает ник
                        name = a[6];
                        // путь к файлу
                        path = "src/main/resources/client/history/temp_";
                        File file = new File(path + name + ".txt");
                        // если файл с последними сообщениями есть, тогда считывается файл, иначе создается новый
                        // после закрытия
                        if (file.exists()) {
                            try (BufferedReader reader = new BufferedReader(new FileReader(path + name + ".txt"))) {
                                // массив для сборки сообщений из temp_*.txt
                                List<String> arrayMessage = new ArrayList<>();
                                // добавление в массив сообщения из temp_*.txt
                                while (reader.ready()) {
                                    arrayMessage.add(reader.readLine() + "\n" + reader.readLine() + "\n");
                                }
                                // добавление сообщения в чат клиента
                                Platform.runLater(() -> {
                                    for (String s : arrayMessage) {
                                        output.getItems().add(s);
                                    }
                                });
                            }
                        }
                    }
                    if (msg.equals("/exit") && name != null) {
                        // путь к файлу
                        path = "src/main/resources/client/history";
                        // выполнение метода сохранения последний 100 записей
                        saveTempFile("/temp_", path);
                        network.close();
                        break;
                    }
                    if (msg.startsWith("/save")) {
                        // путь к файлу
                        path = "src/main/resources/client/history";
                        // выполнение метода сохранения истории
                        saveToFile("/history_", path);
                    }
                    if (!isSave) {
                        Platform.runLater(() -> {
                            output.getItems().add(msg + "\n");
                        });
                    }
                }
            } catch (IOException e) {
                System.err.println("Server was broken");
                Platform.runLater(() -> output.getItems().add("Server was broken"));
                e.printStackTrace();
            }
        }).start();
    }

    // метод для сохранения последних 100 сообщений
    private void saveTempFile(String nameFile, String pathTo) {
        // если длина текущих сообщений больше или равно 100 сторк
        if (output.getItems().size() >= 100) {
            try (FileOutputStream out = new FileOutputStream(pathTo + nameFile +
                    name + ".txt")) {
                int size = output.getItems().size();
                // сохранение в файл строк начиная с последнего
                for (int i = 100; i > 0; i--) {
                    out.write(output.getItems().get(size - i).getBytes(StandardCharsets.UTF_8));
                }
                out.flush();
                isSave = true;
                // печатается в чат оповещение об сохранении истории с путем
                if (isSave) {
                    Platform.runLater(() -> {
                        output.getItems().add("История сохранена: " + pathTo + nameFile +
                                name + ".txt\n");
                    });
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            // если меньше, тогда добавляются все строки, используя метод saveToFile
        } else saveToFile(nameFile, pathTo);
    }

    // метод для сохранения истории в файл
    private void saveToFile(String nameFile, String pathTo) {
        try (FileOutputStream out = new FileOutputStream(pathTo + nameFile +
                name + ".txt")) {
            for (int i = 0; i < output.getItems().size(); i++) {
                out.write(output.getItems().get(i).getBytes(StandardCharsets.UTF_8));
            }
            out.flush();
            isSave = true;
            // печатается в чат оповещение об сохранении истории с путем
            if (isSave) {
                Platform.runLater(() -> {
                    output.getItems().add("История сохранена: " + pathTo + nameFile +
                            name + ".txt\n");
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
