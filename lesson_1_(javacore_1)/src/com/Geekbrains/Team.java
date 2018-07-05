package com.Geekbrains;

import java.util.HashMap;

public class Team {
    public String teamName = "Команда А";
    public HashMap<Barrier, TeamMemberResult[]> results = new HashMap<Barrier, TeamMemberResult[]>();

    public TeamMember[] teamMembers = new TeamMember[3];
    public Team (int memberNumber, int memberPower){
        for (int i = 0; i < teamMembers.length; i++) {
            teamMembers[i] = new TeamMember(i + 1, i + 5);
        }

    }

    protected void teamName() {System.out.println("Список участников " + teamName + ":");}
//    protected void teamMembers() {System.out.println();}
    protected void showResults() {
        for (HashMap.Entry<Barrier, TeamMemberResult[]> entry: results.entrySet()) {
            System.out.println("\nПрепятствие " + entry.getKey().number + ":");
            for (int i = 0; i < entry.getValue().length; i++) {
                String pass = entry.getValue()[i].pass ? "прошел" : "не прошел";
                System.out.println("Игрок " + entry.getValue()[i].teamMember.memberNumber + ":" + pass);
            }
        }
    }
}
