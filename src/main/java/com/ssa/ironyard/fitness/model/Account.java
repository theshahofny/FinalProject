package com.ssa.ironyard.fitness.model;

import java.util.Objects;

public class Account implements DomainObject
{

    Integer id;
    String username;
    Password password;

    String firstName;
    String lastName;

    Integer age;
    Gender gender;
    Double height;
    Double weight;

    Goal goal;

    boolean isLoaded = false;

    public Account()
    {
    }

    public Account(Integer id)
    {
        this.id = id;
    }

    public Account(String username, Password password)
    {
        this.username = username;
        this.password = password;
    }

    public Account(Integer id, String username, Password password)
    {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public enum Gender
    {
        Male('M'), Female('F');

        public final char abbrev;

        private Gender(char abbrev)
        {
            this.abbrev = abbrev;
        }

        public static Gender getInstance(char abbrev)
        {
            for (Gender g : values())
            {
                if (g.abbrev == abbrev)
                    return g;
            }
            return null;
        }
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public Password getPassword()
    {
        return password;
    }

    public void setPassword(Password password)
    {
        this.password = password;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public Integer getAge()
    {
        return age;
    }

    public void setAge(Integer age)
    {
        this.age = age;
    }

    public Double getHeight()
    {
        return height;
    }

    public void setHeight(Double height)
    {
        this.height = height;
    }

    public Double getWeight()
    {
        return weight;
    }

    public void setWeight(Double weight)
    {
        this.weight = weight;
    }

    public Goal getGoal()
    {
        return goal;
    }

    public void setGoal(Goal goal)
    {
        this.goal = goal;
    }

    public boolean isLoaded()
    {
        return isLoaded;
    }

    public void setLoaded(boolean isLoaded)
    {
        this.isLoaded = isLoaded;
    }

    @Override
    public Account clone()
    {
        try
        {
            return (Account) super.clone();
        } catch (CloneNotSupportedException ex)
        {
        }
        return null;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());

        return result;
    }

    @Override
    public boolean deeplyEquals(DomainObject obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Account other = (Account) obj;
        if (age == null)
        {
            if (other.age != null)
                return false;
        } else if (!age.equals(other.age))
            return false;
        if (firstName == null)
        {
            if (other.firstName != null)
                return false;
        } else if (!firstName.equals(other.firstName))
            return false;
        if (gender != other.gender)
            return false;
        if (goal == null)
        {
            if (other.goal != null)
                return false;
        } else if (!goal.equals(other.goal))
            return false;
        if (height == null)
        {
            if (other.height != null)
                return false;
        } else if (!height.equals(other.height))
            return false;
        if (id == null)
        {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (isLoaded != other.isLoaded)
            return false;
        if (lastName == null)
        {
            if (other.lastName != null)
                return false;
        } else if (!lastName.equals(other.lastName))
            return false;
        if (password == null)
        {
            if (other.password != null)
                return false;
        } else if (!password.equals(other.password))
            return false;
        if (username == null)
        {
            if (other.username != null)
                return false;
        } else if (!username.equals(other.username))
            return false;
        if (weight == null)
        {
            if (other.weight != null)
                return false;
        } else if (!weight.equals(other.weight))
            return false;
        return true;
    }

    public Gender getGender()
    {
        return gender;
    }

    public void setGender(Gender gender)
    {
        this.gender = gender;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (! (obj instanceof Account) || (null == obj))
            return false;
        Account other = (Account) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString()
    {
        return "Account [id=" + id + ", username=" + username + ", password=" + password + ", firstName=" + firstName
                + ", lastName=" + lastName + ", age=" + age + ", gender=" + gender + ", height=" + height + ", weight="
                + weight + ", goal=" + goal + ", isLoaded=" + isLoaded + "]";
    }

}
