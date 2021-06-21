package io.socket;

import constants.Ports;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Scanner;
import java.util.concurrent.locks.LockSupport;

/**
 * @author 🌺xuliangliang🌺
 * @Description
 * @Date 2021/6/20
 */
public class ClientDemo {

    public static void main(String[] args) throws IOException {
        // socket()
        Socket socket = new Socket();
        socket.setSendBufferSize(20);
        socket.setTcpNoDelay(true);

        // bind()
        SocketAddress socketAddress = new InetSocketAddress("localhost", Ports.CLIENT_PORT_001);
        socket.bind(socketAddress);

        // connect()
        SocketAddress serverAddress = new InetSocketAddress("localhost", Ports.SERVER_PORT);
        socket.connect(serverAddress);

        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        Scanner sc = new Scanner(System.in);

        while (true) {
            String writeData = sc.nextLine();
            bufferedWriter.write(writeData);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            String readData = bufferedReader.readLine();
            System.out.println("recv : " + readData);

            if ("bye".equals(readData)) {
                socket.close();
                break;
            }
        }

        bufferedReader.close();
        bufferedWriter.close();

    }
}
