package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Network {
    private final static int PORT = 8080;
    private static Network instance;

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    private Network() {
        try {
            socket = new Socket("localhost", PORT);
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.err.println("Problem with server");
        }
    }

    public static Network getInstance() {
        if (instance == null) {
           instance = new Network();
        }
        return instance;
    }

    public void writeMessage(String message) throws IOException {
        out.writeUTF(message);
        out.flush();
    }

    public String readMessage() throws IOException {
        return in.readUTF();
    }

    public void close() throws IOException {
        out.close();
        in.close();
        socket.close();
    }
}
