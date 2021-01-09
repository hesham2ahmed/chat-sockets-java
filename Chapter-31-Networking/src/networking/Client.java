package networking;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static DataInputStream in;
    private static DataOutputStream out;
    public static Socket socket;

    public static void connect(int port, String host){
        try {
            socket = new Socket(host, port);

            Thread thread = new Thread(()->{
                read();
            });
            thread.start();

            Thread thread1 = new Thread(()->{
                send();
            });
            thread1.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void send(){
        Scanner scanner = new Scanner(System.in);
        try {
            out = new DataOutputStream(socket.getOutputStream());
            while(true){
                out.writeUTF(scanner.nextLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void read(){
        try {
            in = new DataInputStream(socket.getInputStream());
            while(true){
                System.out.println("Other client: " + in.readUTF());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Client.connect(8080, "localhost");
    }
}
