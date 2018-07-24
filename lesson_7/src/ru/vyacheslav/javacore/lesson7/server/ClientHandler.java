package ru.vyacheslav.javacore.lesson7.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {

    private Server myServer;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private String name;

    public String getName() {
        return name;
    }

    public ClientHandler(Server myServer, Socket socket) {
        try {
            this.myServer = myServer;
            this.socket = socket;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
            this.name = "";
            new Thread(() -> {
                try {
                    while (true) { // цикл авторизации
                        String str = in.readUTF();
                        if (str.startsWith("/auth")) {
                            String[] parts = str.split("\\s");
                            String nick =
                                    myServer.getAuthService().getNickByLoginPass(parts[1], parts[2]);
                            if (nick != null) {
                                if (!myServer.isNickBusy(nick)) {
                                    sendMsg("/authok " + nick);
                                    name = nick;
                                    myServer.broadcastMsg(name + " зашел в чат");
                                    myServer.subscribe(this);
                                    break;
                                } else sendMsg("Учетная запись уже используется");
                            } else {
                                sendMsg("Неверные логин/пароль");
                            }
                        }
                    }
                    while (true) { // цикл получения сообщений
                        String str = in.readUTF();
                        System.out.println("от " + name + ": " + str);
                        if (str.equals("/end")) break;
                        try {
                            if (str.startsWith("/w")) { // создание личного сообщения
                                String[] nickParts = str.split("\\s");
                                String nick = nickParts[1];
                                String[] msgParts = str.split("\\s",3);
                                String msg = msgParts[2];
                                if (nick != null && msg != null) {
                                    if (myServer.isNickBusy(nick)) {
                                        sendMsg("Вы отправили личное сообщение " + nick + ": " + msg);
                                        myServer.privateMsg("Личное сообщение от " + name + ": " + msg, nick);
                                    } else sendMsg("Пользователя " + nick + " не существует");
                                } else {
                                    sendMsg("Неверные данные");
                                }
                            }  else {
                                myServer.broadcastMsg(name + ": " + str);
                            }
                        } catch (ArrayIndexOutOfBoundsException e) {
                            sendMsg("Команда введена неверно - /w nick сообщение");
                        }

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    myServer.unsubscribe(this);
                    myServer.broadcastMsg(name + " вышел из чата");
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } catch (IOException e) {
            throw new RuntimeException("Проблемы при создании обработчика клиента");
        }
    }

    public void sendMsg(String msg) {
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
