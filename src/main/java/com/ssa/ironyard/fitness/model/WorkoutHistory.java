package com.ssa.ironyard.fitness.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class WorkoutHistory implements DomainObject {
    Integer id;

    LocalDate workout_date;
    Exercise exercise;
    Integer sets;
    Integer reps;
    BigDecimal weight;
    BigDecimal time;
    Account account;
    BigDecimal distance;
    boolean isLoaded = false;

    public WorkoutHistory() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getWorkout_date() {
        return workout_date;
    }

    public void setWorkout_date(LocalDate workout_date) {
        this.workout_date = workout_date;
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

    public BigDecimal getDistance() {
        return distance;
    }

    public void setDistance(BigDecimal distance) {
        this.distance = distance;
    }

    public boolean isLoaded() {
        return isLoaded;
    }

    public void setLoaded(boolean loaded) {
        this.isLoaded = loaded;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public WorkoutHistory clone() {

        try {
            WorkoutHistory wh = (WorkoutHistory) super.clone();
            wh.setExercise(this.exercise.clone());
            wh.setAccount(this.account.clone());
            return wh;
        } catch (Exception e) {

        }
        return null;
    }

    @Override
    public boolean deeplyEquals(DomainObject obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        WorkoutHistory other = (WorkoutHistory) obj;
        if (account == null) {
            if (other.account != null)
                return false;
        } else if (!account.equals(other.account))
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
        if (time != null) {
            if (other.time != null)
                return false;
        } else if (!(time == other.time))
            return false;
        if (weight.equals(other.weight))
            return false;
        if (workout_date == null) {
            if (other.workout_date != null)
                return false;
        } else if (!workout_date.equals(other.workout_date))
            return false;
        return true;
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
        WorkoutHistory other = (WorkoutHistory) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "WorkoutHistory [id=" + id + ", workout_date=" + workout_date + ", exercise=" + exercise + ", sets="
                + sets + ", reps=" + reps + ", weight=" + weight + ", time=" + time + ", account=" + account
                + ", distance=" + distance + ", isLoaded=" + isLoaded + "]";
    }

}
