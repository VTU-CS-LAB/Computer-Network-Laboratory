
/*
9. Using TCP/IP sockets, write a client – server program to make the client send the file
name and to make the server send back the contents of the requested file if present.
*/

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

class Server {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(1300);
            Socket socket = serverSocket.accept();
            Scanner socketScanner = new Scanner(socket.getInputStream());
            String fileName = socketScanner.nextLine().trim();
            PrintStream printStream = new PrintStream(socket.getOutputStream());
            File file = new File(fileName);
            if (file.exists()) {
                Scanner fileScanner = new Scanner(file);
                while (fileScanner.hasNextLine()) {
                    printStream.println(fileScanner.nextLine());
                }
            } else {
                printStream.println("ERROR: FILE DOESN'T EXISTS");
            }
            // Keep the server alive for complete transfer of data
            System.in.read();

            socket.close();
            serverSocket.close();
        } catch (IOException e) {

        }
    }
}