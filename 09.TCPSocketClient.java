
/*
9. Using TCP/IP sockets, write a client – server program to make the client send the file
name and to make the server send back the contents of the requested file if present.
*/

import java.net.Socket;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

class Client {

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("127.0.0.1", 1300);
            Scanner socketScanner = new Scanner(socket.getInputStream());
            Scanner consoleScanner = new Scanner(System.in);
            System.out.println("Enter File Name");
            String fileName = consoleScanner.nextLine();
            PrintStream printStream = new PrintStream(socket.getOutputStream());
            printStream.println(fileName);
            while (socketScanner.hasNextLine()) {
                System.out.println(socketScanner.nextLine());
            }
            socket.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}