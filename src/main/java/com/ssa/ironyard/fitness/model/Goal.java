package com.ssa.ironyard.fitness.model;

public class Goal implements DomainObject {
    Integer id;
    Type type;

    boolean isLoaded = false;

    public Goal() {
        // TODO Auto-generated constructor stub
    }

    public enum Type {
        Strength("Str"), Endurance("End"), WeightLoss("WL");

        public final String abbrev;

        private Type(String abbrev) {
            this.abbrev = abbrev;
        }

        public static Type getInstance(String abbrev) {
            for (Type t : values()) {
                if (t.abbrev.equals(abbrev))
                    return t;
            }
            return null;
        }
    }

    public Goal(Type type) {
        this.type = type;
    }

    public Goal(Integer id, Type type) {
        this.id = id;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public boolean isLoaded() {
        return isLoaded;
    }

    public void setLoaded(boolean isLoaded) {
        this.isLoaded = isLoaded;
    }

    @Override
    public Goal clone() {
        try {
            return (Goal) super.clone();
        } catch (CloneNotSupportedException ex) {
        }
        return null;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        Goal other = (Goal) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    public boolean deeplyEquals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Goal other = (Goal) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (isLoaded != other.isLoaded)
            return false;
        if (type != other.type)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Goal [id=" + id + ", type=" + type + ", isLoaded=" + isLoaded + "]";
    }

}
