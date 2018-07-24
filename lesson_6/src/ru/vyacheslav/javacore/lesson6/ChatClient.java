package ru.vyacheslav.javacore.lesson6;

// @author Vyacheslav Suslov

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Scanner;

public class ChatClient extends JDialog {
    private JPanel contentPaneClient;
    private JButton buttonSendClient;
    private JFormattedTextField chatTextFieldClient;
    private JTextArea chatAreaClient;
    private JPanel textSenderClient;
    private JPanel chatViewClient;
    private JLabel errorsLabelClient;

    private final String SERVER_ADDR = "localhost";
    private final int SERVER_PORT = 8189;
    private Socket sock;
    private Scanner in;
    private PrintWriter out;

    public ChatClient() {
        setTitle("Уютный чатик - Клиент");

        setContentPane(contentPaneClient);
        setModal(true);
        getRootPane().setDefaultButton(buttonSendClient);

        buttonSendClient.addActionListener(new ActionListener() {
            final public void actionPerformed(ActionEvent e) {
                onSend();
            }
        });

        chatTextFieldClient.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_ENTER:
                        onSend();
                        break;
                }
            }
        });

        try {
            sock = new Socket(SERVER_ADDR, SERVER_PORT);
            in = new Scanner(sock.getInputStream());
            out = new PrintWriter(sock.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        String inMsg = in.nextLine();
                        if (inMsg.equals("end")) break;
                        chatAreaClient.append("Server-App: " + inMsg + "\n");
                        out.flush();
                        System.out.println("Сервер пишет: " + inMsg);
                    }
                } catch (Exception e) {
                }
            }
        }).start();

    }
    private void onSend() {
        final int maxChar = 40;
        if (chatTextFieldClient.equals(chatTextFieldClient.getText().contains(""))) {
            errorsLabelClient.setText("Поле пустое, введите сообщение!");
        }
        if (chatTextFieldClient.getText().length() > maxChar) {
            errorsLabelClient.setText("Максмальное колчество знаков " + maxChar);
        }
        if (chatTextFieldClient.getText().length() > 0 && chatTextFieldClient.getText().length() <= maxChar) {
            errorsLabelClient.setText("Сообщение отправлено: " + dateNow());
            chatAreaClient.append("Вы: " + chatTextFieldClient.getText() + "\n");
            out.println(chatTextFieldClient.getText());
            out.flush();
            chatTextFieldClient.setText("");
        }
    }

    private String dateNow() {
        String today = ZonedDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT));
        return today;

    }


}