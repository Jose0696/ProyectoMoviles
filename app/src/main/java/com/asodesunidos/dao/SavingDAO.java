package com.asodesunidos.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.asodesunidos.entity.Saving;

import java.util.List;

@Dao
public interface SavingDAO extends CrudDAO<Saving>{

    @Query("SELECT * FROM savings where customerId = :customerId")
    List<Saving> find(int customerId);

    @Query("SELECT * FROM savings")
    @Override List<Saving> findAll();
}
