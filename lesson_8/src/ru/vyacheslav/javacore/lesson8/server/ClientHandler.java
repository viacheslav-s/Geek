package ru.vyacheslav.javacore.lesson8.server;

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
    private boolean authorisedClient = false;

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
            final int timeout = 120 * 1000;


            new Thread(() -> { // устанавливаем таймаут на разрыв соединения в отдельном потоке
                try {
                    Thread.sleep(timeout);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    if (!authorisedClient) {
                        sendMsg("Время ожидания в " + (timeout / 1000) +
                                " секунд истекло. Вы были отключены от сервера.");
                        socket.close();
                        System.out.println("Тайм-аут в " + (timeout / 1000) +
                                " секунд истек. Соедниение с клиентом закрыто.");
                    }
                } catch (IOException e)  {
                    e.printStackTrace();
                }
//                finally {
//                    myServer.unsubscribe(this);
//                    myServer.broadcastMsg("Инкогнито вышел из чата");
//                    try {
//                        socket.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
            }).start();

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
                                    authorisedClient = true;
                                    break;
                                } else sendMsg("Учетная запись уже используется");
                            } else {
                                sendMsg("Неверные логин/пароль");
                            }
                        }
                    }

                    while (true) { // цикл получения сообщений
                        try {
                            String str = in.readUTF();
                            if (str.startsWith("/")) {
                                if (str.equals("/end")) break;
                                if (str.startsWith("/w ")) {
                                    String[] privateNickPrts = str.split("\\s");
                                    String nick = privateNickPrts[1];
                                    String[] privateMsgPrts = str.split("\\s",3);
                                    String msg = privateMsgPrts[2];
                                    if (myServer.isNickBusy(nick)) {
                                        sendMsg("Вы отправили личное сообщение " + nick + ": " + msg);
                                        myServer.privateMsg("Личное сообщение от " + name + ": " + msg,nick);
                                    } else sendMsg("Пользователя " + nick + " не существует");
                                }
                            } else {
                                myServer.broadcastMsg(name + ": " + str);
                            }
                        } catch(ArrayIndexOutOfBoundsException e){
                                sendMsg("Команда введена неверно - /w nick сообщение");
                            }
                        }

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    myServer.unsubscribe(this);
                    if (name.equals("")) {
                        myServer.broadcastMsg("Инкогнито вышел из чата");
                    } else {
                        myServer.broadcastMsg(name + " вышел из чата");
                    }
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
