package com.Geekbrains;

public class Course {
    Barrier[] barriers = new Barrier[10];



    Course() {
        for (int i = 0; i < barriers.length; i++) {
            barriers[i] = new Barrier(i + 1, i + 4);
        }

    }


    public void doIt(Team team) {
        for (int i = 0; i < barriers.length; i++) {
            TeamMemberResult[] teamMemberResults = new TeamMemberResult[team.teamMembers.length];
            for (int j = 0; j < team.teamMembers.length; j++) {
                if(team.teamMembers[j].memberPower > barriers[i].power) {
                    teamMemberResults[j] = new TeamMemberResult(team.teamMembers[j], true);
                } else {
                    teamMemberResults[j] = new TeamMemberResult(team.teamMembers[j], false);
                }
            }
            team.results.put(barriers[i], teamMemberResults);
        }
    }
}
