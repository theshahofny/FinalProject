package com.ssa.ironyard.fitness.utils;

import java.time.LocalDateTime;

public class Weeks {

    final LocalDateTime t1;

    public Weeks(LocalDateTime t1) {

        this.t1 = t1;
    }
    
    //use wh to grab first workout date from user and use this method to compare and get the number of weeks they've been active.

    public int getWeeks() {
        LocalDateTime present = LocalDateTime.now();
        int year1 = t1.getYear();
        int year2 = present.getYear();
        int day1 = t1.getDayOfYear();
        int day2 = present.getDayOfYear();

        if (year2 - year1 == 0)
            return (day2 - day1) / 7;
        else{
           double modifier = (year2-year1) * 365.25;
           
        return (int) ((day2 - day1 + modifier)/7);
            
        }

    }
}
