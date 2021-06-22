package io.socket;

import constants.Ports;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * @author 🌺xuliangliang🌺
 * @Description
 * @Date 2021/6/20
 */
public class ServerDemo {

    public static void main(String[] args) throws IOException {
        // socket()
        ServerSocket socket = new ServerSocket();

        // bind() and listen()
        SocketAddress serverAddress = new InetSocketAddress("localhost", Ports.SERVER_PORT);
        socket.bind(serverAddress, 3);

        System.out.println("服务已启动.");

        // accept() block
        for (;;) {
            Socket clientSocket = socket.accept();
            new ServerThread(clientSocket).start();
        }

    }

    static class ServerThread extends Thread {

        private final Socket clientSocket;

        ServerThread(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            String clientInfo = clientSocket.getInetAddress().getHostAddress() + ":" + clientSocket.getPort();
            System.out.println(clientInfo + "已连接.");
            BufferedReader bufferedReader = null;
            BufferedWriter bufferedWriter = null;
            try {
                bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                while (true) {
                    String readData = bufferedReader.readLine();
                    System.out.println("recv : " + readData);
                    bufferedWriter.write(readData);
                    bufferedWriter.newLine();
                    bufferedWriter.flush();

                    if ("bye".equals(readData)) {
                        // bye bye
                        System.out.println(clientInfo + "已断开连接");
                        clientSocket.close();
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    if (bufferedWriter != null) {
                        bufferedWriter.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
