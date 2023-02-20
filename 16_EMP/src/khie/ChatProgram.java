package khie;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatProgram extends JFrame implements ActionListener {
  private JTextField input;
  private JTextArea display;
  private JButton send;
  private JPanel panel;
  private JScrollPane scroll;
  private DataOutputStream out;
  private DataInputStream in;
  private Socket socket;

  public ChatProgram() {
    super("Chat Program");
    input = new JTextField(30);
    display = new JTextArea(10, 30);
    send = new JButton("Send");
    panel = new JPanel();
    scroll = new JScrollPane(display);
    display.setEditable(false);
    send.addActionListener(this);
    panel.add(input);
    panel.add(send);
    add(scroll, BorderLayout.NORTH);
    add(panel, BorderLayout.SOUTH);
    setSize(400, 300);
    setVisible(true);
  }

  public void startClient() {
    try {
      socket = new Socket("localhost", 6789);
      out = new DataOutputStream(socket.getOutputStream());
      in = new DataInputStream(socket.getInputStream());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void startServer() {
    try {
      ServerSocket serverSocket = new ServerSocket(6789);
      socket = serverSocket.accept();
      out = new DataOutputStream(socket.getOutputStream());
      in = new DataInputStream(socket.getInputStream());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void actionPerformed(ActionEvent event) {
    if (event.getSource() == send) {
      try {
        out.writeUTF(input.getText());
        input.setText("");
        String message = in.readUTF();
        display.append("\n" + message);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public static void main(String[] args) {
    ChatProgram client = new ChatProgram();
    client.startClient();
  }
}
