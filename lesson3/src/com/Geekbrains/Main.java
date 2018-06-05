package com.Geekbrains;

import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println(" Задание 1:");
        System.out.println("Привет друг! Давай поиграем в игру: ты должен отгадать число, которое я загадал (от 0 до 9).");
	    stage1();
//        stage2();
    }

    public static void stage1() {
        Random rand = new Random();
        int pc_nmb = rand.nextInt(10);
//        int pc_nmb = 6;
        Scanner in = new Scanner(System.in);
        System.out.print("У тебя 3 попытки! Испытаем твою удачу: ");

        int attempts = 0;
        while (attempts < 3) {
            int user_nmb = in.nextInt();

            if (user_nmb > 9 || user_nmb < 0) {
                System.out.print("Я же сказал ОТ 0 ДО 9!!!");
                System.out.println("\n");
                stage1();
                break;
            }

            if (pc_nmb > user_nmb) {
                System.out.println("Не угадал! Даю подсказку: мое число больше " + user_nmb + "\n");
            }
            if (pc_nmb < user_nmb) {
                System.out.println("Не угадал! Даю подсказку: мое число меньше " + user_nmb + "\n");
            }
            if (pc_nmb == user_nmb) {
                System.out.println("Как ты это сделал? Поздравляю, ты угадал, мое число " + pc_nmb + "\n");
                stage2();
                break;
            }
            attempts++;

            if (attempts == 3) {
                System.out.println("\n Игра как-то не задалась. Может попробуем еще раз? \n0 - нет, 1 - да, 2 - переход к следующему заданию \n");
                int user_choice = in.nextInt();
                switch (user_choice) {
                    case 0:
                        break;
                    case 1:
                        System.out.println("Поехали!");
                        stage1();
                        break;
                    case 2:
                        stage2();
                        break;
                }
                break;
            }

            System.out.print("Давай попробуем еще разочек: ");

        }
    }

//Не успел дописать вариант, когда первые буквы у слов совпадают.
    public static void stage2() {
        String[] words = {"apple", "orange", "lemon", "banana", "apricot", "avocado", "broccoli", "carrot", "cherry", "garlic", "grape", "melon", "leak", "kiwi", "mango", "mushroom", "nut", "olive", "pea", "peanut", "pear", "pepper", "pineapple", "pumpkin", "potato"};
        System.out.println(" Задание 2:");
        System.out.println("А теперь я загадал слово на английском языке.");

        Random rand = new Random();
        int word_numb = rand.nextInt(words.length);
        String pc_word = words[word_numb];
//        String pc_word = "orange";
        Scanner in = new Scanner(System.in);
        System.out.print("Попробуй его отгадать: ");
        String user_word = in.nextLine();
        user_word = user_word.toLowerCase();

        for (int i = 0; i < user_word.length() + 1; i++) {
            if (pc_word.charAt(i) == user_word.charAt(i) && pc_word.length() == user_word.length()) {
                System.out.println("Молодец! Ты угадал мое слово " + pc_word);
                break;
            }
            else {
                if (pc_word.charAt(i) == user_word.charAt(i) && pc_word.length() != user_word.length()) {
                        System.out.print(pc_word.charAt(i));
                }
                if (pc_word.charAt(1) != user_word.charAt(1)) {
                    System.out.print("Ни разу не угадал! Пробуй еще: ");
                    user_word = in.nextLine();
                }
            }
        }

    }
}