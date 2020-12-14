package com.muc;

import java.io.*;
import java.net.Socket;

public class ChatClient {
    private final String serverName;
    private final int serverPort;
    private OutputStream serverOut;
    private InputStream serverIn;
    private Socket socket;
    private BufferedReader bufferedIn;

    public ChatClient(String serverName, int serverPort) {
        this.serverName = serverName;
        this.serverPort = serverPort;
    }

    public static void main(String arg[]) throws IOException {
        ChatClient client = new ChatClient("localhost" , 5000);
        if(!client.connect())
        {
            System.err.println("Connect failed");
        }else{
            System.out.println("Connect successfully");
            if(client.login("guest" , "guest"))
            {
                System.out.println("Login successful");
            }else{
                System.err.println("Login failed");
            }
        }
    }

    private boolean login(String login, String password) throws IOException {
        String cmd = "login " + login + " " + password + "\n";
        serverOut.write(cmd.getBytes());

        String response = bufferedIn.readLine();
        System.out.println("Response Line: " + response);

        if("ok login".equalsIgnoreCase(response))
        {
            return true;
        }else{
            return false;
        }
    }

    private boolean connect() {
        try {
            this.socket = new Socket(serverName, serverPort);
            System.out.println("Client port is " + socket.getLocalPort());
            this.serverOut = socket.getOutputStream();
            this.serverIn = socket.getInputStream();
            this.bufferedIn = new BufferedReader(new InputStreamReader(serverIn));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
