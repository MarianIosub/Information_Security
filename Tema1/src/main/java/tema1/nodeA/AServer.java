package tema1.nodeA;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class AServer {
    private Socket KM = null;
    private Socket B = null;
    private ServerSocket server = null;
    private DataInputStream in = null;
    private DataInputStream inB = null;
    private DataInputStream inKM = null;
    private DataOutputStream outB = null;
    private DataOutputStream outKM = null;

    // constructor with port
    public AServer(int port) {
        // starts server and waits for a connection
        try {
            server = new ServerSocket(port);
            System.out.println("Server started");

            System.out.println("Waiting for clients ...");

            KM = server.accept();
            System.out.println("KM Client accepted");

            B = server.accept();
            System.out.println("B Client accepted");
            in = new DataInputStream(System.in);
            outB = new DataOutputStream(B.getOutputStream());
            outKM = new DataOutputStream(KM.getOutputStream());
            inB = new DataInputStream(B.getInputStream());
            inKM = new DataInputStream(KM.getInputStream());


        } catch (IOException i) {
            System.out.println(i);
        }
    }

    public String readFromClient() {
        String response = "";
        System.out.println("Introduceti va rog modul de criptare:  ");

        while (!(response.equals("OFB") || response.equals("ECB"))) {
            System.out.print("  >>>");
            Scanner scanner = new Scanner(System.in);
            response = scanner.nextLine();
            if (!(response.equals("OFB") || response.equals("ECB"))) {
                System.out.println("The operation mode can be only OFB or ECB!");
            }
        }
        return response;
    }

    public byte[] getMessageFromB() {
        byte[] message = new byte[200];
        try {
            inB.read(message);
            return message;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public byte[] getMessageFromKM(int n) {
        byte[] message = new byte[n];
        try {
            inKM.read(message);
            return message;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void sendToKM(byte[] message) {
        try {
            outKM.write(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendToB(byte[] message) {
        try {
            outB.write(message);
            outB.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeConnections() throws IOException {
        KM.close();
        B.close();
        in.close();
        inB.close();
        inKM.close();
        outB.close();
        outKM.close();
    }
}
