package com.ssa.ironyard.fitness.model;

public class WeeklyScore implements DomainObject {
    Integer id;
    int week;
    Account account;
    double score;
    boolean isLoaded = false;

    public WeeklyScore() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public boolean isLoaded() {
        return isLoaded;
    }

    public void setLoaded(boolean isLoaded) {
        this.isLoaded = isLoaded;
    }

    @Override
    public boolean equals(Object obj) {
        WeeklyScore other = (WeeklyScore) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
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
    public boolean deeplyEquals(DomainObject obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        WeeklyScore other = (WeeklyScore) obj;
        if (account == null) {
            if (other.account != null)
                return false;
        } else if (!account.equals(other.account))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (isLoaded != other.isLoaded)
            return false;
        if (Double.doubleToLongBits(score) != Double.doubleToLongBits(other.score))
            return false;
        if (week != other.week)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "WeeklyScore [id=" + id + ", week=" + week + ", account=" + account + ", score=" + score + ", isLoaded="
                + isLoaded + "]";
    }

    @Override
    public WeeklyScore clone() {

        try {
            WeeklyScore ws = (WeeklyScore) super.clone();
            ws.setAccount(this.account.clone());
            return ws;
        } catch (Exception e) {

        }
        return null;
    }

}
