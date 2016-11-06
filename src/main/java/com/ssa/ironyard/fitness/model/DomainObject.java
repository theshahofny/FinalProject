package com.ssa.ironyard.fitness.model;

import java.util.Objects;

public interface DomainObject extends Cloneable {

        Integer getId();
        default boolean isLoaded()
        {
            return false;
        }

        default boolean deeplyEquals(DomainObject other)
        {
            if (! Objects.deepEquals(this, other))
                return false;
            return true;
        }
      
        @Override
        String toString();

        @Override
        boolean equals(Object other);

        @Override
        int hashCode();


        DomainObject clone();
        
        default int idComparison(DomainObject other)
        {
            if (Objects.equals(this, other))
                return 0;
            if (other == null)
                return 1;
            Integer idOne = getId();
            Integer idOther = other.getId();
            if (Objects.equals(idOne, idOther))
                return 0;
            if (idOne == null ^ idOther == null)
            {
                return idOne == null ? -1 : 1;
            };
            return idOne.compareTo(idOther);
        }
    }
