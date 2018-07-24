package ru.vyacheslav.javacore.lesson6;

// @author Vyacheslav Suslov

public class MainClient {
    public static void main(String[] args) {
        final ChatClient clientDialog = new ChatClient();
        clientDialog.pack();
        clientDialog.setVisible(true);
        System.exit(0);
    }
}
