package ru.vyacheslav.javacore.lesson8.server.service;

public interface AuthService {

    void start();

    String getNickByLoginPass(String login,String pass);

    void stop();

}