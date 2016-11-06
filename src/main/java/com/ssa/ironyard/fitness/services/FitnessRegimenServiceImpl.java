package com.ssa.ironyard.fitness.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ssa.ironyard.fitness.controller.RegimenController;
import com.ssa.ironyard.fitness.dao.RegimenDAOImpl;
import com.ssa.ironyard.fitness.model.Regimen;
import com.ssa.ironyard.fitness.model.WorkoutRegimen;

@Component
public class FitnessRegimenServiceImpl
{
    Logger LOGGER = LogManager.getLogger(FitnessRegimenServiceImpl.class);

    RegimenDAOImpl daoRegimen;

    @Autowired
    public FitnessRegimenServiceImpl(RegimenDAOImpl daoRegimen)
    {
        this.daoRegimen = daoRegimen;
    }

    @Transactional
    public Regimen readRegimen(Integer id)
    {
        return daoRegimen.read(id);
    }

    @Transactional
    public WorkoutRegimen readAll(Integer id)
    {
        return daoRegimen.readByUserId(id);
    }

    @Transactional
    public Regimen insertRegimen(Regimen regimen)
    {
        return daoRegimen.insert(regimen);
    }

    @Transactional
    public Regimen updateRegimen(Regimen regimen)
    {
        return daoRegimen.update(regimen);
    }

    @Transactional
    public boolean deleteRegimen(Integer id)
    {
        return daoRegimen.delete(id);
    }

    public List<Regimen> insertRegimens(List<Regimen> regimens)
    {
        List<Regimen> list = new ArrayList<>();

        for (Regimen r : regimens)
            list.add(daoRegimen.insert(r));
        return list;
    }

    public List<Regimen> updateRegimens(List<Regimen> regimens)
    {
        List<Regimen> list = new ArrayList<>();

        for (Regimen r : regimens)
            list.add(daoRegimen.update(r));
        return list;
    }
}
