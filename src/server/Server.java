package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ConcurrentLinkedDeque;

public class Server {
    private final static int PORT = 8080;

    private ConcurrentLinkedDeque<ClientHandler> clients;

    public Server() {
        clients = new ConcurrentLinkedDeque<>();
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server was started and up");
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client connected");
                ClientHandler handler = new ClientHandler(socket, this);
                addClient(handler);
                new Thread(handler).start();
            }
        } catch (Exception e) {
            System.err.println("Server was broken");
        }
    }

    public static void main(String[] args) {
        new Server();

    }

    public void addClient(ClientHandler handler) {
        clients.add(handler);
        System.out.println("[ADD] Client added to queue");
    }

    public void removeClient(ClientHandler handler) {
        clients.remove(handler);
        System.out.println("[REMOVE] Client removed from queue");
    }

    public void sendMessagePrivate(String nickFrom, String nickName, String message) throws IOException {
        SimpleDateFormat date = new SimpleDateFormat("YYYY.MM.dd" + " " + "HH:mm");
        for (ClientHandler handler : clients) {
            if (handler.getNickName().equals(nickName) || handler.getNickName().equals(nickFrom)) {
                handler.sendMessage(date.format(new Date()) + "\n" + message);
            }
        }
    }

    public void sendMessageToAll(String message) throws IOException {
        SimpleDateFormat date = new SimpleDateFormat("YYYY.MM.dd" + " " + "HH:mm");
        for (ClientHandler handler : clients) {
            handler.sendMessage(date.format(new Date()) + "\n" + message);
        }
    }
}
