package com.ssa.ironyard.fitness.model;

import java.time.LocalDate;
import java.util.Objects;

import com.ssa.ironyard.fitness.model.Exercise.Category;

public class CategoryHolder {

    LocalDate date;
    Category cat;

    public CategoryHolder() {
        // TODO Auto-generated constructor stub
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Category getCat() {
        return cat;
    }

    public void setCat(Category cat) {
        this.cat = cat;
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
        if (this == obj)
            return true;
        if (! (obj instanceof CategoryHolder) || obj == null)
            return false;
        CategoryHolder other = (CategoryHolder) obj;
        
        if (! Objects.equals(cat, other.cat))
            return false;
        if (! Objects.equals(this.date,other.date))
                return false;
        
        return true;
    }

    @Override
    public String toString() {
        return "CategoryHolder [date=" + date + ", cat=" + cat + "]";
    }

}
