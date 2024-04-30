package com.asodesunidos.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.asodesunidos.entity.Customer;

import java.util.List;

@Dao
public interface CustomerDAO extends CrudDAO<Customer>{
    @Override
    @Query("SELECT * FROM customers")
    List<Customer> findAll();

    @Query("SELECT * FROM customers WHERE uid = :uid")
    Customer findCustomer(int uid);
}
