package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ClientHandler implements Runnable {

    private Socket socket;
    private Server server;
    private DataInputStream in;
    private DataOutputStream out;
    private String nickName;

    public ClientHandler(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    public String getNickName() {
        return nickName;
    }

    // подключение к БД
    public static Connection getConnection() throws Exception {
        Class.forName("org.sqlite.JDBC");
        return DriverManager.getConnection("jdbc:sqlite:src/databases/clientsdatabase.db");
    }

    // установка ника
    public boolean setNickName(String nickName) {
        String req = "SELECT * FROM clients";
        ResultSet resultSet;
        if (nickName.startsWith("/exit")) {
            try {
                out.writeUTF("/exit");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // условие при входе в чат
        if (nickName.startsWith("/enter")) {
            String[] nickSplit = nickName.split(" ");
            try (PreparedStatement preparedStatement = getConnection().prepareStatement(req)) {
                resultSet = preparedStatement.executeQuery();
                // проход по всей БД
                while (resultSet.next()) {
                    // если имя и пароль совпадают, то происходит вход
                    if (resultSet.getString("Name").equals(nickSplit[1]) &&
                            resultSet.getString("Password").equals(nickSplit[2])) {
                        this.nickName = nickSplit[1];
                        resultSet.close();
                        return true;
                    }
                }
                // в ином случае вход не происходит и печатается сообщение
                sendMessage("Неверное имя или пароль!\nДля регистрации введите /reg [nick] [password]");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // условие при регистрации нового пользователя
        if (nickName.startsWith("/reg")) {
            String[] nickSplit = nickName.split(" ");
            try(PreparedStatement preparedStatement = getConnection().prepareStatement(req)) {
                resultSet = preparedStatement.executeQuery();
                // проход по всей БД
                while (resultSet.next()) {
                    // если имя существует, то печатается сообщение
                    if(resultSet.getString("Name").equals(nickSplit[1])) {
                        sendMessage("Такой ник уже существует!");
                        resultSet.close();
                        return false;
                    }
                }
                // в ином случае меняется строка запроса и на добавление строки в БД
                req = "INSERT INTO clients (Name, Password) VALUES (?, ?)";
                try(PreparedStatement addNewClientDB = getConnection().prepareStatement(req)) {
                    addNewClientDB.setString(1, nickSplit[1]);
                    addNewClientDB.setString(2, nickSplit[2]);
                    addNewClientDB.executeUpdate();
                    System.out.println("new client registered");
                    sendMessage("Вы успешно зарегистрировались!");
                } catch (SQLDataException e) {
                    System.err.println("Произошла ошибка при записи в БД");
                    sendMessage("ОШИБКА РЕГИСТРАЦИИ");
                    return false;
                }
                // после успешной регистрации происходит автоматический вход в чат
                this.nickName = nickSplit[1];
                sendMessage("/enter " + nickSplit[1] + " " + nickSplit[2]);
                resultSet.close();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public void run() {
        try {
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());
            System.out.println("Client started");
            setNick();

            while (true) {
                String message = in.readUTF();
                String[] msgArray = message.split("");
                if (message.startsWith("/exit")) out.writeUTF(message);
                if (message.startsWith("/save")) out.writeUTF(message);
                if (message.startsWith("/help")) out.writeUTF(message);
                // отправка приватного сообщения
                else if (msgArray.length >= 2) {
                    String[] arr = message.split(" ");
                    if (arr[0].equals("/p")) {
                        sendPrivateMessage(message);
                    }
                    // перенос строки
                    List<String> words = Arrays.asList(message.split(""));
                    if (words.size() >= 70) {
                        for (int i = 0; i < words.size(); i++) {
                            if(i % 70 == 0) {
                                words.set(i, words.get(i) + "\n");
                            }
                        }
                    }
                    String mes = words.stream().collect(Collectors.joining(""));
                    server.sendMessageToAll(nickName + ": " + mes);
                } else {
                    server.sendMessageToAll(nickName + ": " + message);
                }
            }

        } catch (Exception e) {
            System.err.println("Handler disconnected");
            try {
                server.sendMessageToAll("=== " + this.getNickName() + " покинул чат ===");
            } catch (IOException ex) {
                System.err.println("client lose server");
            }
            server.removeClient(this);
        }
    }

    private void setNick() throws IOException {
        sendMessage("=== Введите свой ник для входа === \n/enter [nick] [password]");
        while (true) {
            if (setNickName(in.readUTF())) {
                break;
            }
        }
        sendMessage("[Вы вошли в чат как { " + this.getNickName() + " }]");
        sendMessage("/help - отобразить все команды чата");
        server.sendMessageToAll("=== " + this.getNickName() + " вошел в чат ===");
        System.out.println("client identified nick: " + getNickName());
    }

    private void sendPrivateMessage(String message) throws IOException {
        List<String> array = Arrays.asList(message.split(" "));
        try {
            String nickTo = array.get(1);
            array.set(0, "[private]");
            array.set(1, "{ " + this.getNickName() + " to " + nickTo + " }:");
            String sendMessage = array.stream().collect(Collectors.joining(" "));
            server.sendMessagePrivate(getNickName(), nickTo, sendMessage);
        } catch (Exception e) {
            server.sendMessagePrivate(getNickName(), this.getNickName(), "=== Ник получателя не был введен! ===");
        }
    }

    public void sendMessage(String message) throws IOException {
        out.writeUTF(message);
        out.flush();
    }
}
