package com.asodesunidos.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.asodesunidos.entity.User;

import java.util.List;

@Dao
public interface UserDAO extends CrudDAO<User> {

    @Override
    @Query("SELECT * FROM users")
    List<User> findAll();

    @Query("SELECT * FROM users WHERE username = :username AND password = :password")
    User check(String username, String password);
}
