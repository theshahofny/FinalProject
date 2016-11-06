package com.ssa.ironyard.fitness.model;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class DateHolder {

    LocalDate date;
    List<Exercise.Category> categories;

    public DateHolder() {
    }

    public DateHolder(LocalDate date, List<CategoryHolder> categories) {
        super();
        this.date = date;
        this.categories = categories.stream().map(CategoryHolder::getCat).collect(Collectors.toList());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((date == null) ? 0 : date.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        DateHolder other = (DateHolder) obj;
        if (date == null) {
            if (other.date != null)
                return false;
        } else if (!date.equals(other.date))
            return false;
        return true;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<Exercise.Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Exercise.Category> categories) {
        this.categories = categories;
    }

    public void reset() {
        this.categories = Collections.emptyList();
    }

    @Override
    public String toString() {
        return "DateHolder [date=" + date + ", categories=" + categories + "]";
    }

}
