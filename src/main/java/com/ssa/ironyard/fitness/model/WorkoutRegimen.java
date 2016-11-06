
package com.ssa.ironyard.fitness.model;

import com.ssa.ironyard.fitness.model.Regimen.DAY;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author thurston
 */
public class WorkoutRegimen implements Iterable<Regimen>
{
    final List<Regimen> items = new ArrayList<>();
    
    
    public Account getAccount()
    {
        if (items.isEmpty())
            return null;
        return this.items.get(0).getAccount();
    }
    
    public List<Regimen> daily(DAY dayOfWeek)
    {
        Stream<Regimen> matching = this.items.stream().filter(regimen -> regimen.getDay() == dayOfWeek);
        return matching.collect(Collectors.toList());
    }
    
    public void add(Regimen exercise)
    {
        this.items.add(exercise);
    }
    
    public void add(Collection<? extends Regimen> exercises)
    {
        for (Regimen exercise : exercises)
            add(exercise);
    }
    
    public boolean remove(Regimen goner)
    {
        return this.items.remove(goner);
    }

    public List<Regimen> getExercises()
    {
        List<Regimen> sorted = new ArrayList<>(this.items.size());
        for (Regimen regimen : this.items)
        {
            sorted.add(regimen);
        }
        return sorted;
    }
    
    @Override
    public Iterator<Regimen> iterator()
    {
        final List<Regimen> sorted = new ArrayList<>(this.items);
        sorted.sort((one, other) -> {
            DAY dayOne = one.getDay();
            DAY dayOther = other.getDay();
            if (dayOne == dayOther)
                return one.idComparison(other);
            if (dayOne == null ^ dayOther == null)
            {
                return dayOne == null ? -1 : 1;
            }
            return dayOne.ordinal() - dayOther.ordinal();
        });
        
        return sorted.iterator();
    }

    @Override
    public String toString() {
        return "WorkoutRegimen [items=" + items + "]";
    }
    
    /**
     *
     * @return list {@link DAY days} where a day is defined in the regimen in US weekly order (Sunday - Saturday)
     */
    public List<DAY> activeDays()
    {
        List<DAY> actives = this.items.stream().map(Regimen::getDay).distinct().collect(Collectors.toList());
        actives.sort(Comparator.comparing(DAY::ordinal));
        return actives;
    }
    
    public List<DAY> restDays()
    {
        List<DAY> activeDays = activeDays();
        return Stream.of(DAY.values()).filter(day -> ! activeDays.contains(day)).collect(Collectors.toList());
    }
}
