
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
            Socket s = serverSocket.accept();
            Scanner scanner = new Scanner(s.getInputStream());
            String input = scanner.next();
            PrintStream p = new PrintStream(s.getOutputStream());
            File file = new File(input);
            if (file.exists()) {
                Scanner fileScanner = new Scanner(file);
                while (fileScanner.hasNextLine()) {
                    p.println(fileScanner.nextLine());
                }
            } else {
                p.println("ERROR: FILE DOESN'T EXISTS");
            }
            // Keep the server alive for complete transfer of data
            System.in.read();
        } catch (IOException e) {

        }
    }
}