package com.ssa.ironyard.fitness.model;

import java.math.BigDecimal;

public class Regimen implements DomainObject {
    Integer id;
    DAY day;
    Exercise exercise;
    Integer sets;
    Integer reps;
    BigDecimal weight;
    BigDecimal time;
    Account account;
    BigDecimal distance;
    boolean isLoaded = false;

    public Regimen() {
    }

    public enum DAY {
        Sunday("Sun"), Monday("Mon"), Tuesday("Tues"), Wednesday("Wed"), Thursday("Thurs"), Friday("Friday"), Saturday("Sat");

        public final String abbrev;
        
        private DAY(String abbrev) {
            this.abbrev = abbrev;
        }

        public static DAY getInstance(String abbrev) {
            for (DAY d : values()) {
                if (d.abbrev.equals(abbrev))
                    return d;
            }
            return null;
        }
    }

    public DAY getDay() {
        return day;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDay(DAY day) {
        this.day = day;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public Integer getSets() {
        return sets;
    }

    public void setSets(Integer sets) {
        this.sets = sets;
    }

    public Integer getReps() {
        return reps;
    }

    public void setReps(Integer reps) {
        this.reps = reps;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigDecimal getTime() {
        return time;
    }

    public void setTime(BigDecimal time) {
        this.time = time;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public BigDecimal getDistance() {
        return distance;
    }

    public void setDistance(BigDecimal distance) {
        this.distance = distance;
    }

    public boolean isLoaded() {
        return isLoaded;
    }

    public void setLoaded(boolean isLoaded) {
        this.isLoaded = isLoaded;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        Regimen other = (Regimen) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;

        return true;
    }

    @Override
    public boolean deeplyEquals(DomainObject obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Regimen other = (Regimen) obj;
        if (account == null) {
            if (other.account != null)
                return false;
        } else if (!account.equals(other.account))
            return false;
        if (day != other.day)
            return false;
        if (distance.equals(other.distance))
            return false;
        if (exercise == null) {
            if (other.exercise != null)
                return false;
        } else if (!exercise.equals(other.exercise))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (isLoaded != other.isLoaded)
            return false;
        if (reps != other.reps)
            return false;
        if (sets != other.sets)
            return false;
        if (time == null) {
            if (other.time != null)
                return false;
        } else if (!time.equals(other.time))
            return false;
        if (weight.equals(other.weight))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Regimen [id=" + id + ", day=" + day + ", exercise=" + exercise + ", sets=" + sets + ", reps=" + reps
                + ", weight=" + weight + ", time=" + time + ", account=" + account + ", distance=" + distance
                + ", isLoaded=" + isLoaded + "]";
    }

    @Override
    public Regimen clone() {
        try {
            Regimen r = (Regimen) super.clone();
            r.setExercise(this.exercise.clone());
            r.setAccount(this.account.clone());
            return r;
        } catch (Exception e) {

        }
        return null;
    }
}
