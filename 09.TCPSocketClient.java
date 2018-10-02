
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
            Socket s = new Socket("127.0.0.1", 1300);
            Scanner scanner = new Scanner(s.getInputStream());
            // Replace 'file.txt' with your file name.
            // If using Java Project/IDE, place that file at root of the project.
            // If using Command Line, place that file in the source code directory.
            String output = "file.txt";
            PrintStream p = new PrintStream(s.getOutputStream());
            p.println(output);
            while (scanner.hasNextLine()) {
                System.out.println(scanner.nextLine());
            }
        } catch (IOException e) {

        }
    }

}