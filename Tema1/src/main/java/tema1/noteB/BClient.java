package tema1.noteB;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class BClient {
    private Socket A = null;
    private DataInputStream inA = null;
    private DataOutputStream outA = null;

    // constructor to put ip address and port
    public BClient(String address, int port) {
        // establish a connection
        try {
            A = new Socket(address, port);
            System.out.println("Connected");

            // sends output to the socket
            outA = new DataOutputStream(A.getOutputStream());

        } catch (UnknownHostException u) {
            System.out.println(u);
        } catch (IOException i) {
            System.out.println(i);
        }
    }

    public void sendMessageToA(byte[] message) {
        try {
            outA.write(message);
            outA.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public byte[] readMessageFromA(int n) throws IOException {
        inA = new DataInputStream(A.getInputStream());
        byte[] message = new byte[n];
        try {

            inA.read(message);
            return message;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void closeConnections() throws IOException {
        inA.close();
        outA.close();
        A.close();
    }
}
