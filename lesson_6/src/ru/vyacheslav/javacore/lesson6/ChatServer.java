package ru.vyacheslav.javacore.lesson6;

// @author Vyacheslav Suslov

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Scanner;

public class ChatServer extends JDialog {
    private JPanel contentPaneServer;
    private JButton buttonSendServer;
    private JFormattedTextField chatTextFieldServer;
    private JTextArea chatAreaServer;
    private JPanel textSenderServer;
    private JPanel chatViewServer;
    private JLabel errorsLabelServer;

    public ChatServer() {
        setTitle("Уютный чатик - Сервер");
        setContentPane(contentPaneServer);
        setModal(true);
        getRootPane().setDefaultButton(buttonSendServer);

        new Thread(new Runnable() {
            @Override
            public void run() {
                ServerSocket serv = null;
                Socket sock = null;
                try {
                    serv = new ServerSocket(8189);
                    System.out.println("Сервер запущен, ожидаем подключения...");
                    errorsLabelServer.setText("Сервер запущен, ожидаем подключения...");
                    sock = serv.accept();
                    System.out.println("Клиент подключился");
                    errorsLabelServer.setText("Клиент подключился. Отправьте сообщение");
                    Scanner sc = new Scanner(sock.getInputStream());
                    PrintWriter pw = new PrintWriter(sock.getOutputStream());

                    buttonSendServer.addActionListener(new ActionListener() {
                        final public void actionPerformed(ActionEvent e) {
                            onSend(pw);
                        }
                    });

                    chatTextFieldServer.addKeyListener(new KeyAdapter() {
                        @Override
                        final public void keyPressed(KeyEvent e) {
                            switch (e.getKeyCode()) {
                                case KeyEvent.VK_ENTER:
                                    onSend(pw);
                                    break;
                            }
                        }
                    });

                    while (true) {
                        String inMsg = sc.nextLine();
                        if (inMsg.equals("end")) break;
                        chatAreaServer.append("Client-App: " + inMsg + "\n");
                        pw.flush();
                        System.out.println("Клиент пишет: " + inMsg);
                        if (sc.hasNext()) {
                            onSend(pw);
                        }
                    }

                } catch (IOException e) {
                    System.out.println("Ошибка инициализации сервера");
                } finally {
                    try {
                        serv.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }

    private void onSend(PrintWriter out) {
        final int maxChar = 40;

        if (chatTextFieldServer.equals(chatTextFieldServer.getText().contains(""))) {
            errorsLabelServer.setText("Поле пустое, введите сообщение!");
        }
        if (chatTextFieldServer.getText().length() > maxChar) {
            errorsLabelServer.setText("Максмальное колчество знаков " + maxChar);
        }
        if (chatTextFieldServer.getText().length() > 0 && chatTextFieldServer.getText().length() <= maxChar) {
            errorsLabelServer.setText("Сообщение отправлено: " + dateNow());
            chatAreaServer.append("Вы: " + chatTextFieldServer.getText() + "\n");
            out.println(chatTextFieldServer.getText());
            out.flush();
            chatTextFieldServer.setText("");
        }
    }

    private String dateNow() {
        String today = ZonedDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT));
        return today;

    }
}