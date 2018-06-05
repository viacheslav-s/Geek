package com.Geekbrains;

public class Main {

    public static void main(String[] args) {
        TestEmployee();
        Over40();
    }
    private static void TestEmployee() {
        employee employeeTest;
        employeeTest = new employee("Петров Вадим", "Backend", "vadim@test.ru", "+7777777777", 40000, 25);
        System.out.println("Тестовый сотрудник: " + employeeTest.name + ", " + employeeTest.position + ", " + employeeTest.email + ", " + employeeTest.phone + ", " + employeeTest.wage + ", " + employeeTest.age);
    }

    private static void Over40 () {
        employee[] AllEmployee = new employee[5];
        AllEmployee[0] = new employee("Хакимуллин Тимур", "Тестировщик", "test@test.ru", "+7777777888", 30000,47);
        AllEmployee[1] = new employee("Белкина Лера", "Менеджер", "test@test.ru", "+7777777888", 30000,20);
        AllEmployee[2] = new employee("Абаджев Андрей", "iOS", "test@test.ru", "+7777777888", 30000,28);
        AllEmployee[3] = new employee("Попов Михаил", "Frontend", "test@test.ru", "+7777777888", 30000,50);
        AllEmployee[4] = new employee("Ахметшина Алина", "Тестировщик", "test@test.ru", "+7777777888", 30000,41);

        System.out.println("\nСотрудники старше 40 лет:");
        for (int i = 0; i < AllEmployee.length; i++) {
            if(AllEmployee[i].age > 40) {
                System.out.println("Сотрудник " + i + ": " + AllEmployee[i].name + ", " + AllEmployee[i].position + ", " + AllEmployee[i].email + ", " + AllEmployee[i].phone + ", " + AllEmployee[i].wage + ", " + AllEmployee[i].age);
            }
        }
    }
}
