package khie;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
  public static void main(String[] args) {
    try {
      Socket socket = new Socket("localhost", 6789);
      System.out.println("Connected to the server.");

      DataInputStream in = new DataInputStream(socket.getInputStream());
      DataOutputStream out = new DataOutputStream(socket.getOutputStream());

      Scanner scan = new Scanner(System.in);
      while (true) {
        System.out.println("Enter message: ");
        String message = scan.nextLine();
        out.writeUTF(message);
        System.out.println("Server says: " + in.readUTF());
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}