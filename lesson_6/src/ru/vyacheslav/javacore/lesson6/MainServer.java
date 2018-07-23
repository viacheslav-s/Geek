package ru.vyacheslav.javacore.lesson6;

// @author Vyacheslav Suslov

public class MainServer {

    public static void main(String[] args) {
        final ChatServer serverDialog = new ChatServer();
        serverDialog.pack();
        serverDialog.setVisible(true);
        System.exit(0);
    }
}
