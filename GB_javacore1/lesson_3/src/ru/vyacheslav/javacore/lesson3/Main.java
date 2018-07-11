package ru.vyacheslav.javacore.lesson3;

// @author Vyacheslav Suslov

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class Main {

    public static void main(String[] args) {
        //Задание 1
        countArray();

        //Задание 2
        Phonebook phonebook = new Phonebook();
        phonebook.getInfo("Svetlov");
        phonebook.putInfo("Chernichev", "+7911111111");
        phonebook.getInfo("Chernichev");


    }
    private static void countArray() {
        ArrayList<String> array = new ArrayList<String>();
        array.add("tea");
        array.add("king");
        array.add("map");
        array.add("desert");
        array.add("tea");
        array.add("tea");
        array.add("tea");
        array.add("monkey");
        array.add("eagle");
        array.add("desert");
        array.add("monkey");
        array.add("map");
        array.add("tea");
        array.add("tea");
        array.add("king");
        array.add("map");
        array.add("tea");
        array.add("tea");
        array.add("monkey");
        array.add("monkey");

        //Не знаю, на сколько адекватно таким образом преобразовывать List в Set, и затем обратно :)
        HashSet<String> arrayUnique = new HashSet<>(array);
        ArrayList<String> arrayCount = new ArrayList<>(arrayUnique);

        for (int i = 0; i < arrayUnique.size(); i++) {
            System.out.println("Количество " + arrayCount.get(i) + ": " + Collections.frequency(array, arrayCount.get(i)));
        }
        System.out.println("\n");
    }

}
