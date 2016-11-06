package com.ssa.ironyard.fitness.model;

import java.time.LocalDate;

public class WorkoutLogThingy
{
    LocalDate workout_date;
    int exerciseId;
    int sets;
    int reps;
    double weight;
    double time;
    double distance;
    public LocalDate getWorkout_date()
    {
        return workout_date;
    }
    public void setWorkout_date(LocalDate workout_date)
    {
        this.workout_date = workout_date;
    }
    public int getExercise()
    {
        return exerciseId;
    }
    public void setExercise(int exercise)
    {
        this.exerciseId = exercise;
    }
    public int getSets()
    {
        return sets;
    }
    public void setSets(int sets)
    {
        this.sets = sets;
    }
    public int getReps()
    {
        return reps;
    }
    public void setReps(int reps)
    {
        this.reps = reps;
    }
    public double getWeight()
    {
        return weight;
    }
    public void setWeight(double weight)
    {
        this.weight = weight;
    }
    public double getTime()
    {
        return time;
    }
    public void setTime(double time)
    {
        this.time = time;
    }
    public double getDistance()
    {
        return distance;
    }
    public void setDistance(double distance)
    {
        this.distance = distance;
    }
    

}
