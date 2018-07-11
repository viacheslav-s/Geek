package ru.vyacheslav.javacore.lesson3;

// @author Vyacheslav Suslov

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

class Phonebook {
    private HashMap<String, String> phonebook = new HashMap<>();

    void getInfo(String name) {
//        HashMap<String, String> phonebook = new HashMap<>();
        phonebook.put("Popov", "+79999999991");
        phonebook.put("Svetlov", "+79999999992");
        phonebook.put("Kononenko", "+79999999993");
        phonebook.get(name);

        Set<Map.Entry<String, String>> set = phonebook.entrySet();
        for (Map.Entry<String, String> o : set) {
            if (o.getKey() == name) {
                System.out.println("Номер " + o.getKey() + ": " + o.getValue());
            }
        }
    }
    void putInfo(String name, String phone) {
        phonebook.put(name, phone);
    }

    }


