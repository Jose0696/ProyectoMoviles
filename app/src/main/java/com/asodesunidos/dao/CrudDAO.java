package com.asodesunidos.dao;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

import java.util.List;

public interface CrudDAO<E> {

    @Insert
    long insert(E entity);

    @Delete
    void remove(E entity);

    @Update
    void update(E entity);

    @Insert
    void insertAll(E... entities);

    List<E> findAll();
}

class UtilTableName {

    public static String getName() {
        return "";
    }

}
