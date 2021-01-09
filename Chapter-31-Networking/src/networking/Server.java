package networking;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Server {
    private static int port;
    private static ServerSocket server;
    private static Set<Socket> clients;

    public static void setServer(){
        port = 8080;
        clients = new HashSet<>();
        try {
            server = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void runServer(){
        try {
            while(true)
            {
                Socket socket = server.accept();
                clients.add(socket);

                Thread connection = new Thread(()->{
                    read(socket);
                });
                connection.start();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void send(){
        Scanner scanner = new Scanner(System.in);
        try {
            for(Socket socket: clients)
            {
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                out.writeUTF(scanner.nextLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void read(Socket socket){
        try {
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out;
            while(true){
                String str = in.readUTF();
                for(Socket s : clients){
                    if(s != socket) {
                        out = new DataOutputStream(s.getOutputStream());
                        out.writeUTF(str);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        setServer();
        runServer();
    }
}
