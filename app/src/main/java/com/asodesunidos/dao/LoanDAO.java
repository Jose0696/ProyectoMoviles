package com.asodesunidos.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.asodesunidos.entity.Loan;

import java.util.List;

@Dao
public interface LoanDAO extends CrudDAO<Loan>  {


    @Query("SELECT * FROM loans where customerId = :customerId")
    List<Loan> find(int customerId);


    @Query("SELECT * FROM loans")
    @Override List<Loan> findAll();

}