package com.asodesunidos.config;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.asodesunidos.dao.CrudDAO;
import com.asodesunidos.dao.CustomerDAO;
import com.asodesunidos.dao.LoanDAO;
import com.asodesunidos.dao.UserDAO;
import com.asodesunidos.dao.UserDAO_Impl;
import com.asodesunidos.entity.Customer;
import com.asodesunidos.entity.Loan;
import com.asodesunidos.entity.User;


@Database(entities = {User.class, Customer.class, Loan.class}, version = 1)
public abstract class DatabaseConfig extends RoomDatabase {

    public abstract UserDAO getUserDAO();

    public abstract CustomerDAO getCustomerDAO();

    public abstract LoanDAO getLoanDAO();


}
